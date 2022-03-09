package com.example.cse110_project;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.databases.user.UserCourseDao;
import com.example.cse110_project.databases.user.UserDao;
import com.example.cse110_project.utilities.PrepopulateDatabase;
import com.example.cse110_project.utilities.comparators.DefaultBoFComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeMostRecentComparator;
import com.example.cse110_project.utilities.comparators.PrioritizeSmallClassesComparator;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class SortingOptionsTest_MS2_US6 {
    UserDao ud;
    UserCourseDao ucd;
    AppDatabase db;

    @Rule
    public ActivityScenarioRule<HomePageActivity> rule = new ActivityScenarioRule<>(HomePageActivity.class);

    @Before
    public void createTestDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);

        db = AppDatabase.getSingletonInstance();
        ud = db.UserDao();
        ucd = db.UserCourseDao();

        populateDatabases();
    }

    public void populateDatabases() {
        PrepopulateDatabase.populateDefaultDatabase(db);

        db.UserCourseDao().insert(new UserCourse("2017", "Fall",
                "Tiny (1-40)", "CSE", "11"));
        db.UserCourseDao().insert(new UserCourse("2017", "Fall",
                "Tiny (1-40)", "CSE", "12"));
        db.UserCourseDao().insert(new UserCourse("2017", "Fall",
                "Tiny (1-40)", "CSE", "21"));

        db.UserCourseDao().insert(new UserCourse("2018","Winter",
                "Large (150-250)", "CSE","11"));
        db.UserCourseDao().insert(new UserCourse("2018", "Winter",
                "Large (150-250)", "CSE","12"));
        db.UserCourseDao().insert(new UserCourse( "2018","Winter",
                "Large (150-250)", "CSE","21"));
        db.UserCourseDao().insert(new UserCourse("2019","Fall",
                "Large (150-250)", "CSE","100"));

        db.UserCourseDao().insert(new UserCourse("2018", "Spring",
                "Huge (250-400)", "CSE","15L"));
        db.UserCourseDao().insert(new UserCourse("2020", "Summer Session I",
                "Huge (250-400)", "CSE","191"));
        db.UserCourseDao().insert(new UserCourse("2020", "Fall",
                "Huge (250-400)", "CSE","142"));
        db.UserCourseDao().insert(new UserCourse("2020", "Fall",
                "Huge (250-400)", "CSE","112"));
        db.UserCourseDao().insert(new UserCourse("2020", "Fall",
                "Huge (250-400)", "CSE","167"));
    }

    @Test
    public void test_DefaultComparator() {
        ActivityScenario<HomePageActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.compareUserCoursesWithStudents(db);
            activity.displayBirdsOfAFeatherList(db, new DefaultBoFComparator(db.BoFCourseDao()));
            assert(activity.studentsViewAdapter.getBoFStudent(0).getName().equals("Aiko"));
            assert(activity.studentsViewAdapter.getBoFStudent(1).getName().equals("Sandy"));
            assert(activity.studentsViewAdapter.getBoFStudent(2).getName().equals("Steel"));
        });
    }

    @Test
    public void test_PrioritizeMostRecentComparator() {
        ActivityScenario<HomePageActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.compareUserCoursesWithStudents(db);
            activity.displayBirdsOfAFeatherList(db, new PrioritizeMostRecentComparator());
            assert(activity.studentsViewAdapter.getBoFStudent(0).getName().equals("Aiko"));
            assert(activity.studentsViewAdapter.getBoFStudent(1).getName().equals("Sandy"));
            assert(activity.studentsViewAdapter.getBoFStudent(2).getName().equals("Steel"));
        });
    }

    @Test
    public void test_PrioritizeSmallClassesComparator() {
        ActivityScenario<HomePageActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.compareUserCoursesWithStudents(db);
            activity.displayBirdsOfAFeatherList(db, new PrioritizeSmallClassesComparator());
            assert(activity.studentsViewAdapter.getBoFStudent(0).getName().equals("Steel"));
            assert(activity.studentsViewAdapter.getBoFStudent(1).getName().equals("Sandy"));
            assert(activity.studentsViewAdapter.getBoFStudent(2).getName().equals("Aiko"));
        });
    }
}