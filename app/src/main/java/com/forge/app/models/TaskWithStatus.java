package com.forge.app.models;

/**
 * Joins Task with whether it was completed today.
 * Used in HomeFragment so we don't query DB per-item.
 */
public class TaskWithStatus {
    public Task task;
    public boolean completedToday;

    public TaskWithStatus(Task task, boolean completedToday) {
        this.task = task;
        this.completedToday = completedToday;
    }
}
