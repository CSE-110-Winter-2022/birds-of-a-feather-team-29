package com.example.cse110_project.databases.favorite;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favoriteStudents")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    public long id;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "studentName")
    public String studentName;

    public Favorite(String studentName , String url){
        this.studentName = studentName;
        this.url = url;
    }

    public String getName() {return studentName;}

    public long getId() {return id;}

    public String getUrl() {
        return url;
    }
}