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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.SharedPreferencesDatabase;

import java.util.HashSet;

public class AddCoursesMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_courses);
        setTitle(Constants.APP_VERSION);

        addCoursesToDatabase();
        initYearDropdown();
        initQuarterDropdown();
    }

    public void onEnterClicked(View view) {
        TextView subject = findViewById(R.id.enter_subject_textview);
        TextView courseNumber = findViewById(R.id.enter_course_textview);

        // Checks if the user has not entered a course and corresponding course number
        if ((subject.getText().toString().equals(""))
                || (courseNumber.getText().toString().equals(""))) {
            Utilities.showAlert(this, Constants.WARNING,
                    Constants.NO_SUB_OR_COURSE_NUMBER_WARNING);
            return;
        }

//        SharedPreferences pref = SharedPreferencesDatabase.getDatabase(getApplicationContext(),
//                Constants.CURR_ENTERED_COURSES_DB);
//        SharedPreferences.Editor editor = pref.edit();
        Spinner year = findViewById(R.id.year_dropdown_container);
        Spinner quarter = findViewById(R.id.quarter_dropdown_container);

        // Retrieves the selected year and quarter from the dropdown menus and stores them as
        // extras
//        editor.clear();
//        editor.putString(Constants.YEAR_KEY, s1.getSelectedItem().toString());
//        editor.putString(Constants.QTR_KEY, s2.getSelectedItem().toString());
//        editor.apply();

        Intent intent = new Intent(this, AddCoursesActivity.class);

        intent.putExtra("year", year.getSelectedItem().toString());
        intent.putExtra("quarter", quarter.getSelectedItem().toString());
        intent.putExtra("course", subject.getText().toString());
        intent.putExtra("courseNum", courseNumber.getText().toString());

        startActivity(intent);
    }

    public boolean onDoneClicked(View view) {
        SharedPreferences userCourseInfo = SharedPreferencesDatabase.getDatabase(getApplicationContext(),
                Constants.MAIN_USER_COURSE_DB);

        // Checks if the user has entered data into the database
        if (userCourseInfo.getAll().isEmpty()) {
            Utilities.showAlert(this, Constants.WARNING, Constants.NO_CLASSES_ENTERED_WARNING);
            return false;
        }

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);

        return true;
    }

    /**
     * Adding the courses entered by the user from AddCoursesActivity.class in the form of
     * extras into the appropriate SharedPreferences database
     * */
    public boolean addCoursesToDatabase() {
        Bundle extras = getIntent().getExtras();

        if (extras == null) { return false; }

        // Getting the courses entered by the user from the "current entered classes" database
        // in the form of a set
        String subjectKey = extras.getString(Constants.SUBJECT_KEY);
        SharedPreferences curr = SharedPreferencesDatabase.getDatabase(getApplicationContext(),
                Constants.CURR_ENTERED_COURSES_DB);
        HashSet<String> set = (HashSet<String>) curr.getStringSet(subjectKey, null);

        if (set == null) { return false; }

        SharedPreferences userCourseInfo = SharedPreferencesDatabase.getDatabase(getApplicationContext(),
                Constants.MAIN_USER_COURSE_DB);
        SharedPreferences.Editor mainEditor = userCourseInfo.edit();

        // Adding the set of courses received above into a "main user class info" database as a value
        // mapped to a key representing the year, quarter, and subject
        String completeKey = curr.getString(Constants.YEAR_KEY, null) + Constants.COMMA +
                curr.getString(Constants.QTR_KEY, null) + Constants.COMMA + subjectKey;

        if (userCourseInfo.contains(completeKey)) {
            HashSet<String> set2 = (HashSet<String>) userCourseInfo.getStringSet(completeKey, null);
            set.addAll(set2);
        }

        mainEditor.putStringSet(completeKey, set);
        mainEditor.apply();

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
}