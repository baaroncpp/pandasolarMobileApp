package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.Model.entities.StockProduct;
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

public class PayGoProductRepository implements PayGoProductDAO {

    private static PayGoProductRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    NetworkResponse netResponse = new NetworkResponse();

    public static PayGoProductRepository getInstance(){
        if(instance == null){
            instance = new PayGoProductRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<PayGoProduct> getPayGoProduct(final ResponseCallBack callBack, String serial) {

        final MutableLiveData<PayGoProduct> data = new MutableLiveData<>();
        Call<PayGoProduct> call = pandaCoreAPI.getPayGoProduct(serial);

        call.enqueue(new Callback<PayGoProduct>() {
            @Override
            public void onResponse(Call<PayGoProduct> call, Response<PayGoProduct> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<PayGoProduct> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<Boolean> payGoProductIsAvailable(final ResponseCallBack callBack, String serial) {

        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        Call<Boolean> call = pandaCoreAPI.payGoProductIsAvailable(serial);

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                data.setValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<PayGoProduct> stockPayGoProduct(final ResponseCallBack callBack, PayGoProductModel payGoProductModel) {

        final MutableLiveData<PayGoProduct> data = new MutableLiveData<>();
        Call<PayGoProduct> call = pandaCoreAPI.stockPayGoProduct(payGoProductModel);

        call.enqueue(new Callback<PayGoProduct>() {
            @Override
            public void onResponse(Call<PayGoProduct> call, Response<PayGoProduct> response) {
                if(!response.isSuccessful()){
                    try {
                        netResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<PayGoProduct> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<List<StockProduct>> getStockValues(final ResponseCallBack callBack) {

        final MutableLiveData<List<StockProduct>> resultData = new MutableLiveData<>();
        Call<List<StockProduct>> call = pandaCoreAPI.getStockValues();

        call.enqueue(new Callback<List<StockProduct>>() {
            @Override
            public void onResponse(Call<List<StockProduct>> call, Response<List<StockProduct>> response) {

                if(!response.isSuccessful()){
                    NetworkResponse netResponse = new NetworkResponse();
                    netResponse.setCode(response.code());
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
                callBack.onSuccess();
                resultData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<StockProduct>> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return resultData;
    }

}
