package com.example.cse110_project.utilities;

import android.app.Activity;
import android.app.AlertDialog;

import com.example.cse110_project.utilities.Constants;

public class Utilities {
    public static void showAlert(Activity activity, String title, String message) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(activity);

        alertBuilder
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton(Constants.OK, (dialog, id) -> {
                    dialog.cancel();
                })
                .setCancelable(true);


        AlertDialog alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
