package com.panda.solar.Model.entities;

import java.util.Date;

public class LeaseOffer {

    private int id;
    private Product product;
    private String code;
    private short leaseperiod;
    private short percentlease;
    private float recurrentpaymentamount;
    private boolean isactive;
    private float intialdeposit;
    private String name;
    private String description;
    private Date createdon;

    public LeaseOffer(){super();}

    public int getId() {
        return id;
    }

    public Product getProduct() {
        return product;
    }

    public String getCode() {
        return code;
    }

    public short getLeaseperiod() {
        return leaseperiod;
    }

    public short getPercentlease() {
        return percentlease;
    }

    public float getRecurrentpaymentamount() {
        return recurrentpaymentamount;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public float getIntialdeposit() {
        return intialdeposit;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedon() {
        return createdon;
    }
}
