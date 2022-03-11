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
import com.example.cse110_project.adapters.SessionsViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.session.SessionStudent;

import java.util.List;

public class SessionDetailsActivity extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView sessionDetailsRecyclerView;
    private RecyclerView.LayoutManager sessionDetailsLayoutManager;
    private SessionDetailsViewAdapter sessionDetailsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session_details);

        displayStudentsFoundInSession();
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, SessionsActivity.class);
        startActivity(intent);
    }

    public void initializeSessionNameTitle(String sessionName) {
        TextView sessionNameTitle = findViewById(R.id.session_details_title);
        sessionNameTitle.setText(sessionName);
    }

    public void displayStudentsFoundInSession() {
        Intent intent = getIntent();
        String sessionName = intent.getStringExtra("sessionName");

        initializeSessionNameTitle(sessionName);

        db = AppDatabase.singleton(this);
        List<SessionStudent> sessionStudentList = db.SessionStudentDao().getForSession(sessionName);

        sessionDetailsRecyclerView = findViewById(R.id.session_details_recycler_view);
        sessionDetailsLayoutManager = new LinearLayoutManager(this);
        sessionDetailsRecyclerView.setLayoutManager(sessionDetailsLayoutManager);
        sessionDetailsViewAdapter = new SessionDetailsViewAdapter(sessionStudentList);
        sessionDetailsRecyclerView.setAdapter(sessionDetailsViewAdapter);
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
                                initializeSessionNameTitle(sessionName);
                                for (SessionStudent ss : db.SessionStudentDao().getForSession(s.getSessionName())) {

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
}