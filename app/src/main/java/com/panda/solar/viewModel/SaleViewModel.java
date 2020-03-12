package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public class SaleViewModel extends ViewModel {

    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    SaleDAO saleDAO = PandaDAOFactory.getSaleDAO();

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
            public void onError() {
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
            public void onError() {
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
            public void onError() {
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
            public void onError() {
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
            public void onError() {
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, saleId, approveStatus, reviewDescription);
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

}