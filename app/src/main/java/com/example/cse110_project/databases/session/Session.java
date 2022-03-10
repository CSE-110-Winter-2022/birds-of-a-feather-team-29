/*
* Source(s):
*
* https://www.javatpoint.com/java-get-current-date
* */

package com.example.cse110_project.databases.session;

import androidx.annotation.NonNull;
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
    @NonNull
    @ColumnInfo(name = "session_name")
    public String sessionName;

    public Session(String sessionName) {
        this.sessionName = (sessionName == null) ? getCurrentTime() : sessionName;
    }

    public String getSessionName() { return this.sessionName; }

    private String getCurrentTime() {
        DateTimeFormatter dateTime = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        return dateTime.format(LocalDateTime.now());
    }

}
