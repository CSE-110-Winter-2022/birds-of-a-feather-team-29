package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cse110_project.adapters.SessionsViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.session.Session;

import java.util.List;

public class SessionsActivity extends AppCompatActivity {
    private AppDatabase db;
    private RecyclerView sessionsRecyclerView;
    private RecyclerView.LayoutManager sessionsLayoutManager;
    private SessionsViewAdapter sessionsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        displaySessions();
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    private void displaySessions() {
        db = AppDatabase.singleton(getApplicationContext());
        List<Session> sessionList = db.SessionDao().getAll();
        sessionsRecyclerView = findViewById(R.id.sessions_recycler_view);
        sessionsLayoutManager = new LinearLayoutManager(this);
        sessionsRecyclerView.setLayoutManager(sessionsLayoutManager);
        sessionsViewAdapter = new SessionsViewAdapter(sessionList);
        sessionsRecyclerView.setAdapter(sessionsViewAdapter);
    }
}