package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.Model.entities.StockProduct;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.PayGoProductDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;


public class PayGoProductViewModel extends ViewModel {

    PayGoProductDAO payGoProductDAO = PandaDAOFactory.getPayGoProductDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponseMutableLiveData = new MutableLiveData<>();

    public LiveData<PayGoProduct> getPayGoProduct(String serial){
        return payGoProductDAO.getPayGoProduct(new ResponseCallBack() {
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
                networkResponseMutableLiveData.postValue(response);
            }
        }, serial);
    }

    public LiveData<Boolean> payGoProductIsAvailable(String serial){
        return payGoProductDAO.payGoProductIsAvailable(new ResponseCallBack() {
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
                networkResponseMutableLiveData.postValue(response);
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, serial);
    }

    public LiveData<PayGoProduct> stockPayGoProduct(PayGoProductModel payGoProductModel){
        return payGoProductDAO.stockPayGoProduct(new ResponseCallBack() {
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
                networkResponseMutableLiveData.postValue(response);
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, payGoProductModel);
    }

    public LiveData<List<StockProduct>> getStockValues(){
        return payGoProductDAO.getStockValues(new ResponseCallBack() {
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
                networkResponseMutableLiveData.postValue(response);
            }
        });
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

    public LiveData<NetworkResponse> getNetworkResponse(){return networkResponseMutableLiveData;}
}
