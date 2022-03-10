package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.PrepopulateDatabase;

public class DeveloperSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_settings);
    }


    public void onPopulateDefaultDatabaseClicked(View view) {
        PrepopulateDatabase.populateDefaultDatabase(AppDatabase.singleton(getApplicationContext()));
    }
}