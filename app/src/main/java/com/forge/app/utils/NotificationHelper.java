package com.forge.app.utils;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import com.forge.app.models.Task;
import java.util.Calendar;

public class NotificationHelper {
    public static final String CHANNEL_ID   = "forge_reminders";
    public static final String CHANNEL_NAME = "Daily Reminders";
    public static final String EXTRA_TASK_ID    = "task_id";
    public static final String EXTRA_TASK_TITLE = "task_title";

    public static void createChannel(Context ctx) {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Forge daily habit reminders");
        ctx.getSystemService(NotificationManager.class).createNotificationChannel(channel);
    }

    public static void scheduleReminder(Context ctx, Task task) {
        if (!task.reminderEnabled) {
            cancelReminder(ctx, task.id);
            return;
        }
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        PendingIntent pi = buildPendingIntent(ctx, task);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, task.reminderHour);
        cal.set(Calendar.MINUTE,      task.reminderMinute);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);

        // If time already passed today, schedule for tomorrow
        if (cal.getTimeInMillis() <= System.currentTimeMillis()) {
            cal.add(Calendar.DAY_OF_YEAR, 1);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !am.canScheduleExactAlarms()) {
            am.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pi);
        } else {
            am.setRepeating(AlarmManager.RTC_WAKEUP,
                    cal.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pi);
        }
    }

    public static void cancelReminder(Context ctx, int taskId) {
        AlarmManager am = (AlarmManager) ctx.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(ctx, NotificationReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(ctx, taskId, intent,
                PendingIntent.FLAG_NO_CREATE | PendingIntent.FLAG_IMMUTABLE);
        if (pi != null) {
            am.cancel(pi);
            pi.cancel();
        }
    }

    private static PendingIntent buildPendingIntent(Context ctx, Task task) {
        Intent intent = new Intent(ctx, NotificationReceiver.class);
        intent.putExtra(EXTRA_TASK_ID,    task.id);
        intent.putExtra(EXTRA_TASK_TITLE, task.title);
        return PendingIntent.getBroadcast(ctx, task.id, intent,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
    }
}
