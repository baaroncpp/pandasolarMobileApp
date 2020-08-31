package com.panda.solar.services;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;

public class FileUploadService extends JobIntentService {
    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String action = intent.getAction();
    }


}
