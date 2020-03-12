package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.CustomerDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public class CustomerViewModel extends ViewModel {

    private CustomerDAO customerDAO = PandaDAOFactory.getCustomerDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();

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
            public void onError() {
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, page, size, sortby, sortorder);
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

}