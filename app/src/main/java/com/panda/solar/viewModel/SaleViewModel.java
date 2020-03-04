package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
import java.util.List;
import retrofit2.Response;

public class SaleViewModel extends ViewModel {

    private static Response saleResponse;
    SaleDAO saleDAO = PandaDAOFactory.getSaleDAO();

    public LiveData<Sale> makeDirectPayGoSale(DirectSaleModel directSaleModel){
        saleResponse = saleDAO.getResponse();
        return saleDAO.makeDirectSale(directSaleModel);
    }

    public LiveData<LeaseSale> makeLeaseSale(LeaseSaleModel leaseSaleModel){
        saleResponse = saleDAO.getResponse();
        return saleDAO.makeLeaseSale(leaseSaleModel);
    }

    public LiveData<List<Sale>> getAllSales(int page, int size, String sortby, String sortorder){
        saleResponse = saleDAO.getResponse();
        return saleDAO.getAllSales(page, size, sortby, sortorder);
    }

    public LiveData<List<Sale>> getAgentSales(String id, int page, int size, String sortby, String sortorder){
        saleResponse = saleDAO.getResponse();
        return saleDAO.getSalesByAgent(id, page, size, sortby, sortorder);
    }

    public static Response getSaleResponse(){
        return saleResponse;
    }
}