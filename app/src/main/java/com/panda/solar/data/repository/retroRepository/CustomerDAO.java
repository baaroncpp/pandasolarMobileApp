package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.Customer;

import java.util.List;

public interface CustomerDAO {

    public Customer addCustomer(Customer customer);

    public Customer getCustomerByUsername(String username);

    public Customer getCustomerById(String id);

    public List<Customer> getCustomers();

 }
