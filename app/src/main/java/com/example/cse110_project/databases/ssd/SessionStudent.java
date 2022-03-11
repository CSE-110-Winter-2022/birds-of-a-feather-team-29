package com.example.cse110_project.databases.ssd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "newSessionStudent")
public class SessionStudent {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "new_student_id")
    public int newStudentId;

    @ColumnInfo(name = "name")
    public String name;

    public SessionStudent(String name) {
        this.name = name;
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
}
