package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaseOfferRepository implements LeaseOfferDAO {

    private static LeaseOfferRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    private static String errorMessage;

    public static LeaseOfferRepository getInstance(){
        if(instance == null){
            instance = new LeaseOfferRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<List<LeaseOffer>> getAllLeaseOffers() {

        final MutableLiveData<List<LeaseOffer>> leaseOffers = new MutableLiveData<>();

        Call<List<LeaseOffer>> call =pandaCoreAPI.getLeaseOffers();

        call.enqueue(new Callback<List<LeaseOffer>>() {
            @Override
            public void onResponse(Call<List<LeaseOffer>> call, Response<List<LeaseOffer>> response) {
                if(!response.isSuccessful()){
                    errorMessage = "BAD REQUEST";
                }
                leaseOffers.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LeaseOffer>> call, Throwable t) {
                errorMessage = t.getMessage();
            }
        });

        return leaseOffers;
    }
}
