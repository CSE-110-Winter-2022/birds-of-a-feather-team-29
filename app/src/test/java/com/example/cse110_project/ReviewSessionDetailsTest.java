package com.example.cse110_project;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.session.SessionDao;
import com.example.cse110_project.databases.session.SessionStudent;
import com.example.cse110_project.databases.session.SessionStudentDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class ReviewSessionDetailsTest {
    AppDatabase db;
    SessionDao sd;
    SessionStudentDao ssd;

    @Rule
    public ActivityScenarioRule<SessionDetailsActivity> rule = new ActivityScenarioRule<>(SessionDetailsActivity.class);

    @Before
    public void createTestDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);

        db = AppDatabase.getSingletonInstance();
        sd = db.SessionDao();
        ssd = db.SessionStudentDao();
    }

    @After
    public void closeTestDatabase() throws IOException {
        db.close();
    }

    @Test
    public void test_Zero_Students_Found_One_Session() {
        sd.insert(new Session("EXAMPLE_SESSION"));

        ActivityScenario<SessionDetailsActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.displayStudentsFoundInSession(ssd.getForSession("EXAMPLE_SESSION"));
            assert(activity.sessionDetailsViewAdapter.getItemCount() == 0);
        });
    }

    @Test
    public void test_Non_Zero_Students_Found_One_Session() {
        sd.insert(new Session("EXAMPLE_SESSION"));
        ssd.insert(new SessionStudent("EXAMPLE_SESSION", "Bob", 1,"url"));
        ssd.insert(new SessionStudent("EXAMPLE_SESSION", "Hailey", 2,"url"));
        ssd.insert(new SessionStudent("EXAMPLE_SESSION", "Yoda", 3,"url"));

        ActivityScenario<SessionDetailsActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.displayStudentsFoundInSession(ssd.getForSession("EXAMPLE_SESSION"));
            assert(activity.sessionDetailsViewAdapter.getItemCount() == 3);
            assert(activity.sessionDetailsViewAdapter.getSessionStudent(0).getSessionStudentName().equals("Bob"));
            assert(activity.sessionDetailsViewAdapter.getSessionStudent(1).getSessionStudentName().equals("Hailey"));
            assert(activity.sessionDetailsViewAdapter.getSessionStudent(2).getSessionStudentName().equals("Yoda"));
        });
    }
}