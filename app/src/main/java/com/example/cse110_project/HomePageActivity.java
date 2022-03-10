///*
// * Sources used:
// *
// * https://www.tutorialspoint.com/how-to-get-spinner-value-in-android
// * */
//
//package com.example.cse110_project;
//
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.Spinner;
//import android.widget.TextView;
//
//import com.example.cse110_project.databases.AppDatabase;
//import com.example.cse110_project.databases.bof.BoFCourse;
//import com.example.cse110_project.databases.bof.BoFStudent;
//import com.example.cse110_project.databases.def.DefaultCourse;
//import com.example.cse110_project.databases.def.DefaultStudent;
//import com.example.cse110_project.databases.user.UserCourse;
//import com.example.cse110_project.utilities.Constants;
//import com.example.cse110_project.utilities.PrioritizationAlgorithms;
//import com.example.cse110_project.utilities.comparators.BoFComparator;
//import com.example.cse110_project.utilities.comparators.DefaultBoFComparator;
//import com.example.cse110_project.utilities.comparators.PrioritizeMostRecentComparator;
//import com.example.cse110_project.utilities.comparators.PrioritizeSmallClassesComparator;
//
//import java.util.List;
//
//
//
//public class HomePageActivity extends AppCompatActivity {
//    private AppDatabase db;
//    private boolean Start;
//    private Spinner sortingOptionsDropdown;
//    protected RecyclerView studentsRecyclerView;
//    protected RecyclerView.LayoutManager studentsLayoutManager;
//    protected BoFStudentViewAdapter studentsViewAdapter;
//
//    class SortingOptions implements AdapterView.OnItemSelectedListener {
//
//        @Override
//        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//            String sortingOption = adapterView.getItemAtPosition(i).toString();
//
//            if (db == null) { return; }
//
//            if (sortingOption.equals("Default")) {
//                displayBirdsOfAFeatherList(new DefaultBoFComparator(db.BoFCourseDao()));
//            } else if (sortingOption.equals("Prioritize Recent")) {
//                displayBirdsOfAFeatherList(new PrioritizeMostRecentComparator());
//            } else {
//                displayBirdsOfAFeatherList(new PrioritizeSmallClassesComparator());
//            }
//        }
//
//        @Override
//        public void onNothingSelected(AdapterView<?> adapterView) {}
//
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_home_page);
//        setTitle(Constants.APP_VERSION);
//
//        initSortingOptions();
//
//        db = AppDatabase.singleton(getApplicationContext());
//
//        // FIXME: testing
//        sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
//        sortingOptionsDropdown.setOnItemSelectedListener(new SortingOptions());
//        sortingOptionsDropdown.setClickable(false);
//
//        Start = false;
//
//        Bundle extras = getIntent().getExtras();
//        if (extras!=null){
//            if(extras.containsKey("start")){
//                Start = extras.getBoolean("start",false);
//            };
//        }
//
//        TextView topLeftButton = findViewById(R.id.start_button);
//
//        if(Start){
//            topLeftButton.setText(Constants.STOP);
//        }
//        else{
//            topLeftButton.setText(Constants.START);
//        }
//
//        String currText = topLeftButton.getText().toString();
//        if (currText.equals(Constants.STOP)) {
//            compareUserCoursesWithStudents();
//        }
//
//
//        displayBirdsOfAFeatherList(new DefaultBoFComparator(db.BoFCourseDao()));
//    }
//
//    public void onStartClicked(View view) {
//        TextView topLeftButton = findViewById(R.id.start_button);
//        String currText = topLeftButton.getText().toString();
//
//        if (currText.equals(Constants.START)) {
//            Start = true;
//
//            this.sortingOptionsDropdown.setClickable(true);
//
//            topLeftButton.setText(Constants.STOP);
//            compareUserCoursesWithStudents();
//        }
//        else {
//            topLeftButton.setText(Constants.START);
//            Start = false;
//
//            this.sortingOptionsDropdown.setClickable(false);
//        }
//
//        displayBirdsOfAFeatherList(new DefaultBoFComparator(db.BoFCourseDao()));
//    }
//
//    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, MainPageActivity.class);
//        startActivity(intent);
//    }
//
//    public void onMockNearbyMessagesClicked(View view) {
//        Intent intent = new Intent(this, MockNearbyMessagesActivity.class);
//        intent.putExtra("start", Start);
//        startActivity(intent);
//    }
//
//    /**
//     * Adds the students and courses that the User has shared a class with to the BoF database by
//     * cross-checking the User's courses with all the pre-populated courses in the database
//     **/
//    public void compareUserCoursesWithStudents() {
//        List<UserCourse> ucl = db.UserCourseDao().getAll();
//        List<DefaultCourse> dcl;
//        List<BoFStudent> bsl;
//        String year;
//        String quarter;
//        String classSize;
//        String course;
//        String courseNum;
//        double sizeScore;
//        int recentScore;
//        boolean next;
//
//        // Checks which students in the default database have courses that match with the courses
//        // the user has entered
//        for (UserCourse uc : ucl) {
//            year = uc.getYear();
//            quarter = uc.getQuarter();
//            classSize = uc.getClassSize();
//            course = uc.getCourse();
//            courseNum = uc.getCourseNum();
//
//            dcl = db.DefaultCourseDao().getAll();
//
//            for (DefaultCourse dc : dcl) {
//                next = false;
//
//                if (dc.getCourseAdded()) { continue; }
//                // Checks the default course database if any match with the course the user has
//                // entered
//                else if ((dc.getYear().equals(year)) && (dc.getQuarter().equals(quarter))
//                        && (dc.getClassSize().equals(classSize)) && (dc.getCourse().equals(course))
//                        && (dc.getCourseNum().equals(courseNum))) {
//
//                    DefaultStudent currMatchingStudent = db.DefaultStudentDao().get(dc.getStudentId());
//                    bsl = db.BoFStudentDao().getAll();
//
//                    // Checks if the student with the matching course is already in the BoF student
//                    // database
//                    // Note: Current duplicate check is finding the same name
//                    for (BoFStudent bs : bsl) {
//                        if (bs.getName().equals(currMatchingStudent.getName())) {
//                            db.BoFCourseDao().insert(new BoFCourse(bs.getStudentId(), dc.getYear(),
//                                    dc.getQuarter(), dc.getClassSize(), dc.getCourse(), dc.getCourseNum()));
//
//                            // Calculates new class size and recent scores based on the matched
//                            // course
//                            sizeScore = PrioritizationAlgorithms.calcClassSizeScore(bs.getSizeScore(),
//                                    dc.getClassSize().charAt(0));
//                            recentScore = PrioritizationAlgorithms.calcRecentScore(bs.getRecentScore(),
//                                    dc.getYear(), dc.getQuarter());
//
//                            db.BoFStudentDao().updateCourseSizeScore(sizeScore, bs.getStudentId());
//                            db.BoFStudentDao().updateRecentScore(recentScore, bs.getStudentId());
//
//                            next = true;
//                            break;
//                        }
//                    }
//
//                    // States that the matching course has already been added to the BoF Course
//                    // database for the associated student (who exists in the BoF student database
//                    // prior)
//                    if (next) {
//                        db.DefaultCourseDao().updateCourseAdded(true, dc.getCourseId());
//                        continue;
//                    }
//
//                    sizeScore = PrioritizationAlgorithms.calcClassSizeScore(0,
//                            dc.getClassSize().charAt(0));
//                    recentScore = PrioritizationAlgorithms.calcRecentScore(0,
//                            dc.getYear(), dc.getQuarter());
//
//                    // The above but for the case where the student does not exist in the BoF
//                    // student database
//                    db.BoFStudentDao().insert(new BoFStudent(currMatchingStudent.getName(),
//                            sizeScore, recentScore, currMatchingStudent.getIsWaving()));
////                    db.BoFStudentDao().insert(new BoFStudent(currMatchingStudent.getName(),
////                            sizeScore, recentScore));
//                    bsl = db.BoFStudentDao().getAll();
//
//                    for (BoFStudent bs : bsl) {
//                        if (bs.getName().equals(currMatchingStudent.getName())) {
//                            db.BoFCourseDao().insert(new BoFCourse(bs.getStudentId(), dc.getYear(),
//                                    dc.getQuarter(), dc.getClassSize(), dc.getCourse(), dc.getCourseNum()));
//                            break;
//                        }
//                    }
//
//                    db.DefaultCourseDao().updateCourseAdded(true, dc.getCourseId());
//                }
//            }
//        }
//    }
//
//    public void displayBirdsOfAFeatherList(BoFComparator comparator) {
//        db = AppDatabase.singleton(getApplicationContext());
//        List<BoFStudent> students = db.BoFStudentDao().getAll();
//
//        studentsRecyclerView = findViewById(R.id.students_view);
//        studentsLayoutManager = new LinearLayoutManager(this);
//        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
//        studentsViewAdapter = new BoFStudentViewAdapter(students, db.BoFCourseDao(),
//                db.FavoriteDao(), comparator);
//        studentsRecyclerView.setAdapter(studentsViewAdapter);
//    }
//
//    public void initSortingOptions() {
//        Spinner sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                R.array.sorting_options, android.R.layout.simple_spinner_dropdown_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
//        sortingOptionsDropdown.setAdapter(adapter);
//    }
//}

