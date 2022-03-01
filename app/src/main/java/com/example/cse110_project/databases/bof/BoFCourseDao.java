package com.example.cse110_project.databases.bof;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface BoFCourseDao {
    @Transaction
    @Query("SELECT * FROM newCourse where new_student_id=:studentId")
    List<BoFCourse> getForStudent(int studentId);

    @Query("SELECT * FROM newCourse")
    List<BoFCourse> getAll();

    @Query("SELECT * FROM newCourse WHERE new_student_id=:id")
    BoFCourse get(int id);

    @Query("DELETE FROM newCourse")
    void delete();

    @Query("SELECT COUNT(*) from newCourse")
    int count();

    @Insert
    void insert(BoFCourse course);

    @Delete
    void delete(BoFCourse course);
}
