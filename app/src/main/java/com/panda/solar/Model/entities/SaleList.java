package com.panda.solar.Model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaleList {

    @Expose
    @SerializedName("content")
    private List<Sale> saleList;

    public SaleList(List<Sale> saleList) {
        this.saleList = saleList;
    }

    public List<Sale> getSaleList() {
        return saleList;
    }
}
