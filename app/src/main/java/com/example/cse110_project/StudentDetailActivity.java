package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.utilities.Constants;

public class StudentDetailActivity extends AppCompatActivity {
    private AppDatabase db;
    private BoFStudent student;
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private BoFCourseViewAdapter coursesViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        displaySharedCourses(0);
    }

    public void onGoBackClicked(View view) {
        finish();
    }

    public void displaySharedCourses(int testInt) {
        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);

        if ((studentId == -1) && (testInt == 0)) { return; }
        else if ((studentId == -1) && (testInt == 1)) { studentId = 1; }

        db = AppDatabase.singleton(this);

        student = db.BoFStudentDao().get(studentId);

        List<BoFCourse> courses = db.BoFCourseDao().getForStudent(studentId);

        setTitle(student.getName());

        coursesRecyclerView = findViewById(R.id.courses_view);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new BoFCourseViewAdapter(courses);
        coursesRecyclerView.setAdapter(coursesViewAdapter);
    }

    public int getNumOfCoursesDisplayed() {
        return coursesViewAdapter.getItemCount();
    }
}