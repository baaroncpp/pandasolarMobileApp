package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.CustList;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.List;

public class CustomerRetroRepository implements CustomerDAO {

    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    MutableLiveData<List<Customer>> liveCustomers = new MutableLiveData<>();

    @Override
    public MutableLiveData<List<Customer>> getCustomers(int page, int size, String sortby, String sortorder){

        Call<CustList> call = pandaCoreAPI.getAllCustomers(page, size, sortby, sortorder);
        final MutableLiveData<List<Customer>> customers = new MutableLiveData<>();

        call.enqueue(new Callback<CustList>() {
            @Override
            public void onResponse(Call<CustList> call, Response<CustList> response) {
                if(!response.isSuccessful()){
                    Log.e("REQUEST NOT SUCCESSFULL","customer not fetched");
                    return;
                }

                Log.e("YES CONNECTION","successful");
                customers.postValue(response.body().getCustomers());
            }

            @Override
            public void onFailure(Call<CustList> call, Throwable t) {
                Log.e("NO CONNECTION",t.getMessage());
                return;
            }
        });

        return customers;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        return null;
    }

    @Override
    public Customer getCustomerById(String id) {
        return null;
    }

}
