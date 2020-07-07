package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;
import java.util.Map;

public class SaleViewModel extends ViewModel {

    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();
    private SaleDAO saleDAO = PandaDAOFactory.getSaleDAO();

    public LiveData<Sale> makeDirectPayGoSale(DirectSaleModel directSaleModel){
        return saleDAO.makeDirectSale(new ResponseCallBack() {
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
        }, directSaleModel);
    }

    public LiveData<LeaseSale> makeLeaseSale(LeaseSaleModel leaseSaleModel){
        return saleDAO.makeLeaseSale(new ResponseCallBack() {
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
        }, leaseSaleModel);
    }

    public LiveData<List<SaleModel>> getAllSales(int page, int size, String sortby, String sortorder){
        return saleDAO.getAllSales(new ResponseCallBack() {
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
        }, page, size, sortby, sortorder);
    }

    public LiveData<List<SaleModel>> getAgentSales(String id, int page, int size, String sortby, String sortorder){
        return saleDAO.getSalesByAgent(new ResponseCallBack() {
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
        }, id, page, size, sortby, sortorder);
    }

    public LiveData<Sale> approveSale(String saleId, String approveStatus, String reviewDescription){
        return saleDAO.approveSale(new ResponseCallBack() {
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
        }, saleId, approveStatus, reviewDescription);
    }

    public LiveData<Map<String, Integer>> getCustomerSalesSum(String customerId){
        return saleDAO.customerSalesSum(new ResponseCallBack() {
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
        }, customerId);
    }

    public LiveData<Map<String, Integer>> getAgentSalesSum(String agentId){
        return saleDAO.agentSalesSum(new ResponseCallBack() {
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
        }, agentId);
    }

    public LiveData<Sale> makeNonPayGoSale(DirectSaleModel directSaleModel){
        return saleDAO.makeNonPayGoSale(new ResponseCallBack() {
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
        }, directSaleModel);
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

    public LiveData<NetworkResponse> getNetworkResponse(){return networkResponse;}

}