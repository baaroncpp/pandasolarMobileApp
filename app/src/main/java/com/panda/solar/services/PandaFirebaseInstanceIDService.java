package com.panda.solar.services;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.panda.solar.Model.entities.AndroidTokens;
import com.panda.solar.viewModel.UserViewModel;

public class PandaFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "Registration";
    private UserViewModel userViewModel;

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: "+ refreshedToken);
        sendRegistrationToken(refreshedToken);
    }

    public void sendRegistrationToken(String token){

        userViewModel = ViewModelProviders.of((FragmentActivity) getApplicationContext()).get(UserViewModel.class);

        LiveData<AndroidTokens> androidTokensLiveData = userViewModel.registerDevice(token);
        androidTokensLiveData.observe((LifecycleOwner) this, new Observer<AndroidTokens>() {
            @Override
            public void onChanged(@Nullable AndroidTokens androidTokens) {
                Log.d(TAG, "Token saved: "+ androidTokens.getToken());
            }
        });
    }
}
