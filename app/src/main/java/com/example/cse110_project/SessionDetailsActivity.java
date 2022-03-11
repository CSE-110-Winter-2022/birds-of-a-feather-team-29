package com.example.cse110_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        db = AppDatabase.singleton(getApplicationContext());
        startSessionDetails();
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, SessionsActivity.class);
        startActivity(intent);
    }

    public void onChangeSessionNameClicked(View view) {
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

                        // FIXME: fix the stuff behind the pop-up moving
                        for (Session s : db.SessionDao().getAll()) {
                            if (currSessionName.equals(s.getSessionName())) {
                                createSessionNameTitle(sessionName);
                                for (SessionStudent ss : db.SessionStudentDao().getForSession(s.getSessionName())) {
                                    db.SessionStudentDao().updateSessionNameOfSessionStudent(sessionName, s.getSessionName());
                                }
                                db.SessionDao().updateSessionName(sessionName, s.getSessionName());
                                return;
                            }
                        }
                    }
                });

        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    public void createSessionNameTitle(String sessionName) {
        TextView sessionNameTitle = findViewById(R.id.session_details_title);
        sessionNameTitle.setText(sessionName);
    }

    private void startSessionDetails() {
        String sessionName = getSessionName("sessionName");
        if (sessionName == null) { return; }
        createSessionNameTitle(sessionName);
        displayStudentsFoundInSession(getSessionStudentList(sessionName));
    }

    private String getSessionName(String sessionNameKey) {
        Intent intent = getIntent();
        return intent.getStringExtra(sessionNameKey);
    }

    private List<SessionStudent> getSessionStudentList(String sessionName) {
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