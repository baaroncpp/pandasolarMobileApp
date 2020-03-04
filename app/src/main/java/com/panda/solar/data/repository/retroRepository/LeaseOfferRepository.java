package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LeaseOfferRepository implements LeaseOfferDAO {

    private static LeaseOfferRepository instance;
    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    private static String errorMessage;
    private static Response leaseResponse;

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
                leaseResponse = response;
                if(!response.isSuccessful()){
                    errorMessage = "BAD REQUEST";
                }
                leaseOffers.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<LeaseOffer>> call, Throwable t) {
                Log.e("leaseOffer","no connection");
                leaseOffers.postValue(getLeaseOffers());
                errorMessage = t.getMessage();
            }
        });
        return leaseOffers;
    }

    @Override
    public Response getLeaseResponse() {
        return leaseResponse;
    }

    public List<LeaseOffer> getLeaseOffers(){

        List<LeaseOffer> leaseOffers = new ArrayList<>();

        for(int i = 0; i < 5; i++) {
            Product product = new Product();
            product.setName("BoomBox 16 WATTS");
            product.setUnitcostselling(420500);

            LeaseOffer leaseOffer = new LeaseOffer();
            leaseOffer.setCode("PC456");
            leaseOffer.setIntialdeposit(121500);
            leaseOffer.setName("PandaSolar BoomBox Lease");
            leaseOffer.setProduct(product);
            leaseOffer.setRecurrentpaymentamount(50000);

            leaseOffers.add(leaseOffer);
        }

        return leaseOffers;
    }
}
