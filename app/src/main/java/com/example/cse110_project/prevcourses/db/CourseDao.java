package com.example.cse110_project.prevcourses.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface CourseDao {
    @Transaction
    @Query("SELECT * FROM courses where student_id=:studentId")
    List<Course> getForStudent(int studentId);

    @Query("SELECT * FROM courses WHERE id=:id")
    Course get(int id);

    @Query("SELECT COUNT(*) from courses")
    int count();

    @Insert
    void insert(Course course);

    @Delete
    void delete(Course course);
}
