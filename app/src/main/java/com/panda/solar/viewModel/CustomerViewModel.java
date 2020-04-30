package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.Model.entities.Village;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.CustomerDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public class CustomerViewModel extends ViewModel {

    private CustomerDAO customerDAO = PandaDAOFactory.getCustomerDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();

    public LiveData<List<Customer>> getCustomers(int page, int size, String sortby, String sortorder){
        return customerDAO.getCustomers(new ResponseCallBack() {
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
        }, page, size, sortby, sortorder);
    }

    public LiveData<CustomerMeta> addCustomerMeta(CustomerModel customerModel){
        return customerDAO.addCustomer(new ResponseCallBack() {
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
                networkResponse.postValue(response);
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, customerModel);
    }

    public LiveData<List<Village>> getVillages(){
        return customerDAO.getAllVillages(new ResponseCallBack() {
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
                networkResponse.postValue(response);
            }
        });
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

    public LiveData<NetworkResponse> getNetworkResponse(){
        return networkResponse;
    }

}