package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.Constants;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("CoursesActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        setTitle(Constants.APP_VERSION);
        displayCourse();
    }

    public void onBackClicked(View view) {
        Log.d("CoursesActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void onEnterClicked(View view) {
        Log.d("CoursesActivity::onEnterClicked()", "Non-testable method");

        Intent intent = new Intent(this, EnterCourseInformationActivity.class);
        startActivity(intent);
    }

    public void displayCourse() {
        Log.d("CoursesActivity::displayCourse()", "Non-testable method");

        AppDatabase db = AppDatabase.getSingletonInstance();

        ArrayList<String> courseList = new ArrayList<>();

        for(int i = 0; i < db.UserCourseDao().getAll().size(); i++){
            String year = db.UserCourseDao().getAll().get(i).getYear();
            String quarter = db.UserCourseDao().getAll().get(i).getQuarter();
            String subject = db.UserCourseDao().getAll().get(i).getCourse();
            String number = db.UserCourseDao().getAll().get(i).getCourseNum();
            String size = db.UserCourseDao().getAll().get(i).getClassSize();
            String fullCourseName = year + " " + quarter + " " + subject + " " + number + " " + size;
            courseList.add(fullCourseName);
        }

        TextView[] courseLayouts = {findViewById(R.id.prev_course_one_textview),
                findViewById(R.id.prev_course_two_textview),
                findViewById(R.id.prev_course_three_textview),
                findViewById(R.id.prev_course_four_textview),
                findViewById(R.id.prev_course_five_textview),
                findViewById(R.id.prev_course_six_textview),
                findViewById(R.id.prev_course_seven_textview),
                findViewById(R.id.prev_course_eight_textview),
                findViewById(R.id.prev_course_nine_textview),
                findViewById(R.id.prev_course_ten_textview)};

        for(int i = 0; i < courseList.size() && i < 10; i++) {
            courseLayouts[i].setText(courseList.get(i));
        }
    }
}
