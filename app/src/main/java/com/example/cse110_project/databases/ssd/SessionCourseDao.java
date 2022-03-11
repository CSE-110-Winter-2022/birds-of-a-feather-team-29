package com.example.cse110_project.databases.ssd;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

public interface SessionCourseDao {

    @Transaction
    @Query("SELECT * FROM newSessionCourse where new_student_id=:studentId")
    List<SessionCourse> getForStudent(int studentId);

    @Query("SELECT * FROM newSessionCourse")
    List<SessionCourse> getAll();

    @Query("SELECT * FROM newSessionCourse WHERE new_student_id=:id")
    SessionCourse get(int id);

    @Query("DELETE FROM newSessionCourse")
    void delete();

    @Insert
    void insert(SessionCourse sessionCourse);

    @Delete
    void delete(SessionCourse sessionCourse);
}
