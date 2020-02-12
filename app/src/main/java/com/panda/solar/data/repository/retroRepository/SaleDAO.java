package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;

import java.util.List;

public interface SaleDAO {

    public MutableLiveData<LeaseSale> makeLeaseSale(LeaseSaleModel leaseSaleModel);

    public MutableLiveData<Sale> makeDirectSale(DirectSaleModel directSaleModel);

    public MutableLiveData<List<Sale>> getSalesByAgent(String id, int page, int size, String sortby, String sortorder);

}
