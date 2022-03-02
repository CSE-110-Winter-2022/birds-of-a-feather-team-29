package com.example.cse110_project.prevcourses.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "newStudents")
public class BoFStudent {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "new_student_id")
    public int newStudentId;

    @ColumnInfo(name = "previous_student_id")
    public int prevStudentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;

    public BoFStudent(int prevStudentId, String name) {
        this.prevStudentId = prevStudentId;
        this.name = name;
        this.isFavorite = false;

    }

    public void setStudentId(int newStudentId) {
        this.newStudentId = newStudentId;
    }

    public int getStudentId() {
        return newStudentId;
    }

    public int getPrevStudentId() { return prevStudentId; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavorite(boolean isFavorite){this.isFavorite = isFavorite;}

    public boolean getFavorite(){return isFavorite;}



}
