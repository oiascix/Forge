package com.forge.app.workers;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.*;
import com.forge.app.database.AppDatabase;
import com.forge.app.models.UserProfile;
import com.forge.app.utils.DateUtils;
import java.util.concurrent.TimeUnit;

/**
 * Runs once per day just after midnight.
 * Updates lastSeenDate on the UserProfile so HomeFragment
 * knows a new day has started and refreshes the UI.
 * Streaks are computed live from TaskCompletion — no reset needed.
 */
public class MidnightResetWorker extends Worker {

    public static final String WORK_TAG = "midnight_reset";

    public MidnightResetWorker(@NonNull Context ctx, @NonNull WorkerParameters params) {
        super(ctx, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        AppDatabase db = AppDatabase.getDatabase(getApplicationContext());
        UserProfile profile = db.userProfileDao().getProfileSync();
        if (profile != null) {
            profile.lastSeenDate = DateUtils.today();
            db.userProfileDao().update(profile);
        }
        // Re-schedule streaks update for all tasks
        updateAllStreaks(db);
        return Result.success();
    }

    private void updateAllStreaks(AppDatabase db) {
        for (com.forge.app.models.Task task : db.taskDao().getAllTasksSync()) {
            java.util.List<String> dates =
                    db.taskCompletionDao().getCompletionDatesForTaskSync(task.id);
            task.currentStreak = DateUtils.computeStreak(dates);
            task.bestStreak    = DateUtils.computeBestStreak(dates);
            db.taskDao().update(task);
        }
    }

    /** Schedule periodic work — runs daily, aligned to just after midnight. */
    public static void schedule(Context ctx) {
        // Calculate initial delay until next midnight
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
        cal.set(java.util.Calendar.HOUR_OF_DAY, 0);
        cal.set(java.util.Calendar.MINUTE, 1);
        cal.set(java.util.Calendar.SECOND, 0);
        long delay = cal.getTimeInMillis() - System.currentTimeMillis();

        PeriodicWorkRequest request = new PeriodicWorkRequest.Builder(
                MidnightResetWorker.class, 1, TimeUnit.DAYS)
                .setInitialDelay(delay, TimeUnit.MILLISECONDS)
                .addTag(WORK_TAG)
                .build();

        WorkManager.getInstance(ctx).enqueueUniquePeriodicWork(
                WORK_TAG,
                ExistingPeriodicWorkPolicy.KEEP,
                request);
    }
}
