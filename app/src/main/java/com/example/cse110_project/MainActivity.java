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
import com.example.cse110_project.utilities.PrepopulateDatabase;
import com.example.cse110_project.utilities.SharedPreferencesDatabase;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(Constants.APP_VERSION);

        // FIXME: test line(s)
        System.out.println("--------------");
        System.out.println(AppDatabase.singleton(getApplicationContext()).
                BoFStudentDao().getAll().size());
        System.out.println(AppDatabase.singleton(getApplicationContext()).
                BoFCourseDao().getAll().size());
        System.out.println("--------------");

        SharedPreferencesDatabase.clearCurrEnteredCoursesDatabase(getApplicationContext());
        PrepopulateDatabase.populateDefaultDatabase(AppDatabase.singleton(getApplicationContext()));
        initializeUser();

        // FIXME: test line(s)
        System.out.println("--------------");
        System.out.println(AppDatabase.singleton(getApplicationContext()).
                BoFStudentDao().getAll().size());
        System.out.println(AppDatabase.singleton(getApplicationContext()).
                BoFCourseDao().getAll().size());
        System.out.println("--------------");
    }

    public void onStartAppClicked(View view) {
        AppDatabase db = AppDatabase.getSingletonInstance();
        if(db.UserDao().getAll().get(0).getUserFirstName() == null){
            Intent intent = new Intent(this, EnterNameActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        }
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