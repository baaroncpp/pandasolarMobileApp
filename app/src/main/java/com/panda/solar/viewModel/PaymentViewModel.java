package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.PaymentStatisticModel;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.PaymentDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public class PaymentViewModel extends ViewModel {

    private PaymentDAO paymentDAO = PandaDAOFactory.getPaymentDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();

    public LiveData<List<LeasePayment>> getAllLeasePayment(int page, int size, String direction){
        return paymentDAO.getAllPayments(new ResponseCallBack() {
            @Override
            public void onSuccess() {responseMessage.postValue(Constants.SUCCESS_RESPONSE);}

            @Override
            public void onFailure() {responseMessage.postValue(Constants.FAILURE_RESPONSE); }

            @Override
            public void onError(NetworkResponse response) {responseMessage.postValue(Constants.ERROR_RESPONSE);}
        }, page, size, direction);
    }

    public LiveData<List<LeasePayment>> getAllAgentLeasePayment(int page, int size, String direction){
        return paymentDAO.getAllPaymentsByAgentSales(new ResponseCallBack() {
            @Override
            public void onSuccess() {responseMessage.postValue(Constants.SUCCESS_RESPONSE);}

            @Override
            public void onFailure() {responseMessage.postValue(Constants.FAILURE_RESPONSE);}

            @Override
            public void onError(NetworkResponse response) {responseMessage.postValue(Constants.ERROR_RESPONSE);}
        }, page, size, direction);
    }

    public LiveData<PaymentStatisticModel> getPaymentStatistics(){
        return paymentDAO.getPaymentStatistic(new ResponseCallBack() {
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

    public LiveData<NetworkResponse> getNetworkResponse(){return networkResponse;}

}
