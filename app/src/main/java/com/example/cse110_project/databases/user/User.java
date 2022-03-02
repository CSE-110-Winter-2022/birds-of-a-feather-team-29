package com.example.cse110_project.databases.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_Id")
    public int userId;

    @ColumnInfo(name = "user_First_Name")
    private String userFirstName;

    @ColumnInfo(name = "headshot_URL")
    private String headshotURL;

    public User(String userFirstName, String headshotURL) {
        this.userFirstName = userFirstName;
        this.headshotURL = headshotURL;
    }

    public int getUserId() {
        return this.userId;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserFirstName() {
        return this.userFirstName;
    }

    public void setHeadshotURL(String headshotURL) {
        this.headshotURL = headshotURL;
    }

    public String getHeadshotURL() {
        return this.headshotURL;
    }
}
