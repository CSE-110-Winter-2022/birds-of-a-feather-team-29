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
    private String sessionName;

    public Session(String sessionName) {
        this.sessionName = sessionName;
    }

    public String getSessionName() { return this.sessionName; }

}
