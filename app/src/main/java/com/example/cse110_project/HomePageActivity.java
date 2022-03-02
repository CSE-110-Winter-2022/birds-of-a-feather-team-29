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

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;
import com.example.cse110_project.databases.user.UserCourse;
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

        if (currText.equals(Constants.START)) {
            topLeftButton.setText(Constants.STOP);
            //MockNearbyMessagesActivity.onStart();
        }
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
     * Adds the students and courses that the User has shared a class with to the BoF database by
     * cross-checking the User's courses with all the pre-populated courses in the database
     * */
    public void compareUserCoursesWithStudents() {
        db = AppDatabase.singleton(getApplicationContext());
        List<UserCourse> ucl = db.UserCourseDao().getAll();
        List<DefaultCourse> dcl;
        List<BoFStudent> bsl;
        String year;
        String quarter;
        String classSize;
        String course;
        String courseNum;
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
                            next = true;
                            break;
                        }
                    }

                    if (next) {
                        db.DefaultCourseDao().updateCourseAdded(true, dc.getCourseId());
                        continue;
                    }

                    // If student does not exist in the BoF student database
                    db.BoFStudentDao().insert(new BoFStudent(currMatchingStudent.getName()));
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

    public void displayBirdsOfAFeatherList() {
        db = AppDatabase.singleton(getApplicationContext());
        List<BoFStudent> students = db.BoFStudentDao().getAll();

        studentsRecyclerView = findViewById(R.id.students_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
        studentsViewAdapter = new BoFStudentViewAdapter(students, db.BoFCourseDao());
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