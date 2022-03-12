package com.example.cse110_project;

import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class AddCoursesMainTest {
    @Rule
    public ActivityScenarioRule<EnterCourseInformationActivity> rule = new ActivityScenarioRule<>(EnterCourseInformationActivity.class);

    @Test
    public void test_Func_initYearDropdown() {
        ActivityScenario<EnterCourseInformationActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            Spinner yearDropdown = activity.findViewById(R.id.year_dropdown_container);
            assert(yearDropdown.getSelectedItem().toString().equals("2017"));
        });
    }

    @Test
    public void test_Func_initQuarterDropdown() {
        ActivityScenario<EnterCourseInformationActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            Spinner quarterDropdown = activity.findViewById(R.id.quarter_dropdown_container);
            assert(quarterDropdown.getSelectedItem().toString().equals("Fall"));
        });
    }

    @Test
    public void test_Func_initClassSizeDropdown() {
        ActivityScenario<EnterCourseInformationActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            Spinner classSizeDropdown = activity.findViewById(R.id.class_size_dropdown_container);
            assert(classSizeDropdown.getSelectedItem().toString().equals("Tiny (1-40)"));
        });
    }

    @Test
    public void test_Subject_TextView_Is_Initially_Empty() {
        ActivityScenario<EnterCourseInformationActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            TextView subject = activity.findViewById(R.id.enter_subject_textview);
            assert(subject.getText().toString().equals(""));
        });
    }

    @Test
    public void test_Course_Number_TextView_Is_Initially_Empty() {
        ActivityScenario<EnterCourseInformationActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            TextView courseEntry = activity.findViewById(R.id.enter_course_textview);
            assert(courseEntry.getText().toString().equals(""));
        });
    }
}
