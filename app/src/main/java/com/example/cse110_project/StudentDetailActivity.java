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
    public static final String HOLLOW_PIC = "https://lh3.googleusercontent.com/9ZX_bFnxCtJpYCS8sVH1SwmEs_lv6O3iTaIIXklUtswWHMAV_Onyh7MWpK090ddLQosEimbvEA9qxxj-EcBob_0k-nxLpjxO58r1mRPPdGND1jH3a_k6d8039_MmwDrK1kqW2F0KaFmANDsv_6wUKBnzhf8CEqlTKZXhoLatiuVuu2RKDjX05NcsKiKRUYP4xjP1VNDI3FHXbuv2TczPMXyXxK3pphNpvc5Dlx34W-mUbNutN_6V2N4TwkNB9kVuPyGFd4-EsetZHq7PKJSh6HBxw3wmUvSj-ccAJ4xtaS4lXFh2WTgAEFI6VK7h1OEVjxZ4Srl5dZmlzyasS8ZiAHjHb8NXuLrtB91PUp8PkULIafA3pNyPc3yNMs4OfptUIB9btMkpumyEkvnId4hqzJh8X74f2bMa1L8wM_5DM_L37dw2y_2-uSc_GYpxM2CE1qUVxMomkQ19jsLrtM5SkcjgoGIU06fu5ry8xH259dXNuhsoa6qZJK_vNsTTaKUUF-oOcJ2z-61-Q0r6xiHVHmG-yiAk_msptN7PIaBuoNgpKQvrf9JE4-o5u1qzn2ePj-e4KDk1QGZIeHqWXsd9EpatCUcOjtKIka25-YgSccvpsFgYBJ64PxovhzCOzZTho02-0NBmCirpjjg7U4eGyYGvHh3EH42s97pIVCEhD_0WM24wnyEWS1wm77wB7KvXe4iY86d6ka6zlA7GyOYP4fTU=s612-no?authuser=0";
    public static final String FILLED_PIC = "https://lh3.googleusercontent.com/iLtIbUUYmlJPCWLZoTmNGhlZ-zEAqyBz6iPPc6x1HUP4tiab706E3W1xsbHpLWqY0SLqgQrL3Xhn10ZANxMl2d5B1PnMxv6zXBW-MrOmkx0yl7ZwM-KJ_jwv0oZyucpcpyJfR-1a9YK2HeElVTBeT28o2OjN_EgyKm4KQY4MQ8UQtl8p5uXNDd13jo8OdTk1qPAqBpfmk0fdjARq6iFs4cBzmQAF4_UTJlZvYonyN5oxvwYvob3kkjKJ2GQ7o4rlAycd-FAeJAx6gDbpyur2QofneRhSt4Ol2qvF4GIC-nD4PLksAmAGA3Rcx89-hE3fSboqO0Z3CHxIfat8L4jeyJr4swUXBV2Isg6Ul4sFWI_Wj1RnXCu55MNJNVvxluGjNF8vSHlMobBayqDNNSRskEKR89SqgTqRsPs-8egkU9Xs2vjHFXESmH31W07tHF_k6SRoW0A04PbPknXferrr81ywfqyBt1QwlcEWwgkCJc3Ca1mfn2HDlz-sSkxyuIvDIDGWyTIBZ1xI8vysBTxEIgpx4MuGM2RJbUsZvuKqbpqjlDsqdJaKuJKt_vf2GhVmX482iDfp_hwzE29n-ogN9nChntSB4Xuriyzl6EHtJ3Wc05JjOSvttDoFOmbGyF6lRea4vKxNNascTEB-P2frpPxLSMCfVCb06ovygdd4FGCfVgeTlPw0GDuSu18OuInY8T-hyvL-lNvpc2imLFl8E_u4=s612-no?authuser=0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_detail);

        displaySharedCourses(0);
        displayWave();
    }

    private void displayWave() {
        ImageView imageView = findViewById(R.id.hand_wave);
        Picasso.get().load(HOLLOW_PIC).into(imageView);
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
        Toast.makeText(view.getContext(), "Wave sent!", Toast.LENGTH_SHORT).show();
        ImageView imageView = findViewById(R.id.hand_wave);
        Picasso.get().load(FILLED_PIC).into(imageView);
    }
}