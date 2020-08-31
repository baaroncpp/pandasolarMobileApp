package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.panda.solar.Model.constants.IdType;
import java.util.Date;

public class Customer implements Parcelable {

    private String userid;
    private User user;
    private int villageid;
    private String secondaryphone;
    private String secondaryemail;
    private IdType idtype;
    private String idnumber;
    private String consentformpath;
    private String idcopypath;
    private String profilephotopath;
    private String address;
    private String housephotopath;
    private float homelat;
    private float homelong;
    private Date createdon;

    public Customer(){super();}

    protected Customer(Parcel in) {
        userid = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        villageid = in.readInt();
        secondaryphone = in.readString();
        secondaryemail = in.readString();
        idnumber = in.readString();
        consentformpath = in.readString();
        idcopypath = in.readString();
        profilephotopath = in.readString();
        address = in.readString();
        housephotopath = in.readString();
        homelat = in.readFloat();
        homelong = in.readFloat();
    }

    public static final Creator<Customer> CREATOR = new Creator<Customer>() {
        @Override
        public Customer createFromParcel(Parcel in) {
            return new Customer(in);
        }

        @Override
        public Customer[] newArray(int size) {
            return new Customer[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(userid);
        parcel.writeParcelable(user, i);
        parcel.writeInt(villageid);
        parcel.writeString(secondaryphone);
        parcel.writeString(secondaryemail);
        parcel.writeString(idnumber);
        parcel.writeString(consentformpath);
        parcel.writeString(idcopypath);
        parcel.writeString(profilephotopath);
        parcel.writeString(address);
        parcel.writeString(housephotopath);
        parcel.writeFloat(homelat);
        parcel.writeFloat(homelong);
    }

    public String getUserid() {
        return userid;
    }

    public User getUser() {
        return user;
    }

    public int getVillageid() {
        return villageid;
    }

    public String getSecondaryphone() {
        return secondaryphone;
    }

    public String getSecondaryemail() {
        return secondaryemail;
    }

    public IdType getIdtype() {
        return idtype;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public String getConsentformpath() {
        return consentformpath;
    }

    public String getIdcopypath() {
        return idcopypath;
    }

    public String getProfilephotopath() {
        return profilephotopath;
    }

    public String getAddress() {
        return address;
    }

    public String getHousephotopath() {
        return housephotopath;
    }

    public float getHomelat() {
        return homelat;
    }

    public float getHomelong() {
        return homelong;
    }

    public Date getCreatedon() {
        return createdon;
    }

    //setters


    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setVillageid(int villageid) {
        this.villageid = villageid;
    }

    public void setSecondaryphone(String secondaryphone) {
        this.secondaryphone = secondaryphone;
    }

    public void setSecondaryemail(String secondaryemail) {
        this.secondaryemail = secondaryemail;
    }

    public void setIdtype(IdType idtype) {
        this.idtype = idtype;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public void setConsentformpath(String consentformpath) {
        this.consentformpath = consentformpath;
    }

    public void setIdcopypath(String idcopypath) {
        this.idcopypath = idcopypath;
    }

    public void setProfilephotopath(String profilephotopath) {
        this.profilephotopath = profilephotopath;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setHousephotopath(String housephotopath) {
        this.housephotopath = housephotopath;
    }

    public void setHomelat(float homelat) {
        this.homelat = homelat;
    }

    public void setHomelong(float homelong) {
        this.homelong = homelong;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

}
