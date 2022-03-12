package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cse110_project.adapters.SessionDetailsViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.session.SessionStudent;

import java.util.List;

public class SessionDetailsActivity extends AppCompatActivity {
    protected AppDatabase db;
    protected RecyclerView sessionDetailsRecyclerView;
    protected RecyclerView.LayoutManager sessionDetailsLayoutManager;
    protected SessionDetailsViewAdapter sessionDetailsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SessionDetailsActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        db = AppDatabase.singleton(getApplicationContext());
        createTitle();
        startSessionDetails();
    }

    public void onBackClicked(View view) {
        Log.d("SessionDetailsActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, SessionsActivity.class);
        startActivity(intent);
    }

    private void createTitle() {
        TextView currSessionTitle = findViewById(R.id.session_details_title);
        currSessionTitle.setText(getSessionName("sessionName"));
    }

    public void onChangeSessionNameClicked(View view) {
        Log.d("SessionDetailsActivity::onChangeSessionNameClicked()", "Non-testable method");

        db = AppDatabase.singleton(this);
        TextView currSessionTitle = findViewById(R.id.session_details_title);
        String currSessionName = currSessionTitle.getText().toString();

        EditText sessionNameEntry = new EditText(this);
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder
                .setMessage("Enter new name of session below:")
                .setView(sessionNameEntry)
                .setPositiveButton("Save", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String sessionName = sessionNameEntry.getText().toString();

                        for (Session s : db.SessionDao().getAll()) {
                            if (currSessionName.equals(s.getSessionName())) {
                                db.SessionDao().updateSessionName(sessionName, s.getSessionName());
                                db.SessionStudentDao().updateSessionNameOfSessionStudent(sessionName, s.getSessionName());
                                return;
                            }
                        }
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void startSessionDetails() {
        Log.d("SessionDetailsActivity::startSessionDetails()", "Non-testable method");

        String sessionName = getSessionName("sessionName");
        if (sessionName == null) { return; }
        displayStudentsFoundInSession(getSessionStudentList(sessionName));
    }

    private String getSessionName(String sessionNameKey) {
        Intent intent = getIntent();
        return intent.getStringExtra(sessionNameKey);
    }

    private List<SessionStudent> getSessionStudentList(String sessionName) {
        Log.d("SessionDetailsActivity::getSessionStudentList()", "Non-testable method");

        return db.SessionStudentDao().getForSession(sessionName);
    }

    public void displayStudentsFoundInSession(List<SessionStudent> sessionStudentList) {
        sessionDetailsRecyclerView = findViewById(R.id.session_details_recycler_view);
        sessionDetailsLayoutManager = new LinearLayoutManager(this);
        sessionDetailsRecyclerView.setLayoutManager(sessionDetailsLayoutManager);
        sessionDetailsViewAdapter = new SessionDetailsViewAdapter(sessionStudentList);
        sessionDetailsRecyclerView.setAdapter(sessionDetailsViewAdapter);
    }
}