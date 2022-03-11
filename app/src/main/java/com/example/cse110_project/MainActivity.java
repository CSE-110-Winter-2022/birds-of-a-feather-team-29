/*
 * Source(s) used:
 *
 * Automated testing on GitHub -
 *  1) Title: gradlew: Permission Denied
 *     Link: https://stackoverflow.com/questions/17668265/gradlew-permission-denied
 *     Date: 2/4/22
 *     Source used for...: Understanding an error on GitHub actions
 *  2) Lab 2 (page 11)
 *     Link: https://docs.google.com/document/d/19VngfyPVahd7LdmW2fyWTNqWI67Sag_ylnP5Ja2wtBo/edit?usp=sharing
 *     Date: 2/4/22
 *     Source used for...: Reference on how to write a .yml file for automated testing
 * 3) Title : Identifying App Installations
 *    Link : https://android-developers.googleblog.com/2011/03/identifying-app-installations.html
 *    Data: 3/10/22
 *    Source used for: generating UUID
 * */

package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.utilities.Constants;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(Constants.APP_VERSION);

        initializeUser();
    }

    public void onStartAppClicked(View view) {
        AppDatabase db = AppDatabase.getSingletonInstance();

        if (db.UserDao().getAll().get(0).getUserFirstName() == null){
            Intent intent = new Intent(this, EnterNameActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        }
    }

    public void onDeveloperSettingsClicked(View view) {
        Intent intent = new Intent(this, DeveloperSettingsActivity.class);
        startActivity(intent);
    }

    public void initializeUser() {
        AppDatabase db = AppDatabase.singleton(getApplicationContext());
        if (db.UserDao().getAll().size() > 0) {
            Toast.makeText(MainActivity.this, "Welcome back, " + db.UserDao().getAll().
                get(0).getUserFirstName(), Toast.LENGTH_LONG).show();
            return;
        }
        db.UserDao().insert(new User(null, null));
    }
}