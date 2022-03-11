package com.example.cse110_project;


import static org.junit.Assert.assertEquals;

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
    public ActivityScenarioRule<EnterHeadshotURLActivity> rule = new ActivityScenarioRule<EnterHeadshotURLActivity>(EnterHeadshotURLActivity.class);

    @Test
    public void test_invalidURL() {
        ActivityScenario<EnterHeadshotURLActivity> scenario = rule.getScenario();
        scenario.moveToState(Lifecycle.State.STARTED);

        scenario.onActivity(activity -> {
            TextView textView = activity.findViewById(R.id.editTextTextPersonName);
            textView.setText("");
            Button button = activity.findViewById(R.id.continue_button);

            button.performClick();
            assertEquals(Lifecycle.State.STARTED,scenario.getState());

        });
    }

}