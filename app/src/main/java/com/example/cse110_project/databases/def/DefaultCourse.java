package com.example.cse110_project.databases.def;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "courses")
public class DefaultCourse {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    public int courseId = 0;

    @ColumnInfo(name = "student_id")
    public int studentId;

    @ColumnInfo(name = "year")
    public String year;

    @ColumnInfo(name = "quarter")
    public String quarter;

    @ColumnInfo(name = "course")
    public String course;

    public DefaultCourse(int studentId, String year, String quarter, String course) {
        this.studentId = studentId;
        this.year = year;
        this.quarter = quarter;
        this.course = course;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getYear() { return year; }

    public String getQuarter() { return quarter; }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
