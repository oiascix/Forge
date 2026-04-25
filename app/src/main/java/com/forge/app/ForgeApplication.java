package com.forge.app;

import android.app.Application;
import com.forge.app.utils.NotificationHelper;
import com.forge.app.workers.MidnightResetWorker;

public class ForgeApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createChannel(this);
        MidnightResetWorker.schedule(this);
    }
}
