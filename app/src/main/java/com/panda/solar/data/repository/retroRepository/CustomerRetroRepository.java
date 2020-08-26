package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.Model.entities.Village;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.utils.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;

public class CustomerRetroRepository implements CustomerDAO {

    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    NetworkResponse netResponse = new NetworkResponse();

    @Override
    public MutableLiveData<List<Customer>> getCustomers(final ResponseCallBack callBack, int page, int size, String sortby, String sortorder){

        Call<List<Customer>> call = pandaCoreAPI.getAllCustomers(page, size, sortby, sortorder);
        final MutableLiveData<List<Customer>> customers = new MutableLiveData<>();

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    Log.e("REQUEST NOT SUCCESSFULL","customer not fetched");
                    return;
                }
                callBack.onSuccess();
                Log.e("YES CONNECTION","successful");
                customers.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                callBack.onFailure();
                Log.e("NO CONNECTION",t.getMessage());
                return;
            }
        });
        return customers;
    }

    @Override
    public MutableLiveData<List<Village>> getAllVillages(final ResponseCallBack callBack) {

        final MutableLiveData<List<Village>> dataResult = new MutableLiveData<>();
        Call<List<Village>> call = pandaCoreAPI.getAllVillages();

        call.enqueue(new Callback<List<Village>>() {
            @Override
            public void onResponse(Call<List<Village>> call, Response<List<Village>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse networkResponse = new NetworkResponse();
                    callBack.onError(networkResponse);
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Village>> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

    @Override
    public MutableLiveData<CustomerMeta> addCustomer(final ResponseCallBack callBack, CustomerModel customer) {

        final MutableLiveData<CustomerMeta> resultData = new MutableLiveData<>();
        Call<CustomerMeta> call = pandaCoreAPI.addCustomer(customer);

        call.enqueue(new Callback<CustomerMeta>() {
            @Override
            public void onResponse(Call<CustomerMeta> call, Response<CustomerMeta> response) {
                if(!response.isSuccessful()){
                    try {
                        netResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                resultData.postValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<CustomerMeta> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return resultData;
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
