package com.example.cse110_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultStudent;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.FakedMessageListener;
import com.google.android.gms.nearby.Nearby;
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.Arrays;
import java.util.List;

public class MockNearbyMessagesActivity extends AppCompatActivity {
    private static final String TAG = "CSE110-Project";
    private MessageListener messageListener;

    private String myUuid = "4b295157-ba31-4f9f-8401-5d85d9cf659a";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_nearby_messages);
        setTitle(Constants.APP_VERSION);
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    /**
     * Entries:
     *
     * If waving:
     * [0]SUUID,[1]Name,[2]Link,[3]Year,[4]Quarter,[5]Course,[6]CourseNumber,[7]CourseNumber,[8]OUUID,[9]Wave
     *
     * Not waving:
     * [0]SUUID,[1]Name,[2]Link,[3]Year,[4]Quarter,[5]ClassSize,[6]Course,[7]CourseNumber
     * */
    public void onEnterClicked(View view) {
        TextView mockInformation = findViewById(R.id.mock_text_textview);

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                String[] mockArr = new String(message.getContent()).split(Constants.COMMA);

                AppDatabase db = AppDatabase.getSingletonInstance();

                List<DefaultStudent> studentList = db.DefaultStudentDao().getAll();

                for (int i = 0; i< studentList.size(); i++){
                    if (studentList.get(i).getName().equals(mockArr[1])){
                        return;
                    }
//                    if (studentList.get(i).getName().equals(mockArr[0])){
//                        return;
//                    }
                }

                db.DefaultStudentDao().insert(new DefaultStudent(mockArr[1]));

                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();

                int endIndex;
                int mockArrLen = mockArr.length;
                int studentId = defStudentsList.get(defStudentsList.size()-1).getStudentId();

                // Case: student is waving
                if(mockArr[mockArrLen - 1].equals("wave")){
                    endIndex = mockArrLen - 2;
                    Log.v("mocking activity", "mockArr[mockArrLen - 2] is " + mockArr[mockArrLen - 2]);

                    if(mockArr[mockArrLen - 2].compareTo(myUuid) == 0){

                        // TODO: Alternative find by name
                        DefaultStudent ds = defStudentsList.get(defStudentsList.size()-1);
                        db.DefaultStudentDao().updateIsWaving(true, ds.getStudentId());

                        //defStudentsList.get(defStudentsList.size()-1).setIsWave(true);
                        Log.v("mocking activity", "isWave is true");
                    }
                    Log.v("mocking activity", "isWave is true but wrong id");
                }
                else{
                    endIndex = mockArrLen;
                    Log.v("mocking activity", "isWave is false");
                }

                // SUUID,Name,Link,[3+5n]Year,[4+5n]Quarter,[5+5n]Course,[6+5n]CourseNumber,[7+5n]ClassSize
                //
                // public DefaultCourse(int studentId, String year, String quarter, String course, String courseNum,
                //                         String classSize, boolean courseAdded) {
                //        this.studentId = studentId;
                //        this.year = year;
                //        this.quarter = quarter;
                //        this.classSize = classSize;
                //        this.course = course;
                //        this.courseNum = courseNum;
                //        this.courseAdded = courseAdded;
                //

                for (int i = 3; i < endIndex; i=i+5) {
                    db.DefaultCourseDao().insert(new DefaultCourse(studentId,
                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
                }
                Arrays.fill(mockArr, null);


//                for (int i = 2; i < mockArr.length; i=i+5) {
//                    db.DefaultCourseDao().insert(new DefaultCourse(defStudentsList.get(defStudentsList.size()-1).getStudentId(),
//                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
//                }

            }

//            @Override
//            public void onLost(@NonNull Message message) {
//                //Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
//            }
        };

        this.messageListener = new FakedMessageListener(realListener, 3,
                mockInformation.getText().toString());

        mockInformation.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(messageListener!=null){
            Nearby.getMessagesClient(this).subscribe(messageListener);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(messageListener!=null){
            Nearby.getMessagesClient(this).unsubscribe(messageListener);
        }
    }
}


