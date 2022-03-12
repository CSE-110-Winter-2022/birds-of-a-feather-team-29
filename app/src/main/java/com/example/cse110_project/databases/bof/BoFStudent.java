package com.example.cse110_project.databases.bof;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cse110_project.utilities.Constants;

@Entity(tableName = "newStudents")
public class BoFStudent {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    public int newStudentId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;

    @ColumnInfo(name = "course_size_score")
    private double sizeScore;

    @ColumnInfo(name = "recent_score")
    private int recentScore;

    @ColumnInfo(name = "is_waving")
    private boolean isWaving;

    @ColumnInfo(name = "am_i_waving")
    private boolean amIWaving;

    public BoFStudent(String name, double sizeScore, int recentScore, boolean isWaving, String url) {
        this.name = name;
        this.sizeScore = sizeScore;
        this.recentScore = recentScore;
        this.isWaving = isWaving;
        this.amIWaving = false;
        this.url = url;
    }

    public void setStudentId(int newStudentId) {
        this.newStudentId = newStudentId;
    }

    public int getStudentId() {
        return newStudentId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setFavorite(boolean isFavorite){ this.isFavorite = isFavorite; }

    public boolean getFavorite(){ return isFavorite; }

    public void setSetSizeScore(double sizeScore) { this.sizeScore = sizeScore; }

    public double getSizeScore() { return this.sizeScore; }

    public void setRecentScore(int recentScore) { this.recentScore = recentScore; }

    public int getRecentScore() { return this.recentScore; }

    public void setIsWaving(boolean isWaving) { this.isWaving = isWaving; }

    public boolean getIsWaving() { return this.isWaving; }

    public void setAmIWaving(boolean amIWaving) {this.amIWaving = amIWaving; }

    public boolean getAmIWaving() {return this.amIWaving; }

    public String getUrl() {
        return url;
    }

    public void setUrl(){ this.url = url; }
}
