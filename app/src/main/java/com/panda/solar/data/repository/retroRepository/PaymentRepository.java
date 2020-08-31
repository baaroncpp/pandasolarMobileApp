package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.PaymentList;
import com.panda.solar.Model.entities.PaymentStatisticModel;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaymentRepository implements PaymentDAO {

    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    private static PaymentRepository instance;
    NetworkResponse netResponse = new NetworkResponse();

    public static PaymentRepository getInstance(){
        if(instance == null){
            instance = new PaymentRepository();
        }
        return instance;
    }
    @Override
    public MutableLiveData<List<LeasePayment>> getAllPayments(final ResponseCallBack callBack, int page, int size, String direction) {

        final MutableLiveData<List<LeasePayment>> dataResult = new MutableLiveData<>();
        Call<List<LeasePayment>> call = pandaCoreAPI.getAllLeasePayments(page, size, direction);

        call.enqueue(new Callback<List<LeasePayment>>() {
            @Override
            public void onResponse(Call<List<LeasePayment>> call, Response<List<LeasePayment>> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    Log.e("error","wrong req");
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body());
                Log.e("success","good req");
            }

            @Override
            public void onFailure(Call<List<LeasePayment>> call, Throwable t) {
                callBack.onFailure();
                Log.e("failure",t.getMessage());
                return;
            }
        });
        return dataResult;
    }

    @Override
    public MutableLiveData<List<LeasePayment>> getAllPaymentsByAgentSales(final ResponseCallBack callBack, int page, int size, String direction) {

        final MutableLiveData<List<LeasePayment>> dataResult = new MutableLiveData<>();
        Call<List<LeasePayment>> call = pandaCoreAPI.getAllAgentLeasePayments( page, size, direction);

        call.enqueue(new Callback<List<LeasePayment>>() {
            @Override
            public void onResponse(Call<List<LeasePayment>> call, Response<List<LeasePayment>> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LeasePayment>> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

    @Override
    public MutableLiveData<PaymentStatisticModel> getPaymentStatistic(final ResponseCallBack callBack) {

        final MutableLiveData<PaymentStatisticModel> dataResult =  new MutableLiveData<>();
        Call<PaymentStatisticModel> call = pandaCoreAPI.getPaymentStatistic();

        call.enqueue(new Callback<PaymentStatisticModel>() {
            @Override
            public void onResponse(Call<PaymentStatisticModel> call, Response<PaymentStatisticModel> response) {
                if(!response.isSuccessful()){
                    try {
                        netResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callBack.onError(netResponse);
                    return;
                }
                dataResult.postValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<PaymentStatisticModel> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

}
