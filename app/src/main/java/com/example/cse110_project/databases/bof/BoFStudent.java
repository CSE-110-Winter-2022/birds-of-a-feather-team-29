package com.example.cse110_project.databases.bof;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "newStudents")
public class BoFStudent {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "new_student_id")
    public int newStudentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;

    @ColumnInfo(name = "course_size_score")
    private double sizeScore;

    @ColumnInfo(name = "recent_score")
    private int recentScore;

    public BoFStudent(String name, double sizeScore, int recentScore) {
        this.name = name;
        this.sizeScore = sizeScore;
        this.recentScore = recentScore;
    }

    public void setStudentId(int newStudentId) {
        this.newStudentId = newStudentId;
    }

    public int getStudentId() {
        return newStudentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFavorite(boolean isFavorite){ this.isFavorite = isFavorite; }

    public boolean getFavorite(){ return isFavorite; }

    public double getSizeScore() { return this.sizeScore; }

    public int getRecentScore() { return this.recentScore; }
}
