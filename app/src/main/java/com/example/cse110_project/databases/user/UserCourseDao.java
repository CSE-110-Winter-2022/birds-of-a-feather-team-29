package com.example.cse110_project.databases.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserCourseDao {
    @Query("SELECT * FROM userCourses")
    List<UserCourse> getAll();

    @Query("DELETE FROM userCourses")
    void delete();

    @Insert
    void insert(UserCourse course);
}
