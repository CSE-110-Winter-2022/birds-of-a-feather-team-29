package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.PrepopulateDatabase;

public class DeveloperSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("DeveloperSettingsActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_settings);
    }

    public void onPopulateDefaultDatabaseClicked(View view) {
        Log.d("DeveloperSettingsActivity::onPopulateDefaultDatabaseClicked()", "Non-testable method");

        PrepopulateDatabase.populateDefaultDatabase(AppDatabase.singleton(getApplicationContext()));
    }

    public void onBackClicked(View view) {
        Log.d("DeveloperSettingsActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}