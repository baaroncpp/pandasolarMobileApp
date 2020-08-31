package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import com.panda.solar.Model.entities.SaleStatisticsModel;
import com.panda.solar.utils.ResponseCallBack;

public interface ReportsDAO {

    MutableLiveData<SaleStatisticsModel> getSaleStatistic(ResponseCallBack callBack, String id);
}
