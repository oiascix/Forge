package com.forge.app.activities;

import android.app.Application;
import androidx.lifecycle.*;
import com.forge.app.database.AppDatabase;
import com.forge.app.models.*;
import com.forge.app.utils.DateUtils;
import com.forge.app.utils.NotificationHelper;
import java.util.ArrayList;
import java.util.List;

public class MainViewModel extends AndroidViewModel {

    private final AppDatabase db;
    public final String today = DateUtils.today();

    // Raw LiveData
    public final LiveData<UserProfile>       profile;
    public final LiveData<ForgeProject>      activeProject;
    public final LiveData<List<Task>>        allTasks;
    public final LiveData<Task>              featuredTask;
    public final LiveData<List<Task>>        regularTasks;
    public final LiveData<List<ForgeProject>> allProjects;
    public final LiveData<Integer>           totalCount;
    public final LiveData<Integer>           completedTodayCount;
    public final LiveData<List<Integer>>     completedTaskIdsToday;

    // Combined: tasks annotated with today's completion status
    public final LiveData<List<TaskWithStatus>> tasksWithStatus;

    public MainViewModel(Application app) {
        super(app);
        db = AppDatabase.getDatabase(app);

        profile              = db.userProfileDao().getProfile();
        activeProject        = db.projectDao().getActiveProject();
        allTasks             = db.taskDao().getAllTasks();
        featuredTask         = db.taskDao().getFeaturedTask();
        regularTasks         = db.taskDao().getRegularTasks();
        allProjects          = db.projectDao().getAllProjects();
        totalCount           = db.taskDao().getTotalCount();
        completedTodayCount  = db.taskDao().getCompletedCountForDate(today);
        completedTaskIdsToday = db.taskCompletionDao().getCompletedTaskIdsForDateLive(today);

        // Merge tasks + completion ids → TaskWithStatus list
        tasksWithStatus = new MediatorLiveData<List<TaskWithStatus>>() {{
            addSource(allTasks,              tasks  -> combine(tasks, completedTaskIdsToday.getValue()));
            addSource(completedTaskIdsToday, ids    -> combine(allTasks.getValue(), ids));
        }};
    }

    private void combine(List<Task> tasks, List<Integer> ids) {
        if (tasks == null) return;
        List<Integer> completedIds = ids != null ? ids : new ArrayList<>();
        List<TaskWithStatus> result = new ArrayList<>();
        for (Task t : tasks) {
            result.add(new TaskWithStatus(t, completedIds.contains(t.id)));
        }
        ((MediatorLiveData<List<TaskWithStatus>>) tasksWithStatus).setValue(result);
    }

    // ── Toggle completion ────────────────────────────────────────────────────

    public void toggleCompletion(Task task, boolean completed) {
        AppDatabase.executor.execute(() -> {
            if (completed) {
                db.taskCompletionDao().insert(new TaskCompletion(task.id, today));
            } else {
                db.taskCompletionDao().delete(task.id, today);
            }
            // Recompute streak and persist
            List<String> dates = db.taskCompletionDao().getCompletionDatesForTaskSync(task.id);
            task.currentStreak = DateUtils.computeStreak(dates);
            task.bestStreak    = DateUtils.computeBestStreak(dates);
            db.taskDao().update(task);
        });
    }

    // ── CRUD ─────────────────────────────────────────────────────────────────

    public void insertTask(Task task) {
        AppDatabase.executor.execute(() -> {
            long id = db.taskDao().insert(task);
            task.id = (int) id;
            if (task.reminderEnabled) NotificationHelper.scheduleReminder(getApplication(), task);
        });
    }

    public void updateTask(Task task) {
        AppDatabase.executor.execute(() -> {
            db.taskDao().update(task);
            NotificationHelper.scheduleReminder(getApplication(), task);
        });
    }

    public void deleteTask(Task task) {
        AppDatabase.executor.execute(() -> {
            NotificationHelper.cancelReminder(getApplication(), task.id);
            db.taskDao().delete(task);
        });
    }

    public void insertProject(ForgeProject p) {
        AppDatabase.executor.execute(() -> db.projectDao().insert(p));
    }

    public void updateProject(ForgeProject p) {
        AppDatabase.executor.execute(() -> db.projectDao().update(p));
    }

    public void deleteProject(ForgeProject p) {
        AppDatabase.executor.execute(() -> db.projectDao().delete(p));
    }

    public void updateProfile(UserProfile u) {
        AppDatabase.executor.execute(() -> db.userProfileDao().update(u));
    }

    // ── Per-task history (for detail screen) ─────────────────────────────────

    public LiveData<List<String>> getCompletionDates(int taskId) {
        return db.taskCompletionDao().getCompletionDatesForTask(taskId);
    }

    public LiveData<Task> getTaskById(int taskId) {
        return db.taskDao().getTaskById(taskId);
    }
}
