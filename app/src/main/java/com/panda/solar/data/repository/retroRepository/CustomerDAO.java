package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public interface CustomerDAO {

    public MutableLiveData<CustomerMeta> addCustomer(ResponseCallBack callBack, CustomerModel customer);

    public Customer getCustomerByUsername(String username);

    public Customer getCustomerById(String id);

    public MutableLiveData<List<Customer>> getCustomers(ResponseCallBack callBack, int page, int size, String sortby, String sortorder);

 }
