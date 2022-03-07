package com.example.cse110_project.utilities;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesDatabase {
    // ignore comment
    public static void clearCurrEnteredCoursesDatabase(Context context) {
        SharedPreferences curr = context.getSharedPreferences(Constants.CURR_ENTERED_COURSES_DB,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor currEnteredClassesEditor = curr.edit();
        currEnteredClassesEditor.clear();
        currEnteredClassesEditor.apply();

        SharedPreferences userCourseInfo = context.getSharedPreferences(Constants.MAIN_USER_COURSE_DB,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor mainUserClassInfoEditor = userCourseInfo.edit();
        mainUserClassInfoEditor.clear();
        mainUserClassInfoEditor.apply();
    }

    public static SharedPreferences getDatabase(Context context, String database) {
        return context.getSharedPreferences(database, Context.MODE_PRIVATE);
    }
}
