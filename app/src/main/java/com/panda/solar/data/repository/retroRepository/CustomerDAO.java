package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.Customer;

import java.util.List;

public interface CustomerDAO {

    public Customer addCustomer(Customer customer);

    public Customer getCustomerByUsername(String username);

    public Customer getCustomerById(String id);

    public MutableLiveData<List<Customer>> getCustomers(int page, int size, String sortby, String sortorder);

 }
