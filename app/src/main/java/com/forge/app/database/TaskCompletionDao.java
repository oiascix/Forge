package com.forge.app.database;

import androidx.lifecycle.LiveData;
import androidx.room.*;
import com.forge.app.models.TaskCompletion;
import java.util.List;

@Dao
public interface TaskCompletionDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TaskCompletion completion);

    @Query("DELETE FROM task_completions WHERE taskId = :taskId AND dateString = :date")
    void delete(int taskId, String date);

    // Is this task completed on a given date?
    @Query("SELECT COUNT(*) > 0 FROM task_completions WHERE taskId = :taskId AND dateString = :date")
    boolean isCompletedOnDate(int taskId, String date);

    @Query("SELECT COUNT(*) > 0 FROM task_completions WHERE taskId = :taskId AND dateString = :date")
    LiveData<Boolean> isCompletedOnDateLive(int taskId, String date);

    // All completion dates for a task (for calendar), sorted desc
    @Query("SELECT dateString FROM task_completions WHERE taskId = :taskId ORDER BY dateString DESC")
    LiveData<List<String>> getCompletionDatesForTask(int taskId);

    @Query("SELECT dateString FROM task_completions WHERE taskId = :taskId ORDER BY dateString DESC")
    List<String> getCompletionDatesForTaskSync(int taskId);

    // All completions for today
    @Query("SELECT taskId FROM task_completions WHERE dateString = :date")
    List<Integer> getCompletedTaskIdsForDate(String date);

    @Query("SELECT taskId FROM task_completions WHERE dateString = :date")
    LiveData<List<Integer>> getCompletedTaskIdsForDateLive(String date);

    // Last N days for heatmap (returns all completions in range)
    @Query("SELECT * FROM task_completions WHERE taskId = :taskId AND dateString >= :fromDate ORDER BY dateString ASC")
    List<TaskCompletion> getCompletionsSince(int taskId, String fromDate);
}
