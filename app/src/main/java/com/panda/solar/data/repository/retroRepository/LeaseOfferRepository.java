package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaseOfferRepository implements LeaseOfferDAO {

    private static LeaseOfferRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();

    public static LeaseOfferRepository getInstance(){
        if(instance == null){
            instance = new LeaseOfferRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<List<LeaseOffer>> getAllLeaseOffers(final ResponseCallBack callBack) {

        final MutableLiveData<List<LeaseOffer>> leaseOffers = new MutableLiveData<>();
        final NetworkResponse netResponse = new NetworkResponse();

        Call<List<LeaseOffer>> call =pandaCoreAPI.getLeaseOffers();

        call.enqueue(new Callback<List<LeaseOffer>>() {
            @Override
            public void onResponse(Call<List<LeaseOffer>> call, Response<List<LeaseOffer>> response) {

                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                leaseOffers.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LeaseOffer>> call, Throwable t) {
                Log.e("leaseOffer","no connection");
                callBack.onFailure();
                return;
            }
        });
        return leaseOffers;
    }

}
