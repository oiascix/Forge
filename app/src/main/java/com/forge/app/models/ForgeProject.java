package com.forge.app.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "projects")
public class ForgeProject {
    @PrimaryKey(autoGenerate = true)
    public int id;
    public String name;
    public int currentHeat;
    public int maxHeat;
    public String timeLeft;
    public boolean isActive;

    public ForgeProject(String name, int currentHeat, int maxHeat, String timeLeft) {
        this.name = name;
        this.currentHeat = currentHeat;
        this.maxHeat = maxHeat;
        this.timeLeft = timeLeft;
        this.isActive = true;
    }
}
