/*
 * Source:
 *
 * How to permanently update data in Room database -
 *  1) Title: Update some specific field of an entity in android Room
 *     Link: https://stackoverflow.com/questions/45789325/update-some-specific-field-of-an-entity-in-android-room
 *     Date: 2/9/22 - 2/11/22
 *     Source used for...: Understanding how to update data in Room database
 * */

package com.example.cse110_project.databases.def;

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

    @Query("UPDATE students SET is_waving=:isWaving WHERE student_id=:id")
    void updateIsWaving(boolean isWaving, int id);

    @Insert
    void insert(DefaultStudent defaultStudent);

    @Delete
    void delete(DefaultStudent defaultStudent);
}