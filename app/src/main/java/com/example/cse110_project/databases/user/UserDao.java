package com.example.cse110_project.databases.user;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("UPDATE user SET user_First_Name=:userFirstName WHERE user_Id=:userId")
    void updateFirstName(String userFirstName, int userId);

    @Query("UPDATE user SET headshot_URL=:headshotURL WHERE user_Id=:userId")
    void updateHeadshotURL(String headshotURL, int userId);

    @Insert
    void insert(User user);
}
