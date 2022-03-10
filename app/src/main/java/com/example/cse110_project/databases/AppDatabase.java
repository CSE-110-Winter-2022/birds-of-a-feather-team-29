/*
 * Source(s):
 *
 * Adding databases/migration in regards to Room -
 * 1) Title: Understanding migrations with Room
 *    Link: https://medium.com/androiddevelopers/understanding-migrations-with-room-f01e04b07929
 *    Date: 2/9/22 - 2/11/22
 *    Source used for...: Understanding what to do when additional databases are added
 * */

package com.example.cse110_project.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.cse110_project.databases.bof.BoFCourse;
import com.example.cse110_project.databases.bof.BoFCourseDao;
import com.example.cse110_project.databases.bof.BoFStudent;
import com.example.cse110_project.databases.bof.BoFStudentDao;
import com.example.cse110_project.databases.def.DefaultCourse;
import com.example.cse110_project.databases.def.DefaultCourseDao;
import com.example.cse110_project.databases.def.DefaultStudent;
import com.example.cse110_project.databases.def.DefaultStudentDao;
import com.example.cse110_project.databases.favorite.Favorite;
import com.example.cse110_project.databases.favorite.FavoriteDao;
import com.example.cse110_project.databases.session.Session;
import com.example.cse110_project.databases.session.SessionDao;
import com.example.cse110_project.databases.session.SessionStudent;
import com.example.cse110_project.databases.session.SessionStudentDao;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.databases.user.UserCourseDao;
import com.example.cse110_project.databases.user.UserDao;

@Database(entities = {DefaultStudent.class, DefaultCourse.class, BoFStudent.class, BoFCourse.class,
        User.class, UserCourse.class, Favorite.class, Session.class, SessionStudent.class}, version = 4, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase singletonInstance;

    public static AppDatabase singleton(Context context){
        if(singletonInstance == null){
            singletonInstance = Room.databaseBuilder(context, AppDatabase.class, "students.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return singletonInstance;
    }

    public static void useTestSingleton(Context context) {
        singletonInstance = Room.inMemoryDatabaseBuilder(context, AppDatabase.class)
                .allowMainThreadQueries()
                .build();
    }

    public abstract DefaultStudentDao DefaultStudentDao();

    public abstract DefaultCourseDao DefaultCourseDao();

    public abstract BoFStudentDao BoFStudentDao();

    public abstract BoFCourseDao BoFCourseDao();

    public abstract FavoriteDao FavoriteDao();

    public abstract UserDao UserDao();

    public abstract UserCourseDao UserCourseDao();

    public abstract SessionDao SessionDao();

    public abstract SessionStudentDao SessionStudentDao();

    public static AppDatabase getSingletonInstance() {
        return singletonInstance;
    }
}