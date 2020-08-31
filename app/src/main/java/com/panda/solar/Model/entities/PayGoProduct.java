package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.panda.solar.Model.constants.PayGoProductStatus;

public class PayGoProduct implements Parcelable {

    private String tokenSerialNumber;
    private LeaseOffer leaseOffer;
    private PayGoProductStatus payGoProductStatus;

    public PayGoProduct(){super();}

    protected PayGoProduct(Parcel in) {
        tokenSerialNumber = in.readString();
        leaseOffer = in.readParcelable(LeaseOffer.class.getClassLoader());
    }

    public static final Creator<PayGoProduct> CREATOR = new Creator<PayGoProduct>() {
        @Override
        public PayGoProduct createFromParcel(Parcel in) {
            return new PayGoProduct(in);
        }

        @Override
        public PayGoProduct[] newArray(int size) {
            return new PayGoProduct[size];
        }
    };

    public String getTokenSerialNumber() {
        return tokenSerialNumber;
    }

    public LeaseOffer getLeaseOffer() {
        return leaseOffer;
    }

    public PayGoProductStatus getPayGoProductStatus() {
        return payGoProductStatus;
    }

    public void setTokenSerialNumber(String tokenSerialNumber) {
        this.tokenSerialNumber = tokenSerialNumber;
    }

    public void setLeaseOffer(LeaseOffer leaseOffer) {
        this.leaseOffer = leaseOffer;
    }

    public void setPayGoProductStatus(PayGoProductStatus payGoProductStatus) {
        this.payGoProductStatus = payGoProductStatus;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tokenSerialNumber);
        dest.writeParcelable(leaseOffer, flags);
    }
}
