package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

import retrofit2.Response;

public interface LeaseOfferDAO {

    MutableLiveData<List<LeaseOffer>> getAllLeaseOffers(ResponseCallBack callBack);

}
