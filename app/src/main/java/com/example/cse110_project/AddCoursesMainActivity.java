/*
 * Source(s):
 *
 * How to implement a dropdown menu -
 *  1) Title: How to Add a Dropdown Menu in Android Studio
 *     Link: https://code.tutsplus.com/tutorials/how-to-add-a-dropdown-menu-in-android-studio--cms-37860
 *     Date: January 1st - February 2nd, 2022
 *     Source used for...: Guidance on how to implement a dropdown menu
 *  2) Spinners
 *     Link: https://developer.android.com/guide/topics/ui/controls/spinner
 *     Date: January 1st - February 2nd, 2022
 *     Source used for...: Understanding Spinners
 * */

package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.Utilities;

public class AddCoursesMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_courses);
        setTitle(Constants.APP_VERSION);

        initYearDropdown();
        initQuarterDropdown();
        initClassSizeDropdown();
    }

    public void onEnterClicked(View view) {
        TextView subject = findViewById(R.id.enter_subject_textview);
        TextView courseNumber = findViewById(R.id.enter_course_textview);

        // Checks if the user has not entered a course and corresponding course number
        if ((subject.getText().toString().equals("")) || (courseNumber.getText().toString().equals(""))) {
            Utilities.showAlert(this, Constants.WARNING, Constants.NO_SUB_OR_COURSE_NUMBER_WARNING);
            return;
        }

        Spinner year = findViewById(R.id.year_dropdown_container);
        Spinner quarter = findViewById(R.id.quarter_dropdown_container);
        Spinner classSize = findViewById(R.id.class_size_dropdown_container);

        Intent intent = new Intent(this, AddCoursesActivity.class);

        intent.putExtra("year", year.getSelectedItem().toString());
        intent.putExtra("quarter", quarter.getSelectedItem().toString());
        intent.putExtra("classSize", classSize.getSelectedItem().toString());
        intent.putExtra("course", subject.getText().toString());
        intent.putExtra("courseNum", courseNumber.getText().toString());

        startActivity(intent);
    }

    public boolean onDoneClicked(View view) {
        AppDatabase db = AppDatabase.singleton(getApplicationContext());

        // Checks if the user has entered data into the database
        if (db.UserCourseDao().getAll().size() < 1) {
            Utilities.showAlert(this, Constants.WARNING, Constants.NO_CLASSES_ENTERED_WARNING);
            return false;
        }

        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);

        return true;
    }

    public void initYearDropdown() {
        Spinner yearDropdown = findViewById(R.id.year_dropdown_container);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.academic_years, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        yearDropdown.setAdapter(adapter);
    }

    public void initQuarterDropdown() {
        Spinner quarterDropdown = findViewById(R.id.quarter_dropdown_container);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.academic_quarters, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        quarterDropdown.setAdapter(adapter);
    }

    public void initClassSizeDropdown() {
        Spinner classSizeDropdown = findViewById(R.id.class_size_dropdown_container);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.class_size, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        classSizeDropdown.setAdapter(adapter);
    }
}