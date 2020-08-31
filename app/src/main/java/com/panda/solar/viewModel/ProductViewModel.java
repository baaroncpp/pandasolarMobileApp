package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.ProductDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.ArrayList;
import java.util.List;

public class ProductViewModel extends ViewModel {

    private ProductDAO productDAO = PandaDAOFactory.getProductDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();

    public LiveData<List<Product>> getProducts(){
        Log.e("view model", "accessed");
        return productDAO.getAllProducts(new ResponseCallBack() {
            @Override
            public void onSuccess() {
                responseMessage.postValue(Constants.SUCCESS_RESPONSE);
            }

            @Override
            public void onFailure() {
                responseMessage.postValue(Constants.FAILURE_RESPONSE);
            }

            @Override
            public void onError(NetworkResponse response) {
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        });
    }

    public LiveData<Product> getProductBySerialNumber(String serialNumber){
        return productDAO.getProductBySerialNumber(serialNumber);
    }

    public LiveData<Product> getProductById(String id){
        return productDAO.getProductById(id);
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }
}
