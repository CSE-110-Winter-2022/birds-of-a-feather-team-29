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

    @ColumnInfo(name = "classSize")
    public String classSize;

    @ColumnInfo(name = "course")
    public String course;

    @ColumnInfo(name = "course_num")
    private String courseNum;

    @ColumnInfo(name = "course_added")
    private boolean courseAdded;

    public DefaultCourse(int studentId, String year, String quarter, String course, String courseNum,
                         String classSize, boolean courseAdded) {
        this.studentId = studentId;
        this.year = year;
        this.quarter = quarter;
        this.classSize = classSize;
        this.course = course;
        this.courseNum = courseNum;
        this.courseAdded = courseAdded;
    }

    public int getCourseId() { return this.courseId; }

    public int getStudentId() {
        return studentId;
    }

    public String getYear() { return year; }

    public String getQuarter() { return quarter; }

    public String getClassSize() { return this.classSize; }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getCourseNum() {
        return this.courseNum;
    }

    public boolean getCourseAdded() { return this.courseAdded; }
}
