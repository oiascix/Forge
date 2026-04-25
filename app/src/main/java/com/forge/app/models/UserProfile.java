package com.forge.app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_profile")
public class UserProfile {
    @PrimaryKey
    public int id = 1;
    public String name;
    public String lastSeenDate; // "yyyy-MM-dd" — used to detect day change

    public UserProfile(String name, String lastSeenDate) {
        this.name = name;
        this.lastSeenDate = lastSeenDate;
    }
}
