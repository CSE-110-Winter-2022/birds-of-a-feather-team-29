package com.example.cse110_project;

import android.content.Context;
import android.widget.ImageView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.bof.BoFStudentDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class StudentDetailTest {
    BoFStudentDao mainSD;
    BoFCourseDao mainCD;
    AppDatabase db;

    @Rule
    public ActivityScenarioRule<StudentDetailActivity> rule = new ActivityScenarioRule<>(StudentDetailActivity.class);

    @Before
    public void createTestDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);
        db = AppDatabase.getSingletonInstance();
        mainSD = db.BoFStudentDao();
        mainCD = db.BoFCourseDao();
    }

    @After
    public void deleteTestDatabase() throws IOException { db.close(); }

    @Test
    public void defaultTest() {
        assert(true);
    }

    @Test
    public void test_Display_One_Previous_Course_Shared_With_User() {
        mainSD.insert(new BoFStudent("Steel"));
        BoFStudent student = db.BoFStudentDao().getAll().get(0);
        mainCD.insert(new BoFCourse(student.getStudentId(), "2018", "Fall",
                "Tiny (1-40)", "CSE", "21"));

        ActivityScenario<StudentDetailActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.displaySharedCourses(1);
            assert(activity.getNumOfCoursesDisplayed() == 1);
        });
    }

    @Test
    public void test_Display_Five_Previous_Courses_Shared_With_User() {
        mainSD.insert(new BoFStudent("Steel"));
        BoFStudent student = db.BoFStudentDao().getAll().get(0);
        mainCD.insert(new BoFCourse(student.getStudentId(), "2018", "Fall",
                "Tiny (1-40)", "CSE","21"));
        mainCD.insert(new BoFCourse(student.getStudentId(), "2019", "Winter",
                "Tiny (1-40)", "CSE","100"));
        mainCD.insert(new BoFCourse(student.getStudentId(), "2019", "Winter",
                "Tiny (1-40)", "CSE","101"));
        mainCD.insert(new BoFCourse(student.getStudentId(), "2019", "Winter",
                "Tiny (1-40)", "CSE","105"));
        mainCD.insert(new BoFCourse(student.getStudentId(), "2019", "Spring",
                "Tiny (1-40)", "CSE","110"));

        ActivityScenario<StudentDetailActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.displaySharedCourses(1);
            assert(activity.getNumOfCoursesDisplayed() == 5);
        });
    }

    @Test
    public void test_Student_Has_Default_Head_Shot() {
        mainSD.insert(new BoFStudent("Steel"));
        BoFStudent student = db.BoFStudentDao().getAll().get(0);
        mainCD.insert(new BoFCourse(student.getStudentId(), "2018", "Fall",
                "Tiny (1-40)", "CSE","21"));

        ActivityScenario<StudentDetailActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            ImageView headShot = activity.findViewById(R.id.head_shot_image_view);
            assert(headShot.getDrawable() != null);
        });
    }
}
