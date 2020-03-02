package com.example.cse110_project.database;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(indices = {@Index("userName")}, tableName = "users")
public class UserEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;

    // For Firestore use
    public UserEntry(){}

    public UserEntry(String userName, String userEmail) {
        this.userName = userName;
        this.userEmail = userEmail;
    }

    // Basic route information
    private String userName;
    private String userEmail;
    private String teamID;


    public int getId() {
        return id;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getTeamID() { return teamID;
    }
    // This is for database use. It is automatically set. Don't set id directly.
    public void setId(int id) {
        this.id = id;
    }

    public void setTeamID(String teamID) { this.teamID = teamID; }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }


}