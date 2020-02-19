package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
import retrofit2.Response;

public class SaleViewModel extends ViewModel {

    private static Response saleResponse;
    SaleDAO saleDAO = PandaDAOFactory.getSaleDAO();

    public LiveData<Sale> makeDirectPayGoSale(DirectSaleModel directSaleModel){
        saleResponse = saleDAO.getResponse();
        return saleDAO.makeDirectSale(directSaleModel);
    }

    public static Response getSaleResponse(){
        return saleResponse;
    }


}
