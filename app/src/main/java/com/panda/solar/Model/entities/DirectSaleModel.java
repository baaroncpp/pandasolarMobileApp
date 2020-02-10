package com.panda.solar.Model.entities;

import java.util.Date;

public class DirectSaleModel {

    private String agentid;
    private Date createdon;
    private String customerid;
    private String description;
    private float lat;
    private float long_;
    private String productid;
    private int quantity;
    private String scannedserial;

    public DirectSaleModel(){
        super();
    }

    public DirectSaleModel(String agentid, Date createdon, String customerid, String description, float lat, float long_, String productid, int quantity, String scannedserial) {
        this.agentid = agentid;
        this.createdon = createdon;
        this.customerid = customerid;
        this.description = description;
        this.lat = lat;
        this.long_ = long_;
        this.productid = productid;
        this.quantity = quantity;
        this.scannedserial = scannedserial;
    }

    public String getAgentid() {
        return agentid;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public String getCustomerid() {
        return customerid;
    }

    public String getDescription() {
        return description;
    }

    public float getLat() {
        return lat;
    }

    public float getLong_() {
        return long_;
    }

    public String getProductid() {
        return productid;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getScannedserial() {
        return scannedserial;
    }
}
