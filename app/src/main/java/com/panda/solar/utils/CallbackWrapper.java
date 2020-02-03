package com.panda.solar.utils;

import com.google.gson.Gson;
import com.panda.solar.data.network.NetworkCallback;
import com.panda.solar.data.network.NetworkResponse;

import java.io.IOException;

import io.reactivex.observers.DisposableObserver;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Created by macosx on 02/05/2019.
 */

public abstract class CallbackWrapper<V> extends DisposableObserver<V> {


    private Gson gson;
    private NetworkCallback networkCallback;


    public CallbackWrapper(NetworkCallback networkCallback) {

        gson = new Gson();
        this.networkCallback = networkCallback;

    }

    protected abstract void onSuccess(V v);

    @Override
    public void onNext(V v) {
        onSuccess(v);
    }

    @Override
    public void onError(Throwable e) {
        handlerError(e);
    }

    @Override
    public void onComplete() {

    }

    private void handlerError(Throwable e){

        NetworkResponse networkResponse = new NetworkResponse();
        if(e instanceof HttpException){
            HttpException exception = (HttpException)e;
            Response<?> response = exception.response();
            try {
                networkResponse.setBody(response.errorBody().string());
                networkResponse.setCode(response.code());
            } catch (IOException e1) {
                networkResponse.setBody("An error occurred");
                networkResponse.setCode(400);
            }
        }else{
            networkResponse.setBody("An error occurred");
            networkResponse.setCode(400);
        }

        networkCallback.onCallback(networkResponse);
    }
}
