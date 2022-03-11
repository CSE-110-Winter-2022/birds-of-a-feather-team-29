package com.example.cse110_project;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
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
public class ReviewSessionsTest_MS2_US10 {
    AppDatabase db;
    SessionDao sd;
    SessionStudentDao ssd;

    @Rule
    public ActivityScenarioRule<SessionsActivity> rule = new ActivityScenarioRule<>(SessionsActivity.class);

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
    public void test_Zero_Sessions_Saved() {
        ActivityScenario<SessionsActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.displaySessions(sd.getAll());
            assert(activity.sessionsViewAdapter.getItemCount() == 0);
        });
    }

    @Test
    public void test_Non_Zero_Sessions_Saved() {
        sd.insert(new Session("EXAMPLE_SESSION"));
        sd.insert(new Session("EXAMPLE_SESSION (1)"));
        sd.insert(new Session("EXAMPLE_SESSION (2)"));

        ActivityScenario<SessionsActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.displaySessions(sd.getAll());
            assert(activity.sessionsViewAdapter.getItemCount() == 3);
            assert(activity.sessionsViewAdapter.getSession(0).getSessionName().equals("EXAMPLE_SESSION"));
            assert(activity.sessionsViewAdapter.getSession(1).getSessionName().equals("EXAMPLE_SESSION (1)"));
            assert(activity.sessionsViewAdapter.getSession(2).getSessionName().equals("EXAMPLE_SESSION (2)"));

        });
    }
}