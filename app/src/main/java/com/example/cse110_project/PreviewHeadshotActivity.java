package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.cse110_project.utilities.Constants;
import com.squareup.picasso.Picasso;

public class PreviewHeadshotActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("PreviewHeadshotActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        setTitle(Constants.APP_VERSION);

        displayImage();
    }

    public void onBackClicked(View view) {
        Log.d("PreviewHeadshotActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, EnterHeadshotURLActivity.class);
        startActivity(intent);
    }

    public void onConfirmClicked(View view) {
        Log.d("PreviewHeadshotActivity::onConfirmClicked()", "Non-testable method");

        Intent intent = new Intent(this, AddCoursesMainActivity.class);
        startActivity(intent);
    }

    private void displayImage() {
        Log.d("PreviewHeadshotActivity::displayImage()", "Non-testable method");

        SharedPreferences preferences = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
        ImageView imageView = findViewById(R.id.imageView);
        Picasso.get().load(preferences.getString(Constants.USER_URL_KEY,
                Constants.IMAGE_NOT_FOUND_PIC)).into(imageView);
    }
}