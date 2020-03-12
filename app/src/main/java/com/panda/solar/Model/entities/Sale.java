package com.panda.solar.Model.entities;

import java.util.Date;

public class Sale {

    private String id;
    private String saletype;
    private float amount;
    private String description;
    private String scannedserial;
    private float agentcommission;
    private String agentid;
    private float long_;
    private float lat;
    private boolean isreviewed;
    private short salestatus;
    private Date createdon;

    public Sale(){super();}

    public Sale(String id, String saletype, float amount, String description, String scannedserial, float agentcommission, String agentid, float long_, float lat, boolean isreviewed, short salestatus, Date createdon) {
        this.id = id;
        this.saletype = saletype;
        this.amount = amount;
        this.description = description;
        this.scannedserial = scannedserial;
        this.agentcommission = agentcommission;
        this.agentid = agentid;
        this.long_ = long_;
        this.lat = lat;
        this.isreviewed = isreviewed;
        this.salestatus = salestatus;
        this.createdon = createdon;
    }

    public String getId() {
        return id;
    }

    public String getSaletype() {
        return saletype;
    }

    public float getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getScannedserial() {
        return scannedserial;
    }

    public float getAgentcommission() {
        return agentcommission;
    }

    public String getAgentid() {
        return agentid;
    }

    public float getLong_() {
        return long_;
    }

    public float getLat() {
        return lat;
    }

    public boolean isIsreviewed() {
        return isreviewed;
    }

    public short getSalestatus() {
        return salestatus;
    }

    public Date getCreatedon() {
        return createdon;
    }
}
