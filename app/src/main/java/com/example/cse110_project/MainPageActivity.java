package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.utilities.Constants;

public class MainPageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setTitle(Constants.APP_VERSION);
    }

    public void onProfileClicked(View view) {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onCoursesClicked(View view) {
        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
    }

    public void onBoFFunctionClicked(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}


