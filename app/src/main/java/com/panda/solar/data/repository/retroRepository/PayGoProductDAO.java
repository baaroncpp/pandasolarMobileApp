package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;

import retrofit2.Response;

public interface PayGoProductDAO {

    public MutableLiveData<PayGoProduct> getPayGoProduct(String serial);

    public MutableLiveData<Boolean> payGoProductIsAvailable(String serial);

    public MutableLiveData<PayGoProduct> stockPayGoProduct(PayGoProductModel payGoProductModel);

    public Response getPayGoProductResponse();

}
