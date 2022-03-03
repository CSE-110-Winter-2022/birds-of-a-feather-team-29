package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.databases.user.UserDao;
import com.example.cse110_project.utilities.Constants;

public class EnterNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        setTitle(Constants.APP_VERSION);
    }

    public void onConfirmButtonClicked(View view) {
        TextView nameTextView = findViewById(R.id.enter_name);
        String name = nameTextView.getText().toString();

        if (!isValidName(name)) {
            runOnUiThread(() -> {
                Utilities.showAlert(this, Constants.ALERT, Constants.INVALID_NAME);
            });
        }
        else {
            Intent intent = new Intent(this, AddLinkActivity.class);
            SharedPreferences pref = getSharedPreferences(Constants.USER_INFO, MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();

            AppDatabase db = AppDatabase.singleton(getApplicationContext());
            db.UserDao().updateFirstName(name, db.UserDao().getAll().get(0).getUserId());

            editor.putString(Constants.USER_FIRST_NAME, name);

            startActivity(intent);
        }
    }

    public static boolean isValidName(String name){
        if (name.isEmpty()) { return false; }

        for (int i = 0; i < name.length(); i++) {
            char curr = name.charAt(i);
            if (curr < 32) { return false; }
            else if (curr > 32 && curr < 65) { return false; }
            else if (curr > 90 && curr < 97) { return false; }
            else if(curr > 122){ return false; }
        }

        return true;
    }
}