package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository implements ProductDAO{

    private static ProductRepository instance;
    private static String errorMessage = "";
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();

    public static ProductRepository getInstance(){
        if(instance == null){
            instance = new ProductRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<Product> getProductById(String id) {
        return null;
    }

    @Override
    public MutableLiveData<List<Product>> getAllProducts() {

        final MutableLiveData<List<Product>> data = new MutableLiveData<>();
        Call<List<Product>> call = pandaCoreAPI.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.e("REQUEST NOT SUCCESSFULL","product not fetched");
                    return;
                }
                data.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                errorMessage = t.getMessage();
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<Product> addProduct(Product product) {
        return null;
    }
}
