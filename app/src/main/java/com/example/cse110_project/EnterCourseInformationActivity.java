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
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.SpinnerCreation;
import com.example.cse110_project.utilities.Utilities;

public class EnterCourseInformationActivity extends AppCompatActivity {
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("EnterCourseInformationActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_courses);
        setTitle(Constants.APP_VERSION);

        db = AppDatabase.singleton(getApplicationContext());

        createDropdownMenus();
    }

    public void onEnterClicked(View view) {
        Log.d("EnterCourseInformationActivity::onEnterClicked()", "Non-testable method");

        TextView subject = findViewById(R.id.enter_subject_textview);
        TextView courseNumber = findViewById(R.id.enter_course_textview);

        if (checkMissingEntry(subject, courseNumber)) { return; }

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
        Log.d("EnterCourseInformationActivity::onDoneClicked()", "Non-testable methods");

        AppDatabase db = AppDatabase.singleton(getApplicationContext());

        if (checkDatabaseEmpty()) {
        }

        // Checks if the user has entered data into the database
        if (db.UserCourseDao().getAll().size() < 1) {
            Utilities.showAlert(this, Constants.WARNING, Constants.NO_CLASSES_ENTERED_WARNING);
            return false;
        }

        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);

        return true;
    }

    private boolean checkDatabaseEmpty() {
        Log.d("EnterCourseInformationActivity::checkDatabaseEmpty()", "Non-testable method");

        if (db.UserCourseDao().getAll().size() > 0) { return false; }

        Utilities.showAlert(this, Constants.WARNING, Constants.NO_CLASSES_ENTERED_WARNING);

        return false;
    }

    private boolean checkMissingEntry(TextView subject, TextView courseNumber) {
        Log.d("EnterCourseInformationActivity::checkMissingEntry()", "Non-testable method");

        if (!(subject.getText().toString().equals(""))
                && !(courseNumber.getText().toString().equals(""))) {
            return false;
        }

        Utilities.showAlert(this, Constants.WARNING, Constants.NO_SUB_OR_COURSE_NUMBER_WARNING);

        return true;
    }

    private void createDropdownMenus() {
        Log.d("EnterCourseInformationActivity::createDropdownMenus()", "Non-testable method");

        Spinner[] containers = { findViewById(R.id.year_dropdown_container),
                findViewById(R.id.quarter_dropdown_container),
                findViewById(R.id.class_size_dropdown_container) };
        int[] arrays = { R.array.academic_years, R.array.academic_quarters, R.array.class_size };

        for (int i = 0; i < containers.length; i++) {
            SpinnerCreation.createSpinner(this, containers[i], arrays[i]);
        }
    }
}