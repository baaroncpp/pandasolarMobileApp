package com.panda.solar.services.loaders;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.User;
import java.util.ArrayList;
import java.util.List;

public class CustomerLoader extends AsyncTaskLoader<List<Customer>> {

    public CustomerLoader(@NonNull Context context) {
        super(context);
    }

    @Nullable
    @Override
    public List<Customer> loadInBackground() {

        List<Customer> customers = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            User user = new User();
            user.setFirstname("bukenya");
            user.setLastname("aaron");
            user.setPrimaryphone("0770 567 098");

            Customer cu = new Customer();
            cu.setAddress("Ntinda");
            cu.setUser(user);
            customers.add(cu);
        }
        return customers;
    }

    @Override
    public void deliverResult(List<Customer> customers){
        super.deliverResult(customers);
    }

    @Override
    protected void onStartLoading(){
        forceLoad();
    }

}
