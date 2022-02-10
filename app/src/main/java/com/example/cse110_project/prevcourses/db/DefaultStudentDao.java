package com.example.cse110_project.prevcourses.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DefaultStudentDao {
    @Query("SELECT * FROM students")
    List<DefaultStudent> getAll();

    @Query("SELECT * FROM students WHERE student_id=:id")
    DefaultStudent get(int id);

    @Query("DELETE FROM students")
    void delete();

    @Query("UPDATE students SET encountered=:encounter WHERE student_id=:id")
    void updateEncountered(boolean encounter, int id);

    @Insert
    void insert(DefaultStudent defaultStudent);

    @Delete
    void delete(DefaultStudent defaultStudent);
}

/**
 * Source:
 *
 * How to permanently update data in Room database -
 * https://stackoverflow.com/questions/45789325/update-some-specific-field-of-an-entity-in-android-room
 * */