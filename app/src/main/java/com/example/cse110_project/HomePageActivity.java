/*
* Sources used:
*
* https://www.tutorialspoint.com/how-to-get-spinner-value-in-android
* */

package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.PrioritizationAlgorithms;
import com.example.cse110_project.utilities.comparators.BoFComparator;
import com.example.cse110_project.utilities.comparators.DefaultBoFComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeMostRecentComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeSmallClassesComparator;

import java.util.List;



public class HomePageActivity extends AppCompatActivity {
    private AppDatabase db;
    private boolean Start;
    private Spinner sortingOptionsDropdown;
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected BoFStudentViewAdapter studentsViewAdapter;

    class SortingOptions implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String sortingOption = adapterView.getItemAtPosition(i).toString();

            if (db == null) { return; }

            if (sortingOption.equals("Default")) {
                displayBirdsOfAFeatherList(new DefaultBoFComparator(db.BoFCourseDao()));
            } else if (sortingOption.equals("Prioritize Recent")) {
                displayBirdsOfAFeatherList(new PrioritizeMostRecentComparator());
            } else {
                displayBirdsOfAFeatherList(new PrioritizeSmallClassesComparator());
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {}

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle(Constants.APP_VERSION);

        initSortingOptions();

        db = AppDatabase.singleton(getApplicationContext());

        // FIXME: testing
        sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
        sortingOptionsDropdown.setOnItemSelectedListener(new SortingOptions());
        sortingOptionsDropdown.setClickable(false);

        Start = false;

        Bundle extras = getIntent().getExtras();
        if (extras!=null){
            if(extras.containsKey("start")){
                Start = extras.getBoolean("start",false);
            };
        }

        TextView topLeftButton = findViewById(R.id.start_button);

        if(Start){
            topLeftButton.setText(Constants.STOP);
        }
        else{
            topLeftButton.setText(Constants.START);
        }

        String currText = topLeftButton.getText().toString();
        if (currText.equals(Constants.STOP)) {
            compareUserCoursesWithStudents();
        }


        displayBirdsOfAFeatherList(new DefaultBoFComparator(db.BoFCourseDao()));
    }

    public void onStartClicked(View view) {
        TextView topLeftButton = findViewById(R.id.start_button);
        String currText = topLeftButton.getText().toString();

        if (currText.equals(Constants.START)) {
            Start = true;

            this.sortingOptionsDropdown.setClickable(true);

            topLeftButton.setText(Constants.STOP);
            compareUserCoursesWithStudents();
        }
        else {
            topLeftButton.setText(Constants.START);
            Start = false;

            this.sortingOptionsDropdown.setClickable(false);
        }

        displayBirdsOfAFeatherList(new DefaultBoFComparator(db.BoFCourseDao()));
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void onMockNearbyMessagesClicked(View view) {
        Intent intent = new Intent(this, MockNearbyMessagesActivity.class);
        intent.putExtra("start", Start);
        startActivity(intent);
    }

    /**
     * Adds the students and courses that the User has shared a class with to the BoF database by
     * cross-checking the User's courses with all the pre-populated courses in the database
     **/
    public void compareUserCoursesWithStudents() {
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
                            sizeScore, recentScore));
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

    public void displayBirdsOfAFeatherList(BoFComparator comparator) {
        db = AppDatabase.singleton(getApplicationContext());
        List<BoFStudent> students = db.BoFStudentDao().getAll();

        studentsRecyclerView = findViewById(R.id.students_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
        studentsViewAdapter = new BoFStudentViewAdapter(students, db.BoFCourseDao(),
                db.FavoriteDao(), comparator);
        studentsRecyclerView.setAdapter(studentsViewAdapter);
    }

    public void initSortingOptions() {
        Spinner sortingOptionsDropdown = findViewById(R.id.sorting_options_dropdown_menu);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sorting_options, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sortingOptionsDropdown.setAdapter(adapter);
    }
}
