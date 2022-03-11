package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

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
    public static final String HOLLOW_PIC = "https://lh3.googleusercontent.com/pw/AM-JKLXUYrn4mdwGuRuDLMy6S1qtMyy6XiabGLx-plTvrfDjoOLOfbXmNPx8oZjMQlyY8s0_048hCtYCVY5z5ICKsog5S5ibPJSMOyWXva2uoC5RnkHBjC6heUadX41jCgEMiT2Q5tCOBbUiI0fbJDFgAGnJ=s612-no";
    public static final String FILLED_PIC = "https://lh3.googleusercontent.com/pw/AM-JKLVyLTLDFzVi7lBCkiepxiR_kCExkWSchq6uk3RLQo3JuIcld3XWjmNCEDGpvBfUsmykVmFEN9s3VlOmJQO6yYJp7HwF8Jp8fqOp42upEVXcOdxsaifIky3WSZ-tIqMesaHyEnZpLvWQCtxcUGojUh-y=s612-no";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        displaySharedCourses(0);
        displayWave();
    }

    private void displayWave() {
        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);

        student = db.BoFStudentDao().get(studentId);

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

    public void onGoBackClicked(View view) {
        finish();
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
        return coursesViewAdapter.getItemCount();
    }

    public void onClickWave(View view) {
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