package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.Constants;

public class AddLinkActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("AddLinkActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);
        setTitle(Constants.APP_VERSION);

        db = AppDatabase.singleton(getApplicationContext());
    }

    public void onContinueClicked(View view) {
        Log.d("AddLinkActivity::onContinueClicked()", "Non-testable method");

        TextView headshotURLView = findViewById(R.id.editTextTextPersonName);
        if (!(checkHeadshotURLEntry(headshotURLView.getText().toString()))) { return; }

        Intent intent = new Intent(this, PreviewPhotoActivity.class);
        startActivity(intent);
    }

    public void onSkipClicked(View view) {
        Log.d("AddLinkActivity::onSkipClicked()", "Non-testable method");

        updateHeadshotURL(Constants.DEFAULT_PIC_LINK);

        Intent intent = new Intent(this, PreviewPhotoActivity.class);
        startActivity(intent);
    }

    private boolean checkHeadshotURLEntry(String headshotURL) {
        Log.d("AddLinkActivity::checkHeadshotURLEntry()", "Non-testable method");

        if (headshotURL.isEmpty()) { return false; }

        updateHeadshotURL(headshotURL);

        return true;
    }

    private void updateHeadshotURL(String headshotURL) {
        Log.d("AddLinkActivity::updateHeadshotURL()", "Non-testable method");

        SharedPreferences preferences = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(Constants.USER_URL_KEY, headshotURL);
        editor.apply();
        db.UserDao().updateHeadshotURL(headshotURL, db.UserDao().getAll().get(0).getUserId());
    }
}