//package com.example.cse110_project;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.TextView;
//
//import com.example.cse110_project.databases.AppDatabase;
//import com.example.cse110_project.databases.def.DefaultCourse;
//import com.example.cse110_project.databases.def.DefaultStudent;
//import com.example.cse110_project.utilities.Constants;
//import com.example.cse110_project.utilities.FakedMessageListener;
//import com.google.android.gms.nearby.Nearby;
//import com.google.android.gms.nearby.messages.Message;
//import com.google.android.gms.nearby.messages.MessageListener;
//
//import java.util.Arrays;
//import java.util.List;
//
//public class MockNearbyMessagesActivity extends AppCompatActivity {
//    private static final String TAG = "CSE110-Project";
//    private MessageListener messageListener;
//    private boolean Start;
//
//    private String myUuid = "4b295157-ba31-4f9f-8401-5d85d9cf659a";
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_mock_nearby_messages);
//        setTitle(Constants.APP_VERSION);
//    }
//
//    public void onBackClicked(View view) {
//        Intent intent = new Intent(this, HomePageActivity.class);
//        startActivity(intent);
//    }
//
//    /**
//     * Entries:
//     *
//     * If waving:
//     * [0]SUUID,[1]Name,[2]Link,[3]Year,[4]Quarter,[5]Course,[6]CourseNumber,[7]ClassSize,[8]OUUID,[10]Wave
//     *
//     * Not waving:
//     * [0]SUUID,[1]Name,[2]Link,[3]Year,[4]Quarter,[5]Course,[6]CourseNumber,[7]ClassSize
//     * */
//    public void onEnterClicked(View view) {
//        TextView mockInformation = findViewById(R.id.mock_text_textview);
//
//        MessageListener realListener = new MessageListener() {
//            @Override
//            public void onFound(@NonNull Message message) {
//                String[] mockArr = new String(message.getContent()).split(Constants.COMMA);
//
//                AppDatabase db = AppDatabase.getSingletonInstance();
//
//                List<DefaultStudent> studentList = db.DefaultStudentDao().getAll();
//
//                for (int i = 0; i< studentList.size(); i++){
//                    if (studentList.get(i).getName().equals(mockArr[1])){
//                        return;
//                    }
////                    if (studentList.get(i).getName().equals(mockArr[0])){
////                        return;
////                    }
//                }
//
//                db.DefaultStudentDao().insert(new DefaultStudent(mockArr[1]));
//
//                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();
//
//                int endIndex;
//                int mockArrLen = mockArr.length;
//                int studentId = defStudentsList.get(defStudentsList.size()-1).getStudentId();
//
//                // Case: student is waving
//                if(mockArr[mockArrLen - 1].equals("wave")){
//                    endIndex = mockArrLen - 2;
//                    Log.v("mocking activity", "mockArr[mockArrLen - 2] is " + mockArr[mockArrLen - 2]);
//
//                    if(mockArr[mockArrLen - 2].compareTo(myUuid) == 0){
//
//                        // TODO: Alternative find by name
//                        DefaultStudent ds = defStudentsList.get(defStudentsList.size()-1);
//                        db.DefaultStudentDao().updateIsWaving(true, ds.getStudentId());
//
//                        //defStudentsList.get(defStudentsList.size()-1).setIsWave(true);
//                        Log.v("mocking activity", "isWave is true");
//                    }
//                    Log.v("mocking activity", "isWave is true but wrong id");
//                }
//                else{
//                    endIndex = mockArrLen;
//                    Log.v("mocking activity", "isWave is false");
//                }
//
//                // SUUID,Name,Link,[3+5n]Year,[4+5n]Quarter,[5+5n]Course,[6+5n]CourseNumber,[7+5n]ClassSize
//                //
//                // public DefaultCourse(int studentId, String year, String quarter, String course, String courseNum,
//                //                         String classSize, boolean courseAdded) {
//                //        this.studentId = studentId;
//                //        this.year = year;
//                //        this.quarter = quarter;
//                //        this.classSize = classSize;
//                //        this.course = course;
//                //        this.courseNum = courseNum;
//                //        this.courseAdded = courseAdded;
//                //
//
//                for (int i = 3; i < endIndex; i=i+5) {
//                    db.DefaultCourseDao().insert(new DefaultCourse(studentId,
//                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
//                }
//                Arrays.fill(mockArr, null);
//
//
////                for (int i = 2; i < mockArr.length; i=i+5) {
////                    db.DefaultCourseDao().insert(new DefaultCourse(defStudentsList.get(defStudentsList.size()-1).getStudentId(),
////                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
////                }
//
//            }
//
////            @Override
////            public void onLost(@NonNull Message message) {
////                //Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
////            }
//        };
//
//        this.messageListener = new FakedMessageListener(realListener, 3,
//                mockInformation.getText().toString());
//
//        mockInformation.setText("");
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(messageListener!=null){
//            Nearby.getMessagesClient(this).subscribe(messageListener);
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if(messageListener!=null){
//            Nearby.getMessagesClient(this).unsubscribe(messageListener);
//        }
//    }
//}