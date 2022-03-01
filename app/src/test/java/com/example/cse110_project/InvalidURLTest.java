package com.example.cse110_project;


import static androidx.core.content.ContextCompat.startActivity;

import static org.junit.Assert.assertEquals;

import android.app.Activity;
import android.widget.Button;
import android.widget.TextView;

import androidx.lifecycle.Lifecycle;
import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class InvalidURLTest {
    @Rule
    public ActivityScenarioRule<AddLinkActivity> rule = new ActivityScenarioRule<AddLinkActivity>(AddLinkActivity.class);

    @Test
    public void test_invalidURL() {
        ActivityScenario<AddLinkActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.STARTED);

        // ignore comment
        // ignore comment
        scenario.onActivity(activity -> {
            TextView textView = activity.findViewById(R.id.editTextTextPersonName);
            textView.setText("");
            Button button = activity.findViewById(R.id.continue_button);

            button.performClick();
            assertEquals(Lifecycle.State.STARTED,scenario.getState());

        });
    }

}