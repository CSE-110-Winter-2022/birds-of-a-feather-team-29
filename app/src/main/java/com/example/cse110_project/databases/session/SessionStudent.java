package com.example.cse110_project.databases.session;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "sessionStudent")
public class SessionStudent {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "session_student_id")
    private int sessionStudentId;

    @ColumnInfo(name = "url")
    public String url;

    @ColumnInfo(name = "session_name")
    private String sessionName;

    @ColumnInfo(name = "session_student_name")
    private String sessionStudentName;

    @ColumnInfo(name = "num_of_shared_courses")
    private int numOfSharedCourses;

    public SessionStudent(String sessionName, String sessionStudentName, int numOfSharedCourses, String url) {
        this.sessionName = sessionName;
        this.sessionStudentName = sessionStudentName;
        this.numOfSharedCourses = numOfSharedCourses;
        this.url = url;
    }

    public void setSessionStudentId(int sessionStudentId) { this.sessionStudentId = sessionStudentId; }

    public int getSessionStudentId() { return this.sessionStudentId; }

    public String getSessionName() { return this.sessionName; }

    public String getSessionStudentName() { return this.sessionStudentName; }

    public int getNumOfSharedCourses() { return this.numOfSharedCourses; }

    public String getUrl() {
        return url;
    }
}
