package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

public class LeaseOffer implements Parcelable {

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

    protected LeaseOffer(Parcel in) {
        id = in.readInt();
        product = in.readParcelable(Product.class.getClassLoader());
        code = in.readString();
        leaseperiod = (short) in.readInt();
        percentlease = (short) in.readInt();
        recurrentpaymentamount = in.readFloat();
        isactive = in.readByte() != 0;
        intialdeposit = in.readFloat();
        name = in.readString();
        description = in.readString();
    }

    public static final Creator<LeaseOffer> CREATOR = new Creator<LeaseOffer>() {
        @Override
        public LeaseOffer createFromParcel(Parcel in) {
            return new LeaseOffer(in);
        }

        @Override
        public LeaseOffer[] newArray(int size) {
            return new LeaseOffer[size];
        }
    };

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

    public void setId(int id) {
        this.id = id;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setLeaseperiod(short leaseperiod) {
        this.leaseperiod = leaseperiod;
    }

    public void setPercentlease(short percentlease) {
        this.percentlease = percentlease;
    }

    public void setRecurrentpaymentamount(float recurrentpaymentamount) {
        this.recurrentpaymentamount = recurrentpaymentamount;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public void setIntialdeposit(float intialdeposit) {
        this.intialdeposit = intialdeposit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeParcelable(product, flags);
        dest.writeString(code);
        dest.writeInt((int) leaseperiod);
        dest.writeInt((int) percentlease);
        dest.writeFloat(recurrentpaymentamount);
        dest.writeByte((byte) (isactive ? 1 : 0));
        dest.writeFloat(intialdeposit);
        dest.writeString(name);
        dest.writeString(description);
    }
}
