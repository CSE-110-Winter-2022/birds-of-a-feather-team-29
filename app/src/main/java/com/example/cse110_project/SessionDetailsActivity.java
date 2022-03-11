package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cse110_project.adapters.SessionDetailsViewAdapter;
import com.example.cse110_project.adapters.SessionsViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
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

    public void displayStudentsFoundInSession() {
        Intent intent = getIntent();
        String sessionName = intent.getStringExtra("sessionName");

        db = AppDatabase.singleton(this);
        List<SessionStudent> sessionStudentList = db.SessionStudentDao().getForSession(sessionName);

        sessionDetailsRecyclerView = findViewById(R.id.session_details_recycler_view);
        sessionDetailsLayoutManager = new LinearLayoutManager(this);
        sessionDetailsRecyclerView.setLayoutManager(sessionDetailsLayoutManager);
        sessionDetailsViewAdapter = new SessionDetailsViewAdapter(sessionStudentList);
        sessionDetailsRecyclerView.setAdapter(sessionDetailsViewAdapter);
    }
}