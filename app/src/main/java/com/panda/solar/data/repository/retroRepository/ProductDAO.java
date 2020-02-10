package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.Product;

import java.util.List;

public interface ProductDAO {

    public MutableLiveData<Product> getProductById(String id);

    public MutableLiveData<List<Product>> getAllProducts();

    public MutableLiveData<Product> addProduct(Product product);

}
