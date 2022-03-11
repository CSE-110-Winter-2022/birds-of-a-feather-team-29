package com.example.cse110_project.databases.ssd;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

public interface SessionDao {

    @Query("SELECT * FROM newSession")
    List<Session> getAll();

    @Query("SELECT * FROM newSession WHERE new_session_id=:id")
    Session get(int id);

    @Query("DELETE FROM newSession")
    void delete();

    @Insert
    void insert(Session session);

    @Delete
    void delete(Session session);
}
