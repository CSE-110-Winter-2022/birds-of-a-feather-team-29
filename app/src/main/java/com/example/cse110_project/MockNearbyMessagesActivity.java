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
    private static final String myUUID = "abc";
    private MessageListener messageListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("MockNearbyMessagesActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_nearby_messages);
        setTitle(Constants.APP_VERSION);
    }

    public void onBackClicked(View view) {
        Log.d("MockNearbyMessagesActivity::onBackClicked()", "Non-testable method");

        Intent intent = new Intent(this, HomePageActivity.class);
        startActivity(intent);
    }

    /**
     * Format of entries:
     * If mocked student is waving to User:
     * [0]SUUID,[1]Name,[2]Link,[3]Year,[4]Quarter,[5]Course,[6]CourseNumber,[7]ClassSize,
     * [8]MYUUID,[9]Wave
     *
     * If mocked student is not waving to User:
     * [0]SUUID,[1]Name,[2]Link,[3]Year,[4]Quarter,[5]Course,[6]CourseNumber,[7]ClassSize
     * */
    public void onEnterClicked(View view) {
        Log.d("MockNearbyMessagesActivity::onEnterClicked()", "Non-testable method");

        TextView mockInformation = findViewById(R.id.mock_text_textview);

        MessageListener realListener = new MessageListener() {

            @Override
            public void onFound(@NonNull Message message) {
                String[] mockArr = new String(message.getContent()).split(Constants.COMMA);
                AppDatabase db = AppDatabase.getSingletonInstance();
                List<DefaultStudent> studentList = db.DefaultStudentDao().getAll();

                // Search through list to see if student is already in the database
                // Note: Current duplicate check is same name
                for (int i = 0; i < studentList.size(); i++){
                    if (studentList.get(i).getName().equals(mockArr[1])) { return; }
                }

                db.DefaultStudentDao().insert(new DefaultStudent(mockArr[1], mockArr[2]));

                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();
                int endIndex;
                int mockArrLen = mockArr.length;
                int studentId = defStudentsList.get(defStudentsList.size()-1).getStudentId();

                if (mockArr[mockArrLen - 1].equals("wave")) {
                    endIndex = mockArrLen - 2;
                    Log.v("mocking activity", "mockArr[mockArrLen - 2] is " + mockArr[mockArrLen - 2]);

                    if (mockArr[mockArrLen - 2].compareTo(myUUID) == 0) {

                        DefaultStudent ds = defStudentsList.get(defStudentsList.size() - 1);
                        db.DefaultStudentDao().updateIsWaving(true, ds.getStudentId());

                        Log.v("mocking activity", "isWave is true");
                    }
                    Log.v("mocking activity", "isWave is true but wrong id");
                }
                else{
                    endIndex = mockArrLen;
                    Log.v("mocking activity", "isWave is false");
                }

                for (int i = 3; i < endIndex; i=i+5) {
                    db.DefaultCourseDao().insert(new DefaultCourse(studentId,
                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
                }
                Arrays.fill(mockArr, null);
            }

            @Override
            public void onLost(@NonNull Message message) {}

        };

        this.messageListener = new FakedMessageListener(realListener, 3,
                mockInformation.getText().toString());

        mockInformation.setText("");
    }

    @Override
    protected void onStart() {
        Log.d("MockNearbyMessagesActivity::onStart()", "Non-testable method");

        super.onStart();

        if (messageListener != null) {
            Nearby.getMessagesClient(this).subscribe(messageListener);
        }
    }

    @Override
    protected void onStop() {
        Log.d("MockNearbyMessagesActivity::onStop()", "Non-testable method");

        super.onStop();

        if (messageListener != null) {
            Nearby.getMessagesClient(this).unsubscribe(messageListener);
        }
    }
}