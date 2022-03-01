/*
 * Source(s):
 *
 * For deleting database content:
 * 1) Title: Room persistence library. Delete all
 *    Link: https://stackoverflow.com/questions/44244508/room-persistance-library-delete-all
 *    Date: 2/9/22 - 2/11/22
 *    Source used for...: Understanding how to delete data in the Room database
 * */

package com.example.cse110_project.databases.def;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import java.util.List;

@Dao
public interface DefaultCourseDao {
    @Transaction
    @Query("SELECT * FROM courses where student_id=:studentId")
    List<DefaultCourse> getForStudent(int studentId);

    @Query("SELECT * FROM courses")
    List<DefaultCourse> getAll();

    @Query("SELECT * FROM courses WHERE student_id=:id")
    DefaultCourse get(int id);

    @Query("DELETE FROM courses")
    void delete();

    @Insert
    void insert(DefaultCourse defaultCourse);

    @Delete
    void delete(DefaultCourse defaultCourse);
}