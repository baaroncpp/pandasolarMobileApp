package com.panda.solar.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class AppNotificationsService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public AppNotificationsService() {
        super("AppNotificationsService");
    }

    @Override//runs on a background thread and is executed when the service is called
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        BackGroundTasks.executeTask(this, action);
    }
}
