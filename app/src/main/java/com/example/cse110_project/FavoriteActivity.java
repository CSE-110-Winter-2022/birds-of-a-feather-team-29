package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.favorite.Favorite;
import com.example.cse110_project.utilities.Constants;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView studentsRecyclerView;
    protected RecyclerView.LayoutManager studentsLayoutManager;
    private FavStudentAdapter favStudentViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        setTitle(Constants.APP_VERSION);

        AppDatabase db = AppDatabase.singleton(getApplicationContext());
        List<Favorite> students = db.FavoriteDao().getAll();

        studentsRecyclerView = findViewById(R.id.fav_view);
        studentsLayoutManager = new LinearLayoutManager(this);
        studentsRecyclerView.setLayoutManager(studentsLayoutManager);
        favStudentViewAdapter = new FavStudentAdapter(students);
        studentsRecyclerView.setAdapter(favStudentViewAdapter);
    }

    public void onGoBackClicked(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }
}