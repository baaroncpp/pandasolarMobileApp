package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

import retrofit2.Response;


public interface SaleDAO {

    public MutableLiveData<LeaseSale> makeLeaseSale(ResponseCallBack callBack, LeaseSaleModel leaseSaleModel);

    public MutableLiveData<Sale> makeDirectSale(ResponseCallBack callBack, DirectSaleModel directSaleModel);

    public MutableLiveData<List<SaleModel>> getSalesByAgent(ResponseCallBack callBack, String id, int page, int size, String sortby, String sortorder);

    public MutableLiveData<List<SaleModel>> getAllSales(ResponseCallBack callBack, int page, int size, String sortby, String sortorder);

    public MutableLiveData<Sale> approveSale(ResponseCallBack callBack, String id, String approveStatus, String reviewDescription);

}
