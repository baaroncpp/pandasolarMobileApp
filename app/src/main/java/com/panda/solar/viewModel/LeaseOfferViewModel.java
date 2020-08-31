package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.LeaseOfferDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;
import retrofit2.Response;

public class LeaseOfferViewModel extends ViewModel {

    private MutableLiveData<String> responseMessage = new MutableLiveData<>();

    private LeaseOfferDAO leaseOfferDAO = PandaDAOFactory.getLeaseOfferDAO();

    public LiveData<List<LeaseOffer>> getAllLeaseOffers(){
        return leaseOfferDAO.getAllLeaseOffers(new ResponseCallBack() {
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

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }
}
