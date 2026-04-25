package com.forge.app.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.forge.app.database.AppDatabase;
import com.forge.app.models.Task;
import java.util.List;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        if (!Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) return;
        AppDatabase.executor.execute(() -> {
            List<Task> tasks = AppDatabase.getDatabase(ctx).taskDao().getAllTasksSync();
            for (Task t : tasks) {
                if (t.reminderEnabled) {
                    NotificationHelper.scheduleReminder(ctx, t);
                }
            }
        });
    }
}
