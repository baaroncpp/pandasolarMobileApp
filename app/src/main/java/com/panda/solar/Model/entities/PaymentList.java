package com.panda.solar.Model.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PaymentList {

    @Expose
    @SerializedName("content")
    private List<LeasePayment> paymentList;

    public PaymentList(List<LeasePayment> paymentList) {
        this.paymentList = paymentList;
    }

    public List<LeasePayment> getPaymentList() {
        return paymentList;
    }
}
