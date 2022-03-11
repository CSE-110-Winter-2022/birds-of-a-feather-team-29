package com.example.cse110_project;

import android.content.Context;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.databases.user.UserDao;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {
    AppDatabase db;
    UserDao ud;

    @Rule
    public ActivityScenarioRule<MainActivity> rule = new ActivityScenarioRule<>(MainActivity.class);

    @Before
    public void createTestDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);

        db = AppDatabase.getSingletonInstance();
        ud = db.UserDao();
    }

    @After
    public void closeTestDatabase() throws IOException {
        db.close();
    }

    @Test
    public void test_Func_createUser_No_User() {
        ActivityScenario<MainActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            assert(activity.createUser(db));
        });
    }

    @Test
    public void test_Func_createUser_User_Created() {
        ud.insert(new User("Bob", "url.link"));
        ActivityScenario<MainActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.CREATED);
        scenario.onActivity(activity -> {
            assert(!activity.createUser(db));
            assert(db.UserDao().getAll().size() > 0);
        });
    }
}
