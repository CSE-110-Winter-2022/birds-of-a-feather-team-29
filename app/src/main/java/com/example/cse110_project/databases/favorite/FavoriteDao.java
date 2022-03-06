package com.example.cse110_project.databases.favorite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.cse110_project.databases.def.DefaultStudent;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favoriteStudents")
    List<Favorite> getAll();

    @Insert
    long insert(Favorite student);

    @Query("DELETE FROM favoriteStudents WHERE id = :id")
    void deleteById(long id);

    @Delete
    void delete(Favorite favorite);
}
