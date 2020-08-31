package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.DeviceToken;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;
import java.util.Map;

public interface SaleDAO {

    MutableLiveData<LeaseSale> makeLeaseSale(ResponseCallBack callBack, LeaseSaleModel leaseSaleModel);

    MutableLiveData<Sale> makeDirectSale(ResponseCallBack callBack, DirectSaleModel directSaleModel);

    MutableLiveData<List<SaleModel>> getSalesByAgent(ResponseCallBack callBack, String id, int page, int size, String sortby, String sortorder);

    MutableLiveData<List<SaleModel>> getAllSales(ResponseCallBack callBack, int page, int size, String sortby, String sortorder);

    MutableLiveData<Sale> approveSale(ResponseCallBack callBack, String id, String approveStatus, String reviewDescription);

    MutableLiveData<Map<String ,Integer>> agentSalesSum(ResponseCallBack callBack, String agentId);

    MutableLiveData<Map<String ,Integer>> customerSalesSum(ResponseCallBack callBack, String customerId);

    MutableLiveData<Sale> makeNonPayGoSale(ResponseCallBack callBack, DirectSaleModel directSaleModel);

    MutableLiveData<DeviceToken> resendDeviceToken(ResponseCallBack callBack, String leasePaymentId);

}
