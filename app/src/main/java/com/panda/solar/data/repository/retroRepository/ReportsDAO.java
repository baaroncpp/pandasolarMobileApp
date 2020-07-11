package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.SaleStatisticsModel;
import com.panda.solar.utils.ResponseCallBack;

public interface ReportsDAO {

    SaleStatisticsModel getSaleStatistic(ResponseCallBack callBack, String id);
}
