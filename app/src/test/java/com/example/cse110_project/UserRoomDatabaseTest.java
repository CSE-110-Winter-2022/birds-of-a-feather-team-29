package com.example.cse110_project;

import android.content.Context;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.cse110_project.databases.AppDatabase;
import com.example.cse110_project.databases.user.User;
import com.example.cse110_project.databases.user.UserCourse;
import com.example.cse110_project.databases.user.UserCourseDao;
import com.example.cse110_project.databases.user.UserDao;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class UserRoomDatabaseTest {
    UserDao ud;
    UserCourseDao ucd;
    AppDatabase db;

    @Before
    public void createTestDatabase() {
        Context context = ApplicationProvider.getApplicationContext();
        AppDatabase.useTestSingleton(context);
        db = AppDatabase.getSingletonInstance();
        ud = db.UserDao();
        ucd = db.UserCourseDao();
    }

    @Test
    public void test_First_Name_In_Room_Database_Retained() {
        ud.insert(new User("Bob", "default.link/null"));
        List<User> ul = db.UserDao().getAll();
        assert(ul.get(0).getUserFirstName().equals("Bob"));
        AppDatabase db2 = AppDatabase.getSingletonInstance();
        System.out.println(db2.UserDao().getAll().get(0).getUserFirstName());
        assert(db2.UserDao().getAll().get(0).getUserFirstName().equals("Bob"));
    }

    @Test
    public void test_URL_To_Headshot_In_Room_Database_Retained() {
        ud.insert(new User("Bob", "default.link/null"));
        List<User> ul = db.UserDao().getAll();
        assert(ul.get(0).getHeadshotURL().equals("default.link/null"));
        AppDatabase db2 = AppDatabase.getSingletonInstance();
        assert(db2.UserDao().getAll().get(0).getHeadshotURL().equals("default.link/null"));
    }

    @Test
    public void test_Past_Courses_In_Room_Database_Retained() {
        ucd.insert(new UserCourse("2017", "Fall", "Tiny (1-40)", "CSE", "11"));
        ucd.insert(new UserCourse("2017", "Fall","Tiny (1-40)", "CSE", "12"));
        ucd.insert(new UserCourse("2017", "Winter", "Medium (75-150)","CSE", "21"));
        ucd.insert(new UserCourse("2018", "Spring","Huge (250-400)", "CSE", "11"));
        List<UserCourse> uc = db.UserCourseDao().getAll();
        assert(uc.size() == 4);
        AppDatabase db2 = AppDatabase.getSingletonInstance();
        assert(db2.UserCourseDao().getAll().size() == 4);
    }
}
