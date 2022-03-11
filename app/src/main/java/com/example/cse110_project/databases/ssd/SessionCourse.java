package com.example.cse110_project.databases.ssd;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "newSessionCourse")
public class SessionCourse {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "new_course_id")
    public int courseId = 0;

    @ColumnInfo(name = "new_student_id")
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

    public SessionCourse(int studentId, String year, String quarter, String classSize,
                     String course, String courseNum) {
        this.studentId = studentId;
        this.year = year;
        this.quarter = quarter;
        this.classSize = classSize;
        this.course = course;
        this.courseNum = courseNum;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getYear() { return year; }

    public String getQuarter() { return quarter; }

    public String getClassSize() { return this.classSize; }

    public String getCourse() {
        return course;
    }

    public String getCourseNum() {
        return this.courseNum;
    }
}
