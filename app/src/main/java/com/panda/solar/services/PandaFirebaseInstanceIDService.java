package com.panda.solar.services;

import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.panda.solar.utils.AppContext;
import com.panda.solar.utils.Constants;

public class PandaFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "FCM Token Registration";

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: "+ refreshedToken);
        sendRegistrationToken(refreshedToken);
    }

    public void sendRegistrationToken(String token){

        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.FCM_DEVICE_TOKEN, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Log.d(TAG, "Token saved: "+ token);
        editor.putString(Constants.FCM_DEVICE_TOKEN, token);
        editor.apply();
    }
}
