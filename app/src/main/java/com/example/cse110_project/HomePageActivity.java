package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.prevcourses.db.AppDatabase;
import com.example.cse110_project.prevcourses.db.BoFCourse;
import com.example.cse110_project.prevcourses.db.BoFStudent;
import com.example.cse110_project.prevcourses.db.DefaultCourse;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.SharedPreferencesDatabase;


import java.util.List;
import java.util.Map;
import java.util.Set;


public class HomePageActivity extends AppCompatActivity{
    private AppDatabase db;
    protected RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    protected BoFStudentViewAdapter studentsViewAdapter;
    private boolean isfavourite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        setTitle(Constants.APP_VERSION);


        compareUserCoursesWithStudents();
        displayBirdsOfAFeatherList();
    }

    public void onStartClicked(View view) {
        TextView topLeftButton = findViewById(R.id.start_button);
        String currText = topLeftButton.getText().toString();

        if (currText.equals(Constants.START)) { topLeftButton.setText(Constants.STOP); }
        else { topLeftButton.setText(Constants.START); }
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, AddCoursesMainActivity.class);
        startActivity(intent);
    }

    public void onMockNearbyMessagesClicked(View view) {
        Intent intent = new Intent(this, MockNearbyMessagesActivity.class);
        startActivity(intent);
    }

    /**
     * Adds the students and courses that the User has shared a class with to the database by
     * cross-checking the User's courses with all the pre-populated courses in the database
     * */
    public void compareUserCoursesWithStudents() {
        db = AppDatabase.singleton(getApplicationContext());
        SharedPreferences sp = SharedPreferencesDatabase.getDatabase(getApplicationContext(),
                Constants.MAIN_USER_COURSE_DB);
        Map<String, ?> userCoursesMap = sp.getAll();
        Object[] keysArr = userCoursesMap.keySet().toArray();
        List<DefaultCourse> defaultCourseList;
        Set<String> userCourses;
        String[] studentCourseSplit, userKeySplit;
        String year, quarter;
        boolean skipCourse;
        int studentId;

        // Cross-checks the classes entered by the user with the students pre-populated into the
        // database
        for (Object o : keysArr) {
            userKeySplit = ((String)o).split(Constants.COMMA);
            userCourses = (Set<String>) userCoursesMap.get(o);
            defaultCourseList = db.DefaultCourseDao().getAll();

            // Iterates through the course numbers entered by the user for a specific year, quarter,
            // and course
            for (String uC : userCourses) {
                // Iterates through all the remaining courses in the pre-populated database
                for (DefaultCourse cL : defaultCourseList) {
                    skipCourse = false;
                    studentCourseSplit = cL.getCourse().split(Constants.SPACE);
                    year = cL.getYear();
                    quarter = cL.getQuarter();

                    if (cL.getYear().equals(userKeySplit[0]) && cL.getQuarter().equals(userKeySplit[1])
                            && studentCourseSplit[0].equals(userKeySplit[2])
                            && studentCourseSplit[1].equals(uC)) {
                        studentId = cL.getStudentId();

                        // Checks whether or not the student already has the course associated with them
                        // in the database
                        if (db.DefaultStudentDao().get(studentId).getEncountered()) {
                            List<BoFCourse> studentCourses = db.BoFCourseDao().getForStudent(studentId);
                            for (BoFCourse course : studentCourses) {
                                if (course.getCourse().equals(cL.getCourse())) {
                                    skipCourse = true;
                                    break;
                                }
                            }
                            if (skipCourse) { continue; }
                        }

                        // If the student has not been added to the BoF database, then we add the
                        // student to the BoF database
                        if (!(db.DefaultStudentDao().get(studentId).getEncountered())) {
                            BoFStudent ns = new BoFStudent(studentId, db.DefaultStudentDao().get(studentId).getName());
                            db.BoFStudentDao().insert(ns);
                            db.DefaultStudentDao().updateEncountered(true, studentId);
                        }

                        studentId = db.BoFStudentDao().getBasedOnPrevId(studentId).getStudentId();
                        BoFCourse nc = new BoFCourse(studentId, year, quarter, cL.getCourse());
                        db.BoFCourseDao().insert(nc);
                    }
                }
            }
        }
    }

    public void displayBirdsOfAFeatherList() {
        db = AppDatabase.singleton(getApplicationContext());
        List<BoFStudent> students = db.BoFStudentDao().getAll();

        studentsRecyclerView = findViewById(R.id.students_view);

        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);

        studentsViewAdapter = new BoFStudentViewAdapter(students, db.BoFCourseDao());
//        studentsViewAdapter.clear();
//        studentsViewAdapter = new BoFStudentViewAdapter(students, db.BoFCourseDao());
        studentsRecyclerView.setAdapter(studentsViewAdapter);
    }

    public void onToggleStar(View view) {
        TextView favouriteStar = (TextView) view;
        if(!isfavourite){
            // if the star is not already selected and you select it
            isfavourite = true;
            favouriteStar.setTextColor(Color.parseColor("#FFD600"));
            // add the student to database
        }else{
            // if the star is already selected and you unselect it
            isfavourite = false;
            favouriteStar.setTextColor(Color.parseColor("#9E9E9E"));
            // remove student from database
        }
    }
}