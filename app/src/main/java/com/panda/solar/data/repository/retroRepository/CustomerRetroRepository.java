package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.User;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import java.util.ArrayList;
import java.util.List;

public class CustomerRetroRepository implements CustomerDAO {

    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    MutableLiveData<List<Customer>> liveCustomers = new MutableLiveData<>();

    public List<Customer> getCustomers(){

        Call<List<Customer>> call = pandaCoreAPI.getCustomers();
        final List<Customer> customers = new ArrayList<>();

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(!response.isSuccessful()){
                    Log.e("REQUEST NOT SUCCESSFULL","customer not fetched");
                    return;
                }

                //customers.addAll(response.body());
                customers.addAll(cust());
                liveCustomers.setValue(customers);
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("NO CONNECTION",t.getMessage());
                customers.addAll(cust());
                return;
            }
        });

        return customers;
    }

    public List<Customer> cust(){
        List<Customer> c = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            User user = new User();
            Customer cu = new Customer();

            user.setEmail("baaronlubega1@gmail.com");
            user.setFirstname("Lady");
            user.setLastname("Leah");
            user.setPrimaryphone("256 773 039 553");

            cu.setAddress("Ndinda");
            cu.setUser(user);

            c.add(cu);
        }
        return c;
    }


    @Override
    public Customer addCustomer(String jwt, Customer customer) {
        return null;
    }

    @Override
    public Customer getCustomerByUsername(String jwt, String username) {
        return null;
    }

    @Override
    public Customer getCustomerById(String jwt, String id) {
        return null;
    }

    @Override
    public List<Customer> getCustomers(String jwt) {

        Call<List<Customer>> call = pandaCoreAPI.getCustomers();
        final List<Customer> customers = new ArrayList<>();

        call.enqueue(new Callback<List<Customer>>() {
            @Override
            public void onResponse(Call<List<Customer>> call, Response<List<Customer>> response) {
                if(!response.isSuccessful()){
                    Log.e("REQUEST NOT SUCCESSFULL","customer not fetched");
                    return;
                }

                //customers.addAll(response.body());
                //customers.addAll(cust());
            }

            @Override
            public void onFailure(Call<List<Customer>> call, Throwable t) {
                Log.e("NO CONNECTION",t.getMessage());
                customers.addAll(cust());
                return;
            }
        });

        return customers;
    }
}
