package com.forge.app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "tasks")
public class Task {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String title;
    public String subtitle;
    public String iconType;      // brain | book | gym | drop | code
    public boolean isFeatured;
    public int sortOrder;

    // Reminder
    public boolean reminderEnabled;
    public int reminderHour;     // 0-23
    public int reminderMinute;   // 0-59

    // Streak (computed and cached)
    public int currentStreak;    // days in a row completed
    public int bestStreak;

    public Task(String title, String subtitle, String iconType, boolean isFeatured, int sortOrder) {
        this.title = title;
        this.subtitle = subtitle;
        this.iconType = iconType;
        this.isFeatured = isFeatured;
        this.sortOrder = sortOrder;
        this.reminderEnabled = false;
        this.reminderHour = 9;
        this.reminderMinute = 0;
        this.currentStreak = 0;
        this.bestStreak = 0;
    }
}
