package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.Model.entities.StockProduct;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public interface PayGoProductDAO {

    public MutableLiveData<PayGoProduct> getPayGoProduct(ResponseCallBack callBack, String serial);

    public MutableLiveData<Boolean> payGoProductIsAvailable(ResponseCallBack callBack, String serial);

    public MutableLiveData<PayGoProduct> stockPayGoProduct(ResponseCallBack callBack, PayGoProductModel payGoProductModel);

    public MutableLiveData<List<StockProduct>> getStockValues(ResponseCallBack callBack);

}
