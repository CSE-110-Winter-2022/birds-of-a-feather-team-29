package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.utilities.Constants;


public class AddLinkActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_link);
        setTitle("Birds of a Feather v0.0.1");
    }


    public void onContinueClicked(View view) {
        SharedPreferences preferences = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        TextView urlView = findViewById(R.id.editTextTextPersonName);

        editor.putString(Constants.USER_URL_KEY, urlView.getText().toString());
        editor.apply();

        if(!urlView.getText().toString().isEmpty()){
            Intent intent = new Intent(this, PreviewPhotoActivity.class);


            editor.apply();

            // FIXME: elias inserted this here
            AppDatabase db = AppDatabase.singleton(getApplicationContext());
            db.UserDao().updateHeadshotURL(url, db.UserDao().getAll().get(0).getUserId());
            // -------------------

            startActivity(intent);
        }

        }

    public void onSkipClicked(View view) {
        String default_pic = Constants.DEFAULT_PIC_LINK;

        SharedPreferences preferences = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Intent intent = new Intent(this, PreviewPhotoActivity.class);

        editor.putString(Constants.USER_URL_KEY, default_pic);
        editor.apply();


//        // FIXME: elias inserted this here
//        AppDatabase db = AppDatabase.singleton(getApplicationContext());
//        db.UserDao().updateHeadshotURL(Constants.DEFAULT_PIC_LINK, db.UserDao().getAll().get(0).getUserId());
        // -------------------

        startActivity(intent);
    }


}


