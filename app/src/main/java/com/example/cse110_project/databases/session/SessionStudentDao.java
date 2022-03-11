package com.example.cse110_project.databases.session;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionStudentDao {

    @Query("SELECT * FROM sessionStudent WHERE session_name=:sessionName")
    List<SessionStudent> getForSession(String sessionName);

    @Query("UPDATE sessionStudent SET session_name=:newSessionName WHERE session_name=:oldSessionName")
    void updateSessionNameOfSessionStudent(String newSessionName, String oldSessionName);

    @Query("DELETE FROM sessionStudent")
    void delete();

    @Insert
    void insert(SessionStudent student);

}
