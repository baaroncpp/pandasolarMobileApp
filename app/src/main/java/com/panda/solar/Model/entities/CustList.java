package com.panda.solar.Model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class CustList {

    @Expose
    @SerializedName("content")
    private List<Customer> customers;

    public CustList(List<Customer> customers) {
        this.customers = customers;
    }

    public List<Customer> getCustomers() {
        return customers;
    }
}
