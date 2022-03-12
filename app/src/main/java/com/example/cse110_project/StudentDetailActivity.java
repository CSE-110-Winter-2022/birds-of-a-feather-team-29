package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import com.example.cse110_project.adapters.BoFCourseViewAdapter;
import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.utilities.Constants;
import com.squareup.picasso.Picasso;

public class StudentDetailActivity extends AppCompatActivity {
    private AppDatabase db;
    private BoFStudent student;
    private RecyclerView coursesRecyclerView;
    private RecyclerView.LayoutManager coursesLayoutManager;
    private BoFCourseViewAdapter coursesViewAdapter;
    public boolean amIWaving;
    public static final String HOLLOW_PIC = "https://lh3.googleusercontent.com/pw/AM-JKLWhrG5_J2DgZIYaBuFCWWIq1hqzqY2MjyJy86MfGLiodrTs88PND5cfBDpU4-G3D60uhaOE7AYDWq_8aBg6tgPxQ-og88xmx6YdQNUczvnS86iDvTWt7F_y2D06gCXJVEt05DsKjRhGSyohFgja6Iad=s612-no";
    public static final String FILLED_PIC = "https://lh3.googleusercontent.com/pw/AM-JKLVaTugZo4OA4sv4F92MqcL89zjEfHq9f1py7dCP0dM4dlEUrMMdudsXOpTHwngD_tu-8I14jR57urJnwXxM2qpimDO-lYTeICxowK11AJO9cqZDAbf-TYntp_T49MUda-AGn2yAjk0UFz0cZzVOWBHj=s612-no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("StudentDetailActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        displaySharedCourses(0);
        displayWave();
        displayImage();
    }

    private void displayImage() {
        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);

        student = db.BoFStudentDao().get(studentId);
        ImageView headShotView = findViewById(R.id.head_shot_image_view);
        try {
            Picasso.get().load(student.getUrl()).resize(150,150).into(headShotView);
        } catch (IllegalStateException ignored) {}

    }

    public void onGoBackClicked(View view) {
        Log.d("StudentDetailActivity::onGoBackClicked()", "Non-testable method");

        finish();
    }

    private void displayWave() {
        Log.d("StudentDetailActivity::displayWave()", "Non-testable method");

        try {
            Intent intent = getIntent();
            int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);
            student = db.BoFStudentDao().get(studentId);
        } catch (IllegalStateException ignored) {}

        if (student == null) { return; }

        amIWaving = student.getAmIWaving();

        ImageView imageView = findViewById(R.id.hand_wave);
        if(amIWaving) {
            Picasso.get().load(FILLED_PIC).into(imageView);
        }
        else {
            Picasso.get().load(HOLLOW_PIC).into(imageView);
        }
    }

    public void displaySharedCourses(int testInt) {
        db = AppDatabase.singleton(this);

        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);

        if ((studentId == -1) && (testInt == 0)) { return; }
        else if ((studentId == -1) && (testInt == 1)) { studentId = 1; }

        student = db.BoFStudentDao().get(studentId);

        List<BoFCourse> courses = db.BoFCourseDao().getForStudent(studentId);

        if(student != null){setTitle(student.getName());}

        coursesRecyclerView = findViewById(R.id.courses_view);
        coursesLayoutManager = new LinearLayoutManager(this);
        coursesRecyclerView.setLayoutManager(coursesLayoutManager);
        coursesViewAdapter = new BoFCourseViewAdapter(courses);
        coursesRecyclerView.setAdapter(coursesViewAdapter);
    }

    /**
     * Returns the number of courses shared with the User for testing purposes
     * */
    public int getNumOfCoursesDisplayed() {
        Log.d("StudentDetailActivity::getNumOfCoursesDisplayed()", "Non-testable method");

        return coursesViewAdapter.getItemCount();
    }

    public void onClickWave(View view) {
        Log.d("StudentDetailActivity::onClickWave()", "Non-testable method");

        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);
        student = db.BoFStudentDao().get(studentId);

        amIWaving = student.getAmIWaving();
        if(amIWaving) {
            Toast.makeText(view.getContext(), "Wave already sent.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(view.getContext(), "Wave sent!", Toast.LENGTH_SHORT).show();
        }

        db.BoFStudentDao().updateAmIWaving(true, studentId);
        ImageView imageView = findViewById(R.id.hand_wave);
        Picasso.get().load(FILLED_PIC).into(imageView);
    }
}