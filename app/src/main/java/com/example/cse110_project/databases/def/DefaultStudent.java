package com.example.cse110_project.databases.def;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cse110_project.utilities.Constants;

@Entity(tableName = "students")
public class DefaultStudent {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    public int studentId = 0;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "is_waving")
    public boolean isWaving;

    public DefaultStudent(String name) {
        this.name = name;
        this.isWaving = false;
        this.url = Constants.DEFAULT_PIC_LINK;
    }

    public DefaultStudent(String name, String url) {
        this.name = name;
        this.isWaving = false;
        this.url = url;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setIsWaving() { this.isWaving = true; }

    public boolean getIsWaving() { return this.isWaving; }

    public String getUrl() {
        return url;
    }

    public void setUrl(){ this.url = url; }

}
