package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cse110_project.utilities.Constants;
import com.squareup.picasso.Picasso;

public class PreviewPhotoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_photo);
        setTitle(Constants.APP_VERSION);
        displayImage();
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, AddLinkActivity.class);
        startActivity(intent);
    }

    public void onConfirmClicked(View view) {
        // This is the sharePreference that should be use in compare method
        SharedPreferences newPreferences = getSharedPreferences("NameWithURL",MODE_PRIVATE);
        SharedPreferences.Editor newEditor = newPreferences.edit();

        SharedPreferences preferences = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor userInfoEditor = preferences.edit();

        newEditor.putString(preferences.getString(Constants.USER_FIRST_NAME, "name not found"),
                preferences.getString(Constants.USER_URL_KEY, "url not found"));
        newEditor.apply();
        userInfoEditor.clear();
        userInfoEditor.apply();
        Intent intent = new Intent(this, AddCoursesMainActivity.class);
        startActivity(intent);
    }

    public void displayImage() {
        SharedPreferences preferences = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.get().load(preferences.getString(Constants.USER_URL_KEY,
                Constants.IMAGE_NOT_FOUND_PIC)).into(imageView);
    }
}