/*
* Sources used:
*
* https://www.tutorialspoint.com/how-to-get-spinner-value-in-android
* */

package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.bof.BoFStudentDao;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.PrioritizationAlgorithms;
import com.example.cse110_project.utilities.Utilities;
import com.example.cse110_project.utilities.comparators.BoFComparator;
import com.example.cse110_project.utilities.comparators.DefaultBoFComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeMostRecentComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeSmallClassesComparator;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.Arrays;
import java.util.List;

public class HomePageActivity extends AppCompatActivity {
    private AppDatabase db;
    private boolean searchButtonState;
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected BoFStudentViewAdapter studentsViewAdapter;
    protected BluetoothAdapter bluetoothAdapter;
    protected MessageListener mMessageListener;
    protected Message mMessage;

    private static final String TAG = "CSE110-Project";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HomePageActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle(Constants.APP_VERSION);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        //check if bluetooth is on
        if (bluetoothAdapter == null || (!bluetoothAdapter.isEnabled())) {
            runOnUiThread(() -> {
                Utilities.showAlert(this, Constants.ALERT, Constants.Bluetooth_Not_Supported);
            });
        }


        initSortingOptionsDropdown();
        checkSelectedSortingOption();
        checkStateOfSearchButton();

        mMessageListener = new MessageListener() {
            @Override
            public void onFound(final Message message) {
                Log.d(TAG, "Found message: " + new String(message.getContent()));

                String[] mockArr = new String(message.getContent()).split(Constants.COMMA);

                AppDatabase db = AppDatabase.getSingletonInstance();
                List<DefaultStudent> studentList = db.DefaultStudentDao().getAll();
                for (int i = 0; i< studentList.size(); i++){
                    if (studentList.get(i).getName().equals(mockArr[0])){
                        return;
                    }
                }
                db.DefaultStudentDao().insert(new DefaultStudent(mockArr[0]));

                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();


                for (int i = 2; i < mockArr.length; i=i+5) {
                    db.DefaultCourseDao().insert(new DefaultCourse(defStudentsList.get(defStudentsList.size()-1).getStudentId(),
                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
                }
            }

            @Override
            public void onLost(final Message message) {
                Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }

        };

        if(db.UserDao()!=null && db.UserDao().getAll().size()!=0){
            StringBuilder information = new StringBuilder(db.UserDao().getAll().get(0).getUserFirstName());
            for(int i = 0; i < db.UserCourseDao().getAll().size(); i++){
                String year = db.UserCourseDao().getAll().get(i).getYear();
                String quarter = db.UserCourseDao().getAll().get(i).getQuarter();
                String subject = db.UserCourseDao().getAll().get(i).getCourse();
                String number = db.UserCourseDao().getAll().get(i).getCourseNum();
                String size = db.UserCourseDao().getAll().get(i).getClassSize();
                String fullCourseName = year + "," + quarter + "," + size + "," + subject + "," + number;
                information.append(",").append(fullCourseName);
            }
            mMessage = new Message(information.toString().getBytes());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mMessageListener!=null){
            Log.d(TAG, "subscribing");
            Nearby.getMessagesClient(this).subscribe(mMessageListener);
        }
        if(mMessage!=null){
            Log.d(TAG, "publishing message: " + new String(mMessage.getContent()));
            Nearby.getMessagesClient(this).publish(mMessage);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mMessageListener!=null){
            Log.d(TAG, "unsubscribing");
            Nearby.getMessagesClient(this).unsubscribe(mMessageListener);
        }
        if(mMessage!=null){
            Log.d(TAG, "unpublishing message: " + mMessage.toString());
            Nearby.getMessagesClient(this).unpublish(mMessage);
        }
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
        Log.d("HomePageActivity::checkStateOfSearchButton()", "Non:-testable method");

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

    public void onStartClicked(View view) {
        Log.d("HomePageActivity::onStartClicked()", "Non-testable method");

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

    public void initSortingOptionsDropdown() {
        Log.d("HomePageActivity::initSortingOptionsDropdown()", "Non-testable method");

        Spinner sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sortingOptionsDropdown.setAdapter(adapter);
        sortingOptionsDropdown.setOnItemSelectedListener(new SortingOptions());
    }
}
