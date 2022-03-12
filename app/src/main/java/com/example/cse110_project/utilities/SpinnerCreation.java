package com.example.cse110_project.utilities;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SpinnerCreation {
    public static void createSpinner(Context context, Spinner spinner, int array) {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);
    }
}
