package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.Customer;

import java.util.List;

public interface CustomerDAO {

    public Customer addCustomer(String jwt, Customer customer);

    public Customer getCustomerByUsername(String jwt,String username);

    public Customer getCustomerById(String jwt, String id);

    public List<Customer> getCustomers(String jwt);

 }
