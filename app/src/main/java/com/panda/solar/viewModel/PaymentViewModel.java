package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.PaymentDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public class PaymentViewModel extends ViewModel {

    private PaymentDAO paymentDAO = PandaDAOFactory.getPaymentDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

    public LiveData<List<LeasePayment>> getAllLeasePayment(int page, int size, String direction){
        return paymentDAO.getAllPayments(new ResponseCallBack() {
            @Override
            public void onSuccess() {responseMessage.postValue(Constants.SUCCESS_RESPONSE);}

            @Override
            public void onFailure() {responseMessage.postValue(Constants.FAILURE_RESPONSE); }

            @Override
            public void onError() {responseMessage.postValue(Constants.ERROR_RESPONSE);}
        }, page, size, direction);
    }

    public LiveData<List<LeasePayment>> getAllAgentLeasePayment(String agentId, int page, int size, String direction){
        return paymentDAO.getAllPaymentsByAgentSales(new ResponseCallBack() {
            @Override
            public void onSuccess() {responseMessage.postValue(Constants.SUCCESS_RESPONSE);}

            @Override
            public void onFailure() {responseMessage.postValue(Constants.FAILURE_RESPONSE);}

            @Override
            public void onError() {responseMessage.postValue(Constants.ERROR_RESPONSE);}
        }, agentId, page, size, direction);
    }

}
