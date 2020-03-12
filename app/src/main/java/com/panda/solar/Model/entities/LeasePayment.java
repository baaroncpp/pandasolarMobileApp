package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class LeasePayment implements Parcelable {

    private String id;
    private String leaseid;
    private float amount;
    private String payeename;
    private String payeemobilenumber;
    private String paymentchannel;
    private short paymentstatus;
    private String transactionid;
    private String channeltransactionid;
    private String channelstatuscode;
    private String channelmessage;
    private Date createdon;

    public LeasePayment(){super();}

    public LeasePayment(String id, String leaseid, float amount, String payeename, String payeemobilenumber, String paymentchannel, short paymentstatus, String transactionid, String channeltransactionid, String channelstatuscode, String channelmessage, Date createdon) {
        this.id = id;
        this.leaseid = leaseid;
        this.amount = amount;
        this.payeename = payeename;
        this.payeemobilenumber = payeemobilenumber;
        this.paymentchannel = paymentchannel;
        this.paymentstatus = paymentstatus;
        this.transactionid = transactionid;
        this.channeltransactionid = channeltransactionid;
        this.channelstatuscode = channelstatuscode;
        this.channelmessage = channelmessage;
        this.createdon = createdon;
    }

    protected LeasePayment(Parcel in) {
        id = in.readString();
        leaseid = in.readString();
        amount = in.readFloat();
        payeename = in.readString();
        payeemobilenumber = in.readString();
        paymentstatus = (short) in.readInt();
        transactionid = in.readString();
        channeltransactionid = in.readString();
        channelstatuscode = in.readString();
        channelmessage = in.readString();
    }

    public static final Creator<LeasePayment> CREATOR = new Creator<LeasePayment>() {
        @Override
        public LeasePayment createFromParcel(Parcel in) {
            return new LeasePayment(in);
        }

        @Override
        public LeasePayment[] newArray(int size) {
            return new LeasePayment[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getLeaseid() {
        return leaseid;
    }

    public float getAmount() {
        return amount;
    }

    public String getPayeename() {
        return payeename;
    }

    public String getPayeemobilenumber() {
        return payeemobilenumber;
    }

    public String getPaymentchannel() {
        return paymentchannel;
    }

    public short getPaymentstatus() {
        return paymentstatus;
    }

    public String getTransactionid() {
        return transactionid;
    }

    public String getChanneltransactionid() {
        return channeltransactionid;
    }

    public String getChannelstatuscode() {
        return channelstatuscode;
    }

    public String getChannelmessage() {
        return channelmessage;
    }

    public Date getCreatedon() {
        return createdon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(leaseid);
        dest.writeFloat(amount);
        dest.writeString(payeename);
        dest.writeString(payeemobilenumber);
        dest.writeInt((int) paymentstatus);
        dest.writeString(transactionid);
        dest.writeString(channeltransactionid);
        dest.writeString(channelstatuscode);
        dest.writeString(channelmessage);
        dest.writeString(paymentchannel);
    }
}
