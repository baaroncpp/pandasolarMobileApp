package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class SaleModel implements Parcelable {

    private String id;
    private String saletype;
    private float amount;
    private String description;
    private String scannedserial;
    private float agentcommission;
    private AgentMeta agent;
    private float long_;
    private float lat;
    private boolean isreviewed;
    private short salestatus;
    private Date createdon;
    private Date completedon;
    private Product product;
    private int quantity;
    private CustomerMeta customer;

    public SaleModel(){
        super();
    }


    protected SaleModel(Parcel in) {
        id = in.readString();
        saletype = in.readString();
        amount = in.readFloat();
        description = in.readString();
        scannedserial = in.readString();
        agentcommission = in.readFloat();
        agent = in.readParcelable(AgentMeta.class.getClassLoader());
        long_ = in.readFloat();
        lat = in.readFloat();
        isreviewed = in.readByte() != 0;
        salestatus = (short) in.readInt();
        product = in.readParcelable(Product.class.getClassLoader());
        quantity = in.readInt();
        customer = in.readParcelable(CustomerMeta.class.getClassLoader());
    }

    public static final Creator<SaleModel> CREATOR = new Creator<SaleModel>() {
        @Override
        public SaleModel createFromParcel(Parcel in) {
            return new SaleModel(in);
        }

        @Override
        public SaleModel[] newArray(int size) {
            return new SaleModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(saletype);
        dest.writeFloat(amount);
        dest.writeString(description);
        dest.writeString(scannedserial);
        dest.writeFloat(agentcommission);
        dest.writeParcelable(agent, flags);
        dest.writeFloat(long_);
        dest.writeFloat(lat);
        dest.writeByte((byte) (isreviewed ? 1 : 0));
        dest.writeInt((int) salestatus);
        dest.writeParcelable(product, flags);
        dest.writeInt(quantity);
        dest.writeParcelable(customer, flags);
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

    public AgentMeta getAgent() {
        return agent;
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

    public Date getCompletedon() {
        return completedon;
    }

    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public CustomerMeta getCustomer() {
        return customer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSaletype(String saletype) {
        this.saletype = saletype;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setScannedserial(String scannedserial) {
        this.scannedserial = scannedserial;
    }

    public void setAgentcommission(float agentcommission) {
        this.agentcommission = agentcommission;
    }

    public void setAgent(AgentMeta agent) {
        this.agent = agent;
    }

    public void setLong_(float long_) {
        this.long_ = long_;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public void setIsreviewed(boolean isreviewed) {
        this.isreviewed = isreviewed;
    }

    public void setSalestatus(short salestatus) {
        this.salestatus = salestatus;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public void setCompletedon(Date completedon) {
        this.completedon = completedon;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setCustomer(CustomerMeta customer) {
        this.customer = customer;
    }
}
