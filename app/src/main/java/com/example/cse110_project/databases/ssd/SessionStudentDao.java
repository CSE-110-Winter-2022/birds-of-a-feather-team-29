package com.example.cse110_project.databases.ssd;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface SessionStudentDao {

    @Query("SELECT * FROM newSessionStudent")
    List<SessionStudent> getAll();

    @Query("SELECT * FROM newSessionStudent WHERE new_student_id=:id")
    SessionStudent get(int id);

    @Query("DELETE FROM newSessionStudent")
    void delete();

    @Insert
    void insert(SessionStudent sessionStudent);

    @Delete
    void delete(SessionStudent sessionStudent);
}