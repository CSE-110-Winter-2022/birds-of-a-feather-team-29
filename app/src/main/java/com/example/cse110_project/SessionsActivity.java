package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.cse110_project.adapters.SessionsViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.session.SessionStudent;

import java.util.List;

public class SessionsActivity extends AppCompatActivity {
    protected AppDatabase db;
    protected RecyclerView sessionsRecyclerView;
    protected RecyclerView.LayoutManager sessionsLayoutManager;
    protected SessionsViewAdapter sessionsViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("SessionsActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions);

        db = AppDatabase.singleton(getApplicationContext());
        displaySessions(getSessionsList());
    }

    public void onBackClicked(View view) {
        Log.d("SessionsActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    private List<Session> getSessionsList() {
        Log.d("SessionsActivity::getSessionsList()", "Non-testable method");

        return db.SessionDao().getAll();
    }

    public void displaySessions(List<Session> sessionList) {
        sessionsRecyclerView = findViewById(R.id.sessions_recycler_view);
        sessionsLayoutManager = new LinearLayoutManager(this);
        sessionsRecyclerView.setLayoutManager(sessionsLayoutManager);
        sessionsViewAdapter = new SessionsViewAdapter(sessionList);
        sessionsRecyclerView.setAdapter(sessionsViewAdapter);
    }
}