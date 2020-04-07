package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.PaymentList;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

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
        Call<PaymentList> call = pandaCoreAPI.getAllLeasePayments(page, size, direction);

        call.enqueue(new Callback<PaymentList>() {
            @Override
            public void onResponse(Call<PaymentList> call, Response<PaymentList> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    Log.e("error","wrong req");
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body().getPaymentList());
                Log.e("success","good req");
            }

            @Override
            public void onFailure(Call<PaymentList> call, Throwable t) {
                callBack.onFailure();
                Log.e("failure",t.getMessage());
                return;
            }
        });
        return dataResult;
    }

    @Override
    public MutableLiveData<List<LeasePayment>> getAllPaymentsByAgentSales(final ResponseCallBack callBack, String agentId, int page, int size, String direction) {

        final MutableLiveData<List<LeasePayment>> dataResult = new MutableLiveData<>();
        Call<PaymentList> call = pandaCoreAPI.getAllAgentLeasePayments(agentId, page, size, direction);

        call.enqueue(new Callback<PaymentList>() {
            @Override
            public void onResponse(Call<PaymentList> call, Response<PaymentList> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body().getPaymentList());
            }

            @Override
            public void onFailure(Call<PaymentList> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

}
