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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.session.SessionStudent;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.utilities.Constants;

public class MainActivity extends AppCompatActivity {
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MainActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle(Constants.APP_VERSION);

        db = AppDatabase.singleton(getApplicationContext());

        // FIXME: delete, method will be moved to another class for SRP
        checkAppClosedUnexpectedly();

        createUser(db);
    }

    public void onStartAppClicked(View view) {
        Log.d("MainActivity::onStartAppClicked()", "Non-testable method");

        if (db.UserDao().getAll().get(0).getUserFirstName() == null) {
            Intent intent = new Intent(this, EnterNameActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(this, MainPageActivity.class);
            startActivity(intent);
        }
    }

    public void onDeveloperSettingsClicked(View view) {
        Log.d("MainActivity::onDeveloperSettingsClicked()", "Non-testable method");

        Intent intent = new Intent(this, DeveloperSettingsActivity.class);
        startActivity(intent);
    }

    public boolean createUser(AppDatabase db) {
        if (db.UserDao().getAll().size() > 0) { return false; }
        db.UserDao().insert(new User(null, null));
        return true;
    }

    private void checkAppClosedUnexpectedly() {
        SharedPreferences sessionSP = getSharedPreferences("WasSessionSavedProperly", MODE_PRIVATE);
        boolean sessionSavedProperly = sessionSP.getBoolean("sessionSavedProperly", true);
        SharedPreferences.Editor sessionSPEditor = sessionSP.edit();
        SharedPreferences sessionDefaultNameSP = getSharedPreferences("SessionDefaultName", MODE_PRIVATE);

        if (!sessionSavedProperly && (sessionDefaultNameSP.getString("sessionDefaultName", null) != null)) {
            AppDatabase db = AppDatabase.singleton(this);
            String sessionDefaultName = sessionDefaultNameSP.getString("sessionDefaultName", null);

            for (Session s : db.SessionDao().getAll()) {
                if (s.getSessionName().equals(sessionDefaultName)) {
                    for (BoFStudent bs : db.BoFStudentDao().getAll()) {
                        db.SessionStudentDao().insert(new SessionStudent(sessionDefaultName,
                                bs.getName(), db.BoFCourseDao().getForStudent(bs.getStudentId()).size(), bs.getUrl()));
                    }
                }
            }

            sessionSPEditor.putBoolean("sessionSavedProperly", true);
            sessionSPEditor.apply();
        }
    }
}