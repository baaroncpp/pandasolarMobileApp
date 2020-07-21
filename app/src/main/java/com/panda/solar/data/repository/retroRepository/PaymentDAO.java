package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.PaymentStatisticModel;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public interface PaymentDAO {

    MutableLiveData<List<LeasePayment>> getAllPayments(ResponseCallBack responseWrapper, int page, int size, String direction);

    MutableLiveData<List<LeasePayment>> getAllPaymentsByAgentSales(ResponseCallBack responseWrapper, int page, int size, String direction);

    MutableLiveData<PaymentStatisticModel> getPaymentStatistic(ResponseCallBack callBack);

}
