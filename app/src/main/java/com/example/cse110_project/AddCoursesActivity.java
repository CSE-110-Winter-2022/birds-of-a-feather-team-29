package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.SharedPreferencesDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.HashSet;

public class AddCoursesActivity extends AppCompatActivity {
    /** Constants */
    private final int COURSE_COUNTER_MAX = 5;
    private final int LIST_SIZE = 6;

    /** Instance variables */
    List<String> enteredCourses = new ArrayList<>(LIST_SIZE);
    private int courseCounter = 0;
    private String year;
    private String quarter;
    private String course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_courses);
        setTitle(Constants.APP_VERSION);

        Bundle extras = getIntent().getExtras();
        this.year = extras.getString("year");
        this.quarter = extras.getString("quarter");
        this.course = extras.getString("course");

        displayInitPrevCourse();
    }

    public void onEnterClicked(View view) {
        TextView enteredCourseNumber = findViewById(R.id.course_number_textview);

        // Checks if 1) user has entered > 6 courses, 2) no course was entered, and 3) course has
        // already been added to database
        if (this.courseCounter == COURSE_COUNTER_MAX) {
            Utilities.showAlert(this, Constants.ALERT, Constants.TOO_MANY_COURSES_WARNING);
            return;
        } else if (enteredCourseNumber.getText().toString().equals("")) {
            Utilities.showAlert(this, Constants.WARNING, Constants.NO_COURSE_ENTERED);
            return;
        } else if (enteredCourses.contains(enteredCourseNumber.getText().toString())) {
            Utilities.showAlert(this, Constants.WARNING, Constants.DUPLICATE_COURSE);
            return;
        }

        displayEnteredPrevCourse(this.courseCounter);
        this.courseCounter++;
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, AddCoursesMainActivity.class);
        startActivity(intent);
    }

    /**
     * Displays the course the user entered this page with
     * */
    public void displayInitPrevCourse() {
        TextView firstCourse = findViewById(R.id.prev_course_one_textview);
        Bundle extras = getIntent().getExtras();

        String fullCourseName = this.course + Constants.SPACE + extras.getString("courseNum");
        firstCourse.setText(fullCourseName);

        addToList(extras.getString(Constants.INIT_COURSE_NUMBER));
        addToDatabase(extras.getString("courseNum"));
    }

    public void displayEnteredPrevCourse(int courseIndex) {
        TextView[] courseLayouts = {findViewById(R.id.prev_course_two_textview),
                findViewById(R.id.prev_course_three_textview),
                findViewById(R.id.prev_course_four_textview),
                findViewById(R.id.prev_course_five_textview),
                findViewById(R.id.prev_course_six_textview)};
        TextView courseNumber = findViewById(R.id.course_number_textview);

        String fullCourseName = this.course + Constants.SPACE + courseNumber.getText().toString();

        courseLayouts[courseIndex].setText(fullCourseName);

        addToList(courseNumber.getText().toString());
        addToDatabase(courseNumber.getText().toString());
    }

    public void addToList(String courseNumber) {
        enteredCourses.add(courseNumber);
    }

    public void addToDatabase(String courseNum) {
        AppDatabase db = AppDatabase.singleton(getApplicationContext());
        List<UserCourse> ucl = db.UserCourseDao().getAll();

        // Checks if the course already exists in the database
        for (UserCourse uc : ucl) {
            if ((uc.getYear().equals(this.year)) && (uc.getQuarter().equals(this.quarter)) &&
                (uc.getCourse().equals(this.course)) && (uc.getCourseNum().equals(courseNum))) {
                return;
            }
        }

        db.UserCourseDao().insert(new UserCourse(this.year, this.quarter, this.course, courseNum));
    }
}