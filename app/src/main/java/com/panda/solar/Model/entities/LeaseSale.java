package com.panda.solar.Model.entities;

public class LeaseSale {
    private Sale sale;
    private Lease lease;

    public LeaseSale(){super();}

    public Sale getSale() {
        return sale;
    }

    public Lease getLease() {
        return lease;
    }
}
