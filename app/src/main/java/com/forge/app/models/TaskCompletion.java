package com.forge.app.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;

/**
 * One row per (taskId, dateString) where dateString = "yyyy-MM-dd".
 * This table drives streaks and the calendar history view.
 */
@Entity(
    tableName = "task_completions",
    primaryKeys = {"taskId", "dateString"},
    foreignKeys = @ForeignKey(
        entity = Task.class,
        parentColumns = "id",
        childColumns = "taskId",
        onDelete = ForeignKey.CASCADE
    ),
    indices = @Index("taskId")
)
public class TaskCompletion {
    public int taskId;
    @NonNull
    public String dateString; // "yyyy-MM-dd"

    public TaskCompletion(int taskId, String dateString) {
        this.taskId = taskId;
        this.dateString = dateString;
    }
}
