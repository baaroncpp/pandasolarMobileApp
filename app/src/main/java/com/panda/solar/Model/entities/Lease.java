package com.panda.solar.Model.entities;

import java.util.Date;

public class Lease {

    private String id;
    private LeaseOffer leaseOffer;
    private String customerid;
    private float initialdeposit;
    private String couponcode;
    private Date dateinstalled;
    private Date createdon;
    private String saleagentid;
    private float dailypayment;
    private int totalleaseperiod;
    private Date expectedfinishdate;
    private float totalleasevalue;
    private boolean iscompleted;
    private boolean isactivated;
    private Date paymentcompletedon;

    public  Lease(){super();}

    public String getId() {
        return id;
    }

    public LeaseOffer getLeaseOffer() {
        return leaseOffer;
    }

    public String getCustomerid() {
        return customerid;
    }

    public float getInitialdeposit() {
        return initialdeposit;
    }

    public String getCouponcode() {
        return couponcode;
    }

    public Date getDateinstalled() {
        return dateinstalled;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public String getSaleagentid() {
        return saleagentid;
    }

    public float getDailypayment() {
        return dailypayment;
    }

    public int getTotalleaseperiod() {
        return totalleaseperiod;
    }

    public Date getExpectedfinishdate() {
        return expectedfinishdate;
    }

    public float getTotalleasevalue() {
        return totalleasevalue;
    }

    public boolean isIscompleted() {
        return iscompleted;
    }

    public boolean isIsactivated() {
        return isactivated;
    }

    public Date getPaymentcompletedon() {
        return paymentcompletedon;
    }
}
