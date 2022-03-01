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
import com.google.android.gms.nearby.messages.Message;
import com.google.android.gms.nearby.messages.MessageListener;

import java.util.List;

public class MockNearbyMessagesActivity extends AppCompatActivity {
    private static final String TAG = "CSE110-Project";
    private MessageListener messageListener;

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

    public void onEnterClicked(View view) {
        TextView mockInformation = findViewById(R.id.mock_text_textview);

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                //Log.d(TAG, "Found message: " + new String(message.getContent()));
                String[] mockArr = new String(message.getContent()).split(Constants.COMMA);

                AppDatabase db = AppDatabase.singleton(getApplicationContext());
                db.clearAllTables();
                db.DefaultStudentDao().delete();
                db.DefaultCourseDao().delete();
                //1

                DefaultStudent[] defaultStudentList = {
                        new DefaultStudent("Steel"),
                        new DefaultStudent("Sandy"),
                        new DefaultStudent("Aiko"),
                        new DefaultStudent(mockArr[0])
                };

                for (DefaultStudent dS : defaultStudentList) {
                    db.DefaultStudentDao().insert(dS);
                }

                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();

                DefaultCourse[] defaultCourseList = {
                        new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                                "Fall", "CSE 11"),
                        new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                                "Fall","CSE 12"),
                        new DefaultCourse(defStudentsList.get(0).getStudentId(), "2017",
                                "Fall","CSE 21"),
                        new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                                "Spring","CSE 100"),
                        new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                                "Spring","CSE 140"),
                        new DefaultCourse(defStudentsList.get(0).getStudentId(), "2019",
                                "Spring","CSE 105"),

                        new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                                "Winter","CSE 11"),
                        new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                                "Winter","CSE 12"),
                        new DefaultCourse(defStudentsList.get(1).getStudentId(), "2018",
                                "Winter","CSE 21"),
                        new DefaultCourse(defStudentsList.get(1).getStudentId(), "2019",
                                "Fall", "CSE 100"),

                        new DefaultCourse(defStudentsList.get(2).getStudentId(), "2018",
                                "Spring","CSE 15L"),
                        new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                                "Summer Session I","CSE 191"),
                        new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                                "Fall","CSE 142"),
                        new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                                "Fall","CSE 112"),
                        new DefaultCourse(defStudentsList.get(2).getStudentId(), "2020",
                                "Fall","CSE 167"),
                };


                for (DefaultCourse defaultCourse : defaultCourseList) {
                    db.DefaultCourseDao().insert(defaultCourse);
                }

                for (int i = 2; i < mockArr.length; i=i+4) {
                    Log.d(TAG, mockArr[i] + " " + mockArr[i+1] + " " + mockArr[i+2] + mockArr[i+3]);
                    db.DefaultCourseDao().insert(new DefaultCourse(defStudentsList.get(3).getStudentId(),
                            mockArr[i], mockArr[i+1], mockArr[i+2] + " " + mockArr[i+3]));
                }

//                AppDatabase db = AppDatabase.singleton(getApplicationContext());
//                DefaultStudentDao sd = db.DefaultStudentDao();
//                DefaultCourseDao cd = db.DefaultCourseDao();
//
//                sd.insert(new DefaultStudent(mockArr[0]));
//
//                // index 8 -->
//
//                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();
//
            }

            @Override
            public void onLost(@NonNull Message message) {
                //Log.d(TAG, "Lost sight of message: " + new String(message.getContent()));
            }
        };

        this.messageListener = new FakedMessageListener(realListener, 3,
                mockInformation.getText().toString());

        mockInformation.setText("");
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Nearby.getMessagesClient(this).subscribe(messageListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        //Nearby.getMessagesClient(this).unsubscribe(messageListener);
    }
}