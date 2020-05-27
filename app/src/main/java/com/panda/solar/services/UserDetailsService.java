package com.panda.solar.services;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;

public class UserDetailsService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     */
    public UserDetailsService() {
        super("UserDetailsService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String action = intent.getAction();
        BackGroundTasks.executeTask(this, action);
    }
}
