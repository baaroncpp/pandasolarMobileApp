package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.panda.solar.Model.constants.IdType;

import java.util.Date;

public class CustomerMeta implements Parcelable {

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

    public CustomerMeta(){
        super();
    }

    protected CustomerMeta(Parcel in) {
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

    public static final Creator<CustomerMeta> CREATOR = new Creator<CustomerMeta>() {
        @Override
        public CustomerMeta createFromParcel(Parcel in) {
            return new CustomerMeta(in);
        }

        @Override
        public CustomerMeta[] newArray(int size) {
            return new CustomerMeta[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeParcelable(user, flags);
        dest.writeInt(villageid);
        dest.writeString(secondaryphone);
        dest.writeString(secondaryemail);
        dest.writeString(idnumber);
        dest.writeString(consentformpath);
        dest.writeString(idcopypath);
        dest.writeString(profilephotopath);
        dest.writeString(address);
        dest.writeString(housephotopath);
        dest.writeFloat(homelat);
        dest.writeFloat(homelong);
    }
}
