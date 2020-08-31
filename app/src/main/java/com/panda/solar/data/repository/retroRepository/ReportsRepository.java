package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.SaleStatisticsModel;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportsRepository implements ReportsDAO {

    private static ReportsRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();

    public static ReportsRepository getInstance(){
        if(instance == null){
            instance = new ReportsRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<SaleStatisticsModel> getSaleStatistic(final ResponseCallBack callBack, String id) {

        final MutableLiveData<SaleStatisticsModel> dataResult = new MutableLiveData<>();
        Call<SaleStatisticsModel> call = pandaCoreAPI.getSaleStatistic(id);

        call.enqueue(new Callback<SaleStatisticsModel>() {
            @Override
            public void onResponse(Call<SaleStatisticsModel> call, Response<SaleStatisticsModel> response) {
                if(!response.isSuccessful()){
                    NetworkResponse networkResponse = new NetworkResponse();
                    networkResponse.setCode(response.code());
                    try {
                        networkResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.e("err","stai");
                    callBack.onError(networkResponse);
                    return;
                }
                Log.e("suc","stai");
                dataResult.postValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<SaleStatisticsModel> call, Throwable t) {
                Log.e("fail","stai");
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }
}
