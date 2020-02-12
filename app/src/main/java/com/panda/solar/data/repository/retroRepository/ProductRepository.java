package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;

import java.util.ArrayList;
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

        Log.e("repo", "accessed");

        final MutableLiveData<List<Product>> data = new MutableLiveData<>();
        Call<List<Product>> call = pandaCoreAPI.getAllProducts();

        call.enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if(!response.isSuccessful()){
                    Log.e("REQUEST NOT SUCCESSFULL","product not fetched");
                    return;
                }
                //data.postValue(response.body());
                data.postValue(setProducts());
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                Log.e("Connection failed", t.getMessage());
                errorMessage = t.getMessage();
                data.setValue(setProducts());
            }
        });
        return data;
    }

    @Override
    public MutableLiveData<Product> addProduct(Product product) {
        return null;
    }


    public List<Product> setProducts(){
        List<Product> products = new ArrayList<>();

        for(int i = 0; i < 40 ; i++){
            Product product = new Product();
            product.setName("Boom box Panda Solar");
            product.setUnitcostselling(400000);

            products.add(product);
        }

        return products;
    }
}
