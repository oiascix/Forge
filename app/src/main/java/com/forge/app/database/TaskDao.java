package com.forge.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.forge.app.models.Task;
import java.util.List;

@Dao
public interface TaskDao {
    @Insert long insert(Task task);
    @Update void update(Task task);
    @Delete void delete(Task task);

    @Query("SELECT * FROM tasks ORDER BY isFeatured DESC, sortOrder ASC")
    LiveData<List<Task>> getAllTasks();

    @Query("SELECT * FROM tasks ORDER BY isFeatured DESC, sortOrder ASC")
    List<Task> getAllTasksSync();

    @Query("SELECT * FROM tasks WHERE isFeatured = 1 LIMIT 1")
    LiveData<Task> getFeaturedTask();

    @Query("SELECT * FROM tasks WHERE isFeatured = 0 ORDER BY sortOrder ASC")
    LiveData<List<Task>> getRegularTasks();

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    LiveData<Task> getTaskById(int id);

    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    Task getTaskByIdSync(int id);

    @Query("SELECT COUNT(*) FROM tasks")
    LiveData<Integer> getTotalCount();

    // Count completed today
    @Query("SELECT COUNT(*) FROM task_completions WHERE dateString = :date")
    LiveData<Integer> getCompletedCountForDate(String date);

    @Query("SELECT COUNT(*) FROM task_completions WHERE dateString = :date")
    int getCompletedCountForDateSync(String date);
}
