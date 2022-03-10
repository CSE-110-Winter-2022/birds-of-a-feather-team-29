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
    public static final String HOLLOW_PIC = "https://lh3.googleusercontent.com/CxixNkLWPjhDDRxw91lN86yAEi5uKKupY4Q_bz1Gk8oZ8rybYqmfmApuH_5ths3VAzWGtvTC7uX8_2Mcz1TRElOTIuIAe_nKlQapg5qsFto80MDxa3tsavjYCKGARjbP-MjWVrjhPJnHIZ7_-gdvaL1tlrCyanNC8z49VCiXiTAnUBH-uKcXTNTK33PA1VsGZBsUF81murGg-781ZQYU1QXLTjD7iPB-03Mnaq-h5P_lDqOdGagv20E2f571_j3ctdvT2NdHHTEBOLNkp7MJq_BDiK5U8b-OlNniIfyxwms9-NIy2AfJih5QWRrMNkghWV5qGRUzZgVi2pWxNdsO6u-FSbw72CywBMrJ8UK5jQv4rR7JMDROeRowLaIYlBIdLkxnPyT7bRo2B02kbk4lhV1x2lYPJfRFXYpbSzDegXumsSuyDDZ3vv9LYHrcfgAvgfbkLu8jsoppKy427Eia4tJjC2QMCOvf9OWAq30Pe_xVdFiKNZnV4OjqJnX8SJOPa_6Y3TwJeWj8jBvNFZjqVxzbQkMym8ZeDp28h5zfWYgeQB07dNNDUa1UaQG7e7HBUNQNf37KNHn1IDrXkbVe_eHkd9jbnF9d1SCW2Zjm9PgnE8-pkolAdhq7AsR46VtSsIr7Et4TPBl1KP8p0JsBKcD9XSHn9_jdsJgAN4Vv0phoAJCBimhf8JV7ghDlWYMvGaN9qh2i9EsoqrdChLRt6cqq=s612-no?authuser=0";
    public static final String FILLED_PIC = "https://lh3.googleusercontent.com/avjhjBdq7mI4T85cJkYRZDv9lS_zoUe7yjhzFWpG9seILBAaGNtPTESRdn8UokS7xwwTMogQZAmGHBmg9MyDamnBNd-Uhi4BXIyYtfKrI0myYqsuiVA7ixo8dIPsoeekhkB35fncItroxfT4nYZx8V3uBk0mL3FN1uQZgVN2QXMWbaf0pRSHvEZEIhrsuojtQesCydsy1uYWwN_THVGPEyinOpikUVg556Ab9dRuAugD5FZisfvNsa_g9wTLvqphC_9bDJPQaTWO6KvV_zfeVQAjcTLmc_g9DJy-MN3J3dLUzfoREarnOjTFLDQwCtMQcL3hF0JLnMJ0Lz32Nywpf0mhT9ZAduCEx8Ci_WKulOvBzWNJYa4NXoOBgibc1Ri0iEl5K0O0aXxcya_E6z7-lYIWbatsTzk2-MSUARp3nid-fYjV70mpU0MAsgVrWhdTAFuSq9ZqVpZRE7gLuxA4wcPOlFnvJEV6hcysh2vU2m6t_nr0_YvaJ4soNR0YVBdbB9ZAcu4NhoWSyJV3susXcTh-Xoe2YskxAHTf9qGC2or1LH1C12EjL3cCrPq7X6x6qdpqhyiASo1nvjoHhH-3-TOGzFI2-DssDg43uFavwDw9rRrML5aDuIgT3wZ9FmsvXeeWZ7FZ3mvTwgsUlReAEzdBuj93IJAQVgiDq8w4oMJgX6VeCJJgY-orMwkdMJDYtfawLLO_Uw_2j82GYW3EZosi=s612-no?authuser=0";
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
        Intent intent = getIntent();
        int studentId = intent.getIntExtra(Constants.BOF_STUDENT_ID, -1);

        if ((studentId == -1) && (testInt == 0)) { return; }
        else if ((studentId == -1) && (testInt == 1)) { studentId = 1; }

        db = AppDatabase.singleton(this);

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