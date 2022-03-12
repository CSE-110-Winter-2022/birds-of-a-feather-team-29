package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.utilities.Constants;

public class MainPageActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainPageActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setTitle(Constants.APP_VERSION);
    }

    public void onProfileClicked(View view) {
        Log.d("MainPageActivity::onProfileClicked()", "Non-testable method");

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void onCoursesClicked(View view) {
        Log.d("MainPageActivity::onCoursesClicked()", "Non-testable method");

        Intent intent = new Intent(this, CoursesActivity.class);
        startActivity(intent);
    }

    public void onFavoriteClicked(View view) {
        Log.d("MainPageActivity::onFavoriteClicked()", "Non-testable method");

        Intent intent = new Intent(this, FavoriteActivity.class);
        startActivity(intent);
    }

    public void onBoFFunctionClicked(View view) {
        Log.d("MainPageActivity::onBoFFunctionClicked()", "Non-testable method");

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    public void onSavedSessionsClicked(View view) {
        Log.d("MainPageActivity::onSavedSessionsClicked()", "Non-testable method");

        Intent intent = new Intent(this, SessionsActivity.class);
        startActivity(intent);
    }
}


