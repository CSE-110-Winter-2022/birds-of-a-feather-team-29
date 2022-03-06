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

import java.util.List;

public class MockNearbyMessagesActivity extends AppCompatActivity {
    private static final String TAG = "CSE110-Project";
    private MessageListener messageListener;
    private boolean Start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_nearby_messages);
        setTitle(Constants.APP_VERSION);

        Bundle extras = getIntent().getExtras();
        Start = extras.getBoolean("start");
    }

    public void onBackClicked(View view) {
        Intent intent = new Intent(this, HomePageActivity.class);
        intent.putExtra("start", Start);
        startActivity(intent);
    }

    public void onEnterClicked(View view) {
        TextView mockInformation = findViewById(R.id.mock_text_textview);

        MessageListener realListener = new MessageListener() {
            @Override
            public void onFound(@NonNull Message message) {
                //Log.d(TAG, "Found message: " + new String(message.getContent()));
                String[] mockArr = new String(message.getContent()).split(Constants.COMMA);

                //AppDatabase db = AppDatabase.singleton(getApplicationContext());
                AppDatabase db = AppDatabase.getSingletonInstance();
                List<DefaultStudent> studentList = db.DefaultStudentDao().getAll();
                for (int i = 0; i< studentList.size(); i++){
                    if (studentList.get(i).getName().equals(mockArr[0])){
                        return;
                    }
                }
                db.DefaultStudentDao().insert(new DefaultStudent(mockArr[0]));

                List<DefaultStudent> defStudentsList = db.DefaultStudentDao().getAll();


                for (int i = 2; i < mockArr.length; i=i+5) {
                    db.DefaultCourseDao().insert(new DefaultCourse(defStudentsList.get(defStudentsList.size()-1).getStudentId(),
                            mockArr[i], mockArr[i+1], mockArr[i+2], mockArr[i+3], mockArr[i+4], false));
                }

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