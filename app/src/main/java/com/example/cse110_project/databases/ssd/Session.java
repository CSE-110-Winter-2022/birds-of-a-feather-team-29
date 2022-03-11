package com.example.cse110_project.databases.ssd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "newSession")
public class Session {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "new_session_id")
    public int new_session_id;

    @ColumnInfo(name = "newSessionStudentList")
    public List<SessionStudent> sessionStudentList;

    @ColumnInfo(name = "newSessionCourseList")
    public List<SessionCourse> sessionCourseList;

    @ColumnInfo(name = "time")
    public String time;

    public Session (List<SessionStudent> sessionStudentList, List<SessionCourse> sessionCourseList,
        String time) {
        this.sessionStudentList = sessionStudentList;
        this.sessionCourseList = sessionCourseList;
        this.time =  time;
    }

    public List<SessionStudent> getSessionStudentList() {
        return sessionStudentList;
    }

    public List<SessionCourse> getSessionCourseList() {
        return sessionCourseList;
    }

    public String getTime() {
        return time;
    }

    public int getNew_session_id() {
        return new_session_id;
    }
}

