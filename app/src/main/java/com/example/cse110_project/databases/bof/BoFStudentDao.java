package com.example.cse110_project.databases.bof;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BoFStudentDao {
    @Query("SELECT * FROM newStudents")
    List<BoFStudent> getAll();

    @Query("SELECT * FROM newStudents WHERE new_student_id=:id")
    BoFStudent get(int id);

    @Query("DELETE FROM newStudents")
    void delete();

    @Query("UPDATE newStudents SET course_size_score=:score WHERE new_student_id=:id")
    void updateCourseSizeScore(double score, int id);

    @Query("UPDATE newStudents SET recent_score=:score WHERE new_student_id=:id")
    void updateRecentScore(int score, int id);

    @Insert
    void insert(BoFStudent student);

    @Delete
    void delete(BoFStudent student);
}
