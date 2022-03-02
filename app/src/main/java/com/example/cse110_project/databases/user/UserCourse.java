package com.example.cse110_project.databases.user;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userCourses")
public class UserCourse {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    public int courseId;

    @ColumnInfo(name = "year")
    private String year;

    @ColumnInfo(name = "quarter")
    private String quarter;

    @ColumnInfo(name = "classSize")
    private String classSize;

    @ColumnInfo(name = "course")
    private String course;

    @ColumnInfo(name = "course_num")
    private String courseNum;

    public UserCourse(String year, String quarter, String classSize, String course, String courseNum) {
        this.year = year;
        this.quarter = quarter;
        this.classSize = classSize;
        this.course = course;
        this.courseNum = courseNum;
    }

    public String getYear() {
        return this.year;
    }

    public String getQuarter() {
        return this.quarter;
    }

    public String getClassSize() { return this.classSize; }

    public String getCourse() {
        return this.course;
    }

    public String getCourseNum() {
        return this.courseNum;
    }
}
