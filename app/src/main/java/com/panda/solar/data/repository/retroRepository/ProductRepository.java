package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository implements ProductDAO{

    private static ProductRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    NetworkResponse netResponse = new NetworkResponse();

    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<Product> getProductById(String id) {

        final MutableLiveData<Product> data = new MutableLiveData<>();

        Call<Product> call = pandaCoreAPI.getProductById(id);
        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(!response.isSuccessful()){
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                return;
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<List<Product>> getAllProducts(final ResponseCallBack callBack) {

        Log.e("repo", "accessed");

        final MutableLiveData<List<Product>> data = new MutableLiveData<>();
        Call<List<Product>> call = pandaCoreAPI.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.e("REQUEST NOT SUCCESSFULL","product not fetched");
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Connection failed", t.getMessage());
                callBack.onFailure();
                return;
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<Product> addProduct(Product product) {
        return null;
    }

    @Override
    public MutableLiveData<Product> getProductBySerialNumber(String serialNumber) {

        final MutableLiveData<Product> data = new MutableLiveData<>();

        Call<Product> call = pandaCoreAPI.getProductBySerialNumnber(serialNumber);

        call.enqueue(new Callback<Product>() {
            @Override
            public void onResponse(Call<Product> call, Response<Product> response) {
                if(!response.isSuccessful()){
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Product> call, Throwable t) {
                return;
            }
        });

        return data;
    }


}
