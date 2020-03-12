package com.panda.solar.utils;

import android.arch.lifecycle.LiveData;

public interface ResponseCallBack {

    public void onSuccess();

    public void onFailure();

    public void onError();

}
