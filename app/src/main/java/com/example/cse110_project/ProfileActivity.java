package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.utilities.Constants;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("ProfileActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setTitle(Constants.APP_VERSION);

        db = AppDatabase.singleton(getApplicationContext());

        displayName();
        displayImage();
    }

    public void onBackClicked(View view) {
        Log.d("ProfileActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void displayName(){
        AppDatabase db = AppDatabase.getSingletonInstance();
        String name = db.UserDao().getAll().get(0).getUserFirstName();
        TextView textView = (TextView) findViewById(R.id.user_name);
        textView.setText(name);
    }

    public void displayImage(){
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        AppDatabase db = AppDatabase.getSingletonInstance();
        Picasso.get().load(db.UserDao().getAll().get(0).getHeadshotURL()).into(imageView);
    }
}
