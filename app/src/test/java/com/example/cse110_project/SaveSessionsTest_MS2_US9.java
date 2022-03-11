package com.example.cse110_project;

import android.content.Context;
import android.view.View;
import android.widget.Button;

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
public class SaveSessionsTest_MS2_US9 {
    AppDatabase db;
    SessionDao sd;
    SessionStudentDao ssd;

    @Rule
    public ActivityScenarioRule<HomePageActivity> rule = new ActivityScenarioRule<>(HomePageActivity.class);

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
    public void test_Session_Saved_Zero_Students_Found() {
        ActivityScenario<HomePageActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.onSearchButtonClicked(new View(activity.getApplicationContext()), db);
            assert(sd.getAll().size() == 1);
            assert(ssd.getForSession(sd.getAll().get(0).getSessionName()).size() == 0);
        });
    }

    @Test
    public void test_Session_Saved_With_Default_Name() {
        ActivityScenario<HomePageActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            activity.onSearchButtonClicked(new View(activity.getApplicationContext()), db);
            String sessionDefaultName = sd.getAll().get(0).getSessionName();
            assert((sessionDefaultName.charAt(4) == '/') && (sessionDefaultName.charAt(7) == '/')
                    && (sessionDefaultName.charAt(10) == ' ') && (sessionDefaultName.charAt(13) == ':')
                    && (sessionDefaultName.charAt(16) == ':'));
        });
    }
}
