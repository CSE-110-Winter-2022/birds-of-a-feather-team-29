package com.example.cse110_project;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.utilities.Constants;
import com.example.cse110_project.utilities.Utilities;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class EnterNameActivity extends AppCompatActivity implements View.OnClickListener {
    private GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("EnterNameActivity::onCreate()", "Non-testable method");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_name);
        setTitle(Constants.APP_VERSION);

        findViewById(R.id.sign_in_button).setOnClickListener(this);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
    }

    private void updateUI(GoogleSignInAccount account) {
        Log.d("EnterNameActivity::updateUI()", "Non-testable method");

        if(account != null){
            findViewById(R.id.sign_in_button).setVisibility(View.INVISIBLE);
            String personGivenName = account.getGivenName();
            TextView textView = findViewById(R.id.enter_name);
            textView.setText(personGivenName);
        }
        else{
            findViewById(R.id.sign_in_button).setVisibility(View.VISIBLE);
        }
    }

    public void onConfirmButtonClicked(View view) {
        Log.d("EnterNameActivity::onConfirmButtonClicked()", "Non-testable method");

        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(account);
        TextView nameTextView = findViewById(R.id.enter_name);
        String name = nameTextView.getText().toString();

        if (!isValidName(name)) {
            runOnUiThread(() -> {
                Utilities.showAlert(this, Constants.ALERT, Constants.INVALID_NAME);
            });
        }
        else {
            Intent intent = new Intent(this, EnterHeadshotURLActivity.class);
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

    @Override
    public void onClick(View view) {
        Log.d("EnterNameActivity::onClick()", "Non-testable method");

        switch (view.getId()) {
            case R.id.sign_in_button:
                OpenSignIn();
                break;
        }
    }

    ActivityResultLauncher<Intent> SignInActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                        handleSignInResult(task);
                    }
                }
            });

    public void OpenSignIn() {
        Log.d("EnterNameActivity::OpenSignIn()", "Non-testable method");

        Intent intent = mGoogleSignInClient.getSignInIntent();
        SignInActivityResultLauncher.launch(intent);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.d("EnterNameActivity::handleSignInResult()", "Non-testable method");

        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            updateUI(account);
        } catch (ApiException e) {
            Log.w("Error", "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
}