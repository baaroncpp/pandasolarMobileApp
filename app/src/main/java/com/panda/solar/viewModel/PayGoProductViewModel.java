package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.PayGoProductDAO;

import retrofit2.Response;

public class PayGoProductViewModel extends ViewModel {

    PayGoProductDAO payGoProductDAO = PandaDAOFactory.getPayGoProductDAO();
    private Response payGoProductResponse;

    public LiveData<PayGoProduct> getPayGoProduct(String serial){
        payGoProductResponse = payGoProductDAO.getPayGoProductResponse();
        return payGoProductDAO.getPayGoProduct(serial);
    }

    public LiveData<Boolean> payGoProductIsAvailable(String serial){
        payGoProductResponse = payGoProductDAO.getPayGoProductResponse();
        return payGoProductDAO.payGoProductIsAvailable(serial);
    }

    public LiveData<PayGoProduct> stockPayGoProduct(PayGoProductModel payGoProductModel){
        payGoProductResponse = payGoProductDAO.getPayGoProductResponse();
        return payGoProductDAO.stockPayGoProduct(payGoProductModel);
    }

    public Response getPayGoProductResponse(){
        return payGoProductResponse;
    }
}
