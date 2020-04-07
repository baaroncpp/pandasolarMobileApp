package com.panda.solar.utils;


import com.panda.solar.data.network.NetworkResponse;

public interface ResponseCallBack {

    void onSuccess();

    void onFailure();

    void onError(NetworkResponse response);

}
