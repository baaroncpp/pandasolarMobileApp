package com.panda.solar.Model.entities;

public class LeaseSaleModel {

    private int leaseoffer;
    private String agentid;
    private String customerid;
    private float cordlat;
    private float cordlong;
    private String deviceserial;

    public LeaseSaleModel(){super();}

    public LeaseSaleModel(int leaseoffer, String agentid, String customerid, float cordlat, float cordlong, String deviceserial) {
        this.leaseoffer = leaseoffer;
        this.agentid = agentid;
        this.customerid = customerid;
        this.cordlat = cordlat;
        this.cordlong = cordlong;
        this.deviceserial = deviceserial;
    }

    public void setLeaseoffer(int leaseoffer) {
        this.leaseoffer = leaseoffer;
    }

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public void setCordlat(float cordlat) {
        this.cordlat = cordlat;
    }

    public void setCordlong(float cordlong) {
        this.cordlong = cordlong;
    }

    public void setDeviceserial(String deviceserial) {
        this.deviceserial = deviceserial;
    }
}
