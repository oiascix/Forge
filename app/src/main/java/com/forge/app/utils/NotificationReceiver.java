package com.forge.app.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import com.forge.app.R;
import com.forge.app.activities.MainActivity;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context ctx, Intent intent) {
        int taskId    = intent.getIntExtra(NotificationHelper.EXTRA_TASK_ID, -1);
        String title  = intent.getStringExtra(NotificationHelper.EXTRA_TASK_TITLE);
        if (taskId == -1) return;

        Intent tap = new Intent(ctx, MainActivity.class);
        tap.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pi = PendingIntent.getActivity(ctx, taskId, tap,
                PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, NotificationHelper.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_forge)
                .setContentTitle("Time to forge 🔥")
                .setContentText(title != null ? title : "Complete your daily habit")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setContentIntent(pi);

        NotificationManager nm = ctx.getSystemService(NotificationManager.class);
        nm.notify(taskId, builder.build());
    }
}
