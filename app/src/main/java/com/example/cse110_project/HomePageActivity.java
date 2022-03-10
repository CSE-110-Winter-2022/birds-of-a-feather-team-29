/*
* Sources used:
*
* https://www.tutorialspoint.com/how-to-get-spinner-value-in-android
* */

package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cse110_project.adapters.BoFStudentViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.bof.BoFStudentDao;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.PrioritizationAlgorithms;
import com.example.cse110_project.utilities.Utilities;
import com.example.cse110_project.utilities.comparators.BoFComparator;
import com.example.cse110_project.utilities.comparators.DefaultBoFComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeMostRecentComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeSmallClassesComparator;

import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private AppDatabase db;
    private boolean searchButtonState;
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected BoFStudentViewAdapter studentsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HomePageActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle(Constants.APP_VERSION);

        initSortingOptionsDropdown();
        checkSelectedSortingOption();
        checkStateOfSearchButton();
    }

    /**
     * Helper method to onSearchButtonClicked
     * */
    public void onSessionTypeClicked() {
        Spinner sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
        TextView topLeftButton = findViewById(R.id.start_button);
        String currText = topLeftButton.getText().toString();

        // If the User has ended the student search, then we maintain the list as is -- otherwise
        // the list is displayed by default sorting
        if (currText.equals(Constants.START)) {
            this.searchButtonState = true;
            topLeftButton.setText(Constants.STOP);
            sortingOptionsDropdown.setSelection(0);
            compareUserCoursesWithStudents(AppDatabase.singleton(getApplicationContext()));
            displayBirdsOfAFeatherList(db.BoFStudentDao(), new DefaultBoFComparator(db.BoFCourseDao()));
        }
        else {
            this.searchButtonState = false;
            topLeftButton.setText(Constants.START);
        }
    }

    public void onSearchButtonClicked(View view) {
        Log.d("HomePageActivity::onStartClicked()", "Non-testable method");

        TextView topLeftButton = findViewById(R.id.start_button);
        String currText = topLeftButton.getText().toString();

        // TODO: for US9

        // Source: https://techicaltutorial.blogspot.com/2021/02/android-studio-popup-window-with-input.html

        if ((currText.equals(Constants.START)) && (db.SessionDao().getAll().size() > 0)) {
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder
                    .setMessage("Would you like to resume a previous session or start" +
                            " a new session?")
                    .setPositiveButton("New", new DialogInterface.OnClickListener() {

                        // "Clears" the current session to start a new session
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            db.BoFStudentDao().delete();
                            onSessionTypeClicked();
                        }

                    })
                    .setNegativeButton("Resume", new DialogInterface.OnClickListener() {

                        // Functionality indefinitely postponed
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {}

                    });

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        } else if ((currText.equals(Constants.START)) && (db.SessionDao().getAll().size() <= 0)) {
            onSessionTypeClicked();
        } else {

            // sources:
            // https://stackoverflow.com/questions/4134117/edittext-on-a-popup-window
            // https://stackoverflow.com/questions/10903754/input-text-dialog-android
            // https://www.baeldung.com/java-replace-character-at-index

            EditText sessionNameEntry = new EditText(this);
            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
            alertBuilder
                    .setMessage((db.SessionDao().getAll().size() > 0) ? "Please save the session's data. Enter one of your" +
                            " current enrolled classes or a class name as the name of the session below:" :
                            "Please save the session's data. Enter a name for your session below:")
                    .setView(sessionNameEntry)
                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String sessionName = sessionNameEntry.getText().toString();
                            String sessionNameInDB;
                            char currentCopy;
                            int newCopy;

                            // Checks for duplicate Primary Key
                            // Note: If the session name entered by the user is a duplicate, then
                            //       we simply mark it as a duplicate. E.g. If we had CSE21 as an
                            //       existing session name, and the user enters CSE21, then we
                            //       save the current session as CSE21 (1)
                            for (Session s : db.SessionDao().getAll()) {
                                if (s.getSessionName().equals(sessionName)) {
                                    sessionNameInDB = s.getSessionName();

                                    if (sessionNameInDB.charAt(s.getSessionName().length()-1) != ')') {
                                        sessionName = sessionName.concat(" (1)");
                                    } else {
                                        currentCopy = sessionNameInDB.charAt(s.getSessionName().length()-2);
                                        newCopy = Character.getNumericValue(currentCopy) + 1;
                                        sessionName = sessionNameInDB.substring(0,sessionNameInDB.length()-2)
                                                + (char)(newCopy + '0')
                                                + sessionNameInDB.substring(sessionNameInDB.length()-1);
                                    }
                                }
                            }

                            db.SessionDao().insert(new Session(sessionName));
                            onSessionTypeClicked();
                        }

                    });

            AlertDialog alertDialog = alertBuilder.create();
            alertDialog.show();
        }
    }

    public void onBackClicked(View view) {
        Log.d("HomePageActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, MainPageActivity.class);
        saveSortingOption();
        saveSearchButtonState();
        startActivity(intent);
    }

    public void onMockNearbyMessagesClicked(View view) {
        Log.d("HomePageActivity::onMockNearbyMessagesClicked()", "Non-testable method");

        Intent intent = new Intent(this, MockNearbyMessagesActivity.class);
        saveSortingOption();
        saveSearchButtonState();
        startActivity(intent);
    }

    /**
     * Adds the students and courses that the User has shared a class with to the BoF database by
     * cross-checking the User's courses with all the pre-populated courses in the database
     **/
    public void compareUserCoursesWithStudents(AppDatabase db) {
        List<UserCourse> ucl = db.UserCourseDao().getAll();
        List<DefaultCourse> dcl;
        List<BoFStudent> bsl;
        String year;
        String quarter;
        String classSize;
        String course;
        String courseNum;
        double sizeScore;
        int recentScore;
        boolean next;

        // Checks which students in the default database have courses that match with the courses
        // the user has entered
        for (UserCourse uc : ucl) {
            year = uc.getYear();
            quarter = uc.getQuarter();
            classSize = uc.getClassSize();
            course = uc.getCourse();
            courseNum = uc.getCourseNum();

            dcl = db.DefaultCourseDao().getAll();

            for (DefaultCourse dc : dcl) {
                next = false;

                if (dc.getCourseAdded()) { continue; }
                // Checks the default course database if any match with the course the user has
                // entered
                else if ((dc.getYear().equals(year)) && (dc.getQuarter().equals(quarter))
                        && (dc.getClassSize().equals(classSize)) && (dc.getCourse().equals(course))
                        && (dc.getCourseNum().equals(courseNum))) {

                    DefaultStudent currMatchingStudent = db.DefaultStudentDao().get(dc.getStudentId());
                    bsl = db.BoFStudentDao().getAll();

                    // Checks if the student with the matching course is already in the BoF student
                    // database
                    // Note: Current duplicate check is finding the same name
                    for (BoFStudent bs : bsl) {
                        if (bs.getName().equals(currMatchingStudent.getName())) {
                            db.BoFCourseDao().insert(new BoFCourse(bs.getStudentId(), dc.getYear(),
                                    dc.getQuarter(), dc.getClassSize(), dc.getCourse(), dc.getCourseNum()));

                            // Calculates new class size and recent scores based on the matched
                            // course
                            sizeScore = PrioritizationAlgorithms.calcClassSizeScore(bs.getSizeScore(),
                                    dc.getClassSize().charAt(0));
                            recentScore = PrioritizationAlgorithms.calcRecentScore(bs.getRecentScore(),
                                    dc.getYear(), dc.getQuarter());

                            db.BoFStudentDao().updateCourseSizeScore(sizeScore, bs.getStudentId());
                            db.BoFStudentDao().updateRecentScore(recentScore, bs.getStudentId());

                            next = true;
                            break;
                        }
                    }

                    // States that the matching course has already been added to the BoF Course
                    // database for the associated student (who exists in the BoF student database
                    // prior)
                    if (next) {
                        db.DefaultCourseDao().updateCourseAdded(true, dc.getCourseId());
                        continue;
                    }

                    sizeScore = PrioritizationAlgorithms.calcClassSizeScore(0,
                            dc.getClassSize().charAt(0));
                    recentScore = PrioritizationAlgorithms.calcRecentScore(0,
                            dc.getYear(), dc.getQuarter());

                    // The above but for the case where the student does not exist in the BoF
                    // student database
                    db.BoFStudentDao().insert(new BoFStudent(currMatchingStudent.getName(),
                            sizeScore, recentScore, currMatchingStudent.getIsWaving()));
                    bsl = db.BoFStudentDao().getAll();

                    for (BoFStudent bs : bsl) {
                        if (bs.getName().equals(currMatchingStudent.getName())) {
                            db.BoFCourseDao().insert(new BoFCourse(bs.getStudentId(), dc.getYear(),
                                    dc.getQuarter(), dc.getClassSize(), dc.getCourse(), dc.getCourseNum()));
                            break;
                        }
                    }

                    db.DefaultCourseDao().updateCourseAdded(true, dc.getCourseId());
                }
            }
        }
    }

    public void displayBirdsOfAFeatherList(BoFStudentDao bsd, BoFComparator comparator) {
        List<BoFStudent> students = bsd.getAll();

        studentsRecyclerView = findViewById(R.id.students_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
        studentsViewAdapter = new BoFStudentViewAdapter(students, db.BoFCourseDao(),
                db.FavoriteDao(), comparator);
        studentsRecyclerView.setAdapter(studentsViewAdapter);
    }

    public void saveSortingOption() {
        Log.d("HomePageActivity::saveSortingOption()", "Non-testable method");

        SharedPreferences sortingOptionSP = getSharedPreferences("SortingOption", MODE_PRIVATE);
        SharedPreferences.Editor sortingEditor = sortingOptionSP.edit();
        Spinner sortingOptionDropdown = findViewById(R.id.sorting_options_dropdown_menu);

        sortingEditor.putString("sortingOption", sortingOptionDropdown.getSelectedItem().toString());
        sortingEditor.apply();
    }

    public void saveSearchButtonState() {
        Log.d("HomePageActivity::saveSearchButtonState()", "Non-testable method");

        SharedPreferences searchButtonSP = getSharedPreferences("SearchButton", MODE_PRIVATE);
        SharedPreferences.Editor searchEditor = searchButtonSP.edit();

        searchEditor.putBoolean("searchButtonState", this.searchButtonState);
        searchEditor.apply();
    }

    public void checkSelectedSortingOption() {
        Log.d("HomePageActivity::checkSelectedSortingOption()", "Non-testable method");

        Spinner sortingOptionDropdown = findViewById(R.id.sorting_options_dropdown_menu);
        SharedPreferences sortingOptionSP = getSharedPreferences("SortingOption", MODE_PRIVATE);
        String sortingOption = sortingOptionSP.getString("sortingOption", "Default");
        String[] sortingOptions = { "Default", "Prioritize Recent", "Prioritize Small Classes" };

        // Reinitialize the BoF list as sorted by the previous sorting option selected by the User
        for (int i = 0; i < sortingOptions.length; i++) {
            if (sortingOption.equals(sortingOptions[i])) { sortingOptionDropdown.setSelection(i); }
        }
    }

    public void checkStateOfSearchButton() {
        Log.d("HomePageActivity::checkStateOfSearchButton()", "Non-testable method");

        db = AppDatabase.singleton(getApplicationContext());
        SharedPreferences searchButtonSP = getSharedPreferences("SearchButton", MODE_PRIVATE);
        this.searchButtonState = searchButtonSP.getBoolean("searchButtonState", false);
        TextView searchButton = findViewById(R.id.start_button);

        if (this.searchButtonState) {
            searchButton.setText(Constants.STOP);
            compareUserCoursesWithStudents(AppDatabase.singleton(getApplicationContext()));
        }
        else { searchButton.setText(Constants.START); }

        displayBirdsOfAFeatherList(db.BoFStudentDao(), new DefaultBoFComparator(db.BoFCourseDao()));
    }

    public void initSortingOptionsDropdown() {
        Log.d("HomePageActivity::initSortingOptionsDropdown()", "Non-testable method");

        Spinner sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sortingOptionsDropdown.setAdapter(adapter);
        sortingOptionsDropdown.setOnItemSelectedListener(new SortingOptions());
    }

    /**
     * Inner class for specifying what happens when a particular sorting option is selected at any
     * point in time on the home page
     * */
    class SortingOptions implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            Log.d("HomePageActivity.SortingOptions::onItemSelected()", "Non-testable method");

            if (db == null) { return; }

            String sortingOption = adapterView.getItemAtPosition(i).toString();

            if (sortingOption.equals("Default")) {
                displayBirdsOfAFeatherList(db.BoFStudentDao(), new DefaultBoFComparator(db.BoFCourseDao()));
            } else if (sortingOption.equals("Prioritize Recent")) {
                displayBirdsOfAFeatherList(db.BoFStudentDao(), new PrioritizeMostRecentComparator());
            } else {
                displayBirdsOfAFeatherList(db.BoFStudentDao(), new PrioritizeSmallClassesComparator());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
            Log.d("HomePageActivity.SortingOptions::onNothingSelected()", "Non-testable method");
        }

    }
}
