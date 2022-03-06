package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.Constants;

import java.util.ArrayList;

public class CoursesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses);
        setTitle(Constants.APP_VERSION);
        displayCourse();
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, MainPageActivity.class);
        startActivity(intent);
    }

    public void onEnterClicked(View view) {
        Intent intent = new Intent(this, AddCoursesMainActivity.class);
        startActivity(intent);
    }

    public void displayCourse() {
        AppDatabase db = AppDatabase.getSingletonInstance();

        int index = 0;

        ArrayList<String> courseList = new ArrayList<>();
//        while(db.UserCourseDao().getAll().get(index).getYear() != null) {
//            String year = db.UserCourseDao().getAll().get(index).getYear();
//            String quarter = db.UserCourseDao().getAll().get(index).getQuarter();
//            String subject = db.UserCourseDao().getAll().get(index).getCourse();
//            String number = db.UserCourseDao().getAll().get(index).getCourseNum();
//            String size = db.UserCourseDao().getAll().get(index).getClassSize();
//            String fullCourseName = year + " " + quarter + " " + subject + " " + number + " " + size;
//            courseList.add(fullCourseName);
//            index++;
//        }

        for(int i = 0; i < db.UserCourseDao().getAll().size(); i++){
            String year = db.UserCourseDao().getAll().get(i).getYear();
            String quarter = db.UserCourseDao().getAll().get(i).getQuarter();
            String subject = db.UserCourseDao().getAll().get(i).getCourse();
            String number = db.UserCourseDao().getAll().get(i).getCourseNum();
            String size = db.UserCourseDao().getAll().get(i).getClassSize();
            String fullCourseName = year + " " + quarter + " " + subject + " " + number + " " + size;
            courseList.add(fullCourseName);
        }

        TextView[] courseLayouts = {findViewById(R.id.prev_course_one_textview),
                findViewById(R.id.prev_course_two_textview),
                findViewById(R.id.prev_course_three_textview),
                findViewById(R.id.prev_course_four_textview),
                findViewById(R.id.prev_course_five_textview),
                findViewById(R.id.prev_course_six_textview),
                findViewById(R.id.prev_course_seven_textview),
                findViewById(R.id.prev_course_eight_textview),
                findViewById(R.id.prev_course_nine_textview),
                findViewById(R.id.prev_course_ten_textview)};

        for(int i = 0; i < courseList.size() && i < 10; i++) {
            courseLayouts[i].setText(courseList.get(i));
        }

        //        String year = db.UserCourseDao().getAll().get(0).getYear();
//        String quarter = db.UserCourseDao().getAll().get(0).getQuarter();
//        String subject = db.UserCourseDao().getAll().get(0).getCourse();
//        String number = db.UserCourseDao().getAll().get(0).getCourseNum();
//        String size = db.UserCourseDao().getAll().get(0).getClassSize();
//        TextView textView = (TextView) findViewById(R.id.prev_course_one_textview);
//        textView.setText(year + " " + quarter + " " + subject + " " + number + " " + size);

//        TextView[] courseLayouts = {findViewById(R.id.prev_course_one_textview),
//                findViewById(R.id.prev_course_two_textview),
//                findViewById(R.id.prev_course_three_textview),
//                findViewById(R.id.prev_course_four_textview),
//                findViewById(R.id.prev_course_five_textview),
//                findViewById(R.id.prev_course_six_textview)};


        //        while(db.UserCourseDao().getAll().get(i).getYear() != null) {
//            String year = db.UserCourseDao().getAll().get(i).getYear();
//            String quarter = db.UserCourseDao().getAll().get(i).getQuarter();
//            String subject = db.UserCourseDao().getAll().get(i).getCourse();
//            String number = db.UserCourseDao().getAll().get(i).getCourseNum();
//            String size = db.UserCourseDao().getAll().get(i).getClassSize();
//
//            String fullCourseName = year + " " + quarter + " " + subject + " " + number + " " + size;
//
//            courseLayouts[i] = new TextView();
//            i++;
//        }

//        LinearLayout list = (LinearLayout) findViewById(R.id.course_list);
//        for(int i = 0; i < courseList.size(); i++){
//            TextView courseView = new TextView(this);
//            courseView.setText(courseList.get(i));
//            list.addView(courseView);
//        }


//        TextView[] tv=(TextView)findViewById(R.id.course_list);
//
//        for(int i = 0; i < 6; i++) { // iterate over all array items and assign them text.
//            TextView txtCnt = new TextView(this);
//            txtCnt.setText(courseList.get(i));
//            tv[i] =txtCnt ;
//        }
//        int[] courseLayouts = {
//                R.id.prev_course_one_textview,
//                R.id.prev_course_two_textview,
//                R.id.prev_course_three_textview,
//                R.id.prev_course_four_textview,
//                R.id.prev_course_five_textview,
//                R.id.prev_course_six_textview};


    }


}
