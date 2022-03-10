/*
* Source(s):
*
* https://www.javatpoint.com/java-get-current-date
* */

package com.example.cse110_project.databases.session;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.cse110_project.databases.bof.BoFStudent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity(tableName = "session")
public class Session {

    @PrimaryKey
    @ColumnInfo(name = "session_name")
    public String sessionName;

    @ColumnInfo(name = "bof_student_list")
    List<BoFStudent> bofList;

    public Session(String sessionName, List<BoFStudent> bofList) {
        this.sessionName = (sessionName == null) ? getCurrentTime() : sessionName;
        this.bofList = bofList;
    }

    public String getSessionName() { return this.sessionName; }

    public List<BoFStudent> getBoFList() { return this.bofList; }

    private String getCurrentTime() {
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dateTime.format(LocalDateTime.now());
    }

}
