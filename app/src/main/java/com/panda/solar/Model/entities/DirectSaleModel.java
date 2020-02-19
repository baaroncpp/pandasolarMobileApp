package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class DirectSaleModel implements Parcelable {

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

    protected DirectSaleModel(Parcel in) {
        agentid = in.readString();
        customerid = in.readString();
        description = in.readString();
        lat = in.readFloat();
        long_ = in.readFloat();
        productid = in.readString();
        quantity = in.readInt();
        scannedserial = in.readString();
    }

    public static final Creator<DirectSaleModel> CREATOR = new Creator<DirectSaleModel>() {
        @Override
        public DirectSaleModel createFromParcel(Parcel in) {
            return new DirectSaleModel(in);
        }

        @Override
        public DirectSaleModel[] newArray(int size) {
            return new DirectSaleModel[size];
        }
    };

    public void setAgentid(String agentid) {
        this.agentid = agentid;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public void setCustomerid(String customerid) {
        this.customerid = customerid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setLong_(float long_) {
        this.long_ = long_;
    }

    public void setProductid(String productid) {
        this.productid = productid;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setScannedserial(String scannedserial) {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(agentid);
        dest.writeString(customerid);
        dest.writeString(description);
        dest.writeFloat(lat);
        dest.writeFloat(long_);
        dest.writeString(productid);
        dest.writeInt(quantity);
        dest.writeString(scannedserial);
    }
}
