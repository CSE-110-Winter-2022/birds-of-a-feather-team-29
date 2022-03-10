package com.example.cse110_project.databases.session;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.cse110_project.databases.bof.BoFStudent;

import java.util.List;

@Dao
public interface SessionDao {

    @Transaction
    @Query("SELECT * FROM session where session_name=:sessionName")
    List<BoFStudent> getForSession(String sessionName);

    @Query("SELECT * FROM session")
    List<BoFStudent> getAll();

    @Query("DELETE FROM session")
    void delete();

    @Insert
    void insert(Session session);

    @Delete
    void delete(Session session);

}
