package com.example.cse110_project.databases.session;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface SessionDao {

    @Query("SELECT * FROM session")
    List<Session> getAll();

    @Query("SELECT * FROM session where session_name=:sessionName")
    Session get(String sessionName);

    @Query("UPDATE session SET session_name=:sessionName WHERE session_name=:defaultSessionName")
    void updateSessionName(String sessionName, String defaultSessionName);

    @Query("DELETE FROM session")
    void delete();

    @Insert
    void insert(Session session);

    @Delete
    void delete(Session session);

}
