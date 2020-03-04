package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PayGoProductRepository implements PayGoProductDAO {

    private static PayGoProductRepository instance;
    private static Response payGoProductResponse;
    private static String errorMessage = "";
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();

    public static PayGoProductRepository getInstance(){
        if(instance == null){
            instance = new PayGoProductRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<PayGoProduct> getPayGoProduct(String serial) {

        final MutableLiveData<PayGoProduct> data = new MutableLiveData<>();
        Call<PayGoProduct> call = pandaCoreAPI.getPayGoProduct(serial);

        call.enqueue(new Callback<PayGoProduct>() {
            @Override
            public void onResponse(Call<PayGoProduct> call, Response<PayGoProduct> response) {
                payGoProductResponse = response;
                if(!response.isSuccessful()){
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<PayGoProduct> call, Throwable t) {
                errorMessage = t.getMessage();
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<Boolean> payGoProductIsAvailable(String serial) {

        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        Call<Boolean> call = pandaCoreAPI.payGoProductIsAvailable(serial);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    Log.e("fail","connect");
                    return;
                }
                data.setValue(response.body());
                Log.e("success","connect");
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Log.e("No","connect");
                errorMessage = t.getMessage();
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<PayGoProduct> stockPayGoProduct(PayGoProductModel payGoProductModel) {

        final MutableLiveData<PayGoProduct> data = new MutableLiveData<>();
        Call<PayGoProduct> call = pandaCoreAPI.stockPayGoProduct(payGoProductModel);

        call.enqueue(new Callback<PayGoProduct>() {
            @Override
            public void onResponse(Call<PayGoProduct> call, Response<PayGoProduct> response) {
                payGoProductResponse = response;
                if(!response.isSuccessful()){
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<PayGoProduct> call, Throwable t) {
                errorMessage = t.getMessage();
            }
        });
        return data;
    }

    @Override
    public Response getPayGoProductResponse(){
        return payGoProductResponse;
    }
}
