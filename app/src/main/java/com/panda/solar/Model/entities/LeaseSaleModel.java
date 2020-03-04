package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class LeaseSaleModel implements Parcelable {

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

    protected LeaseSaleModel(Parcel in) {
        leaseoffer = in.readInt();
        agentid = in.readString();
        customerid = in.readString();
        cordlat = in.readFloat();
        cordlong = in.readFloat();
        deviceserial = in.readString();
    }

    public static final Creator<LeaseSaleModel> CREATOR = new Creator<LeaseSaleModel>() {
        @Override
        public LeaseSaleModel createFromParcel(Parcel in) {
            return new LeaseSaleModel(in);
        }

        @Override
        public LeaseSaleModel[] newArray(int size) {
            return new LeaseSaleModel[size];
        }
    };

    public int getLeaseoffer() {
        return leaseoffer;
    }

    public String getAgentid() {
        return agentid;
    }

    public String getCustomerid() {
        return customerid;
    }

    public float getCordlat() {
        return cordlat;
    }

    public float getCordlong() {
        return cordlong;
    }

    public String getDeviceserial() {
        return deviceserial;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(leaseoffer);
        dest.writeString(agentid);
        dest.writeString(customerid);
        dest.writeFloat(cordlat);
        dest.writeFloat(cordlong);
        dest.writeString(deviceserial);
    }
}
