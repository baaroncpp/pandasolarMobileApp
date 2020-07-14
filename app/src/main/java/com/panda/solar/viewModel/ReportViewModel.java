package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.SaleStatisticsModel;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.ReportsDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

public class ReportViewModel extends ViewModel {

    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();
    private ReportsDAO reportsDAO = PandaDAOFactory.getReportDAO();

    public LiveData<SaleStatisticsModel> getSaleStatistic(String leaseSaleId){
        return reportsDAO.getSaleStatistic(new ResponseCallBack() {
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
        }, leaseSaleId);
    }
}
