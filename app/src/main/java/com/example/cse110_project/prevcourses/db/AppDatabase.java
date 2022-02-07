package com.example.cse110_project.prevcourses.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Student.class, Course.class}, version = 1)

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context){
        if(singletonInstance == null){
            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "students.db")
                    .createFromAsset("starter-students.db")
                    .allowMainThreadQueries()
                    .build();

        }
        return singletonInstance;
    }

    public abstract StudentWithCoursesDao studentWithCoursesDao();
    public abstract CourseDao coursesDao();
}