package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class EnterNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        System.out.println("TESTING");
    }


    public void confirm(View view) {
        TextView nameTextView = findViewById(R.id.enter_name);
        String name = nameTextView.getText().toString();
        if(!isValidName(name)){
            System.out.println("FALSE");
            runOnUiThread(() -> {
                Utilities.showAlert(this, "Alert!", "Invalid name! Valid characters: A-Z, a-z, space character");
            });
        }
        else {
            SharedPreferences preferences = getPreferences(MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("name", name);

        }

    }

    public static boolean isValidName(String name){
        if(name.isEmpty()){
            return false;
        }
        for(int i = 0; i < name.length(); i++){
            char curr = name.charAt(i);
            if(curr < 32){
                return false;
            }
            else if(curr > 32 && curr < 65){
                return false;
            }
            else if(curr > 90 && curr <= 97){
                return false;
            }
            else if(curr > 122){
                return false;
            }
        }
        return true;
    }
}