package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;

import java.util.List;

public class SaleRepository implements SaleDAO{

    private static SaleRepository instance;

    public static SaleRepository getInstance(){
        if(instance == null){
            instance = new SaleRepository();
        }
        return instance;
    }


    @Override
    public MutableLiveData<LeaseSale> makeLeaseSale(LeaseSaleModel leaseSaleModel) {
        return null;
    }

    @Override
    public MutableLiveData<Sale> makeDirectSale(DirectSaleModel directSaleModel) {
        return null;
    }

    @Override
    public MutableLiveData<List<Sale>> getSalesByAgent(String id, int page, int size, String sortby, String sortorder) {
        return null;
    }
}
