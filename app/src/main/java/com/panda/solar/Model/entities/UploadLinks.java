package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

public class UploadLinks implements Parcelable {
	
    private String profilepath;
    private String coipath;
    private String idcopypath;
    private String contractpath;
    private String consentformpath;
    private String housephotopath;

    public UploadLinks(){super();}

    protected UploadLinks(Parcel in) {
        profilepath = in.readString();
        coipath = in.readString();
        idcopypath = in.readString();
        contractpath = in.readString();
        consentformpath = in.readString();
        housephotopath = in.readString();
    }

    public static final Creator<UploadLinks> CREATOR = new Creator<UploadLinks>() {
        @Override
        public UploadLinks createFromParcel(Parcel in) {
            return new UploadLinks(in);
        }

        @Override
        public UploadLinks[] newArray(int size) {
            return new UploadLinks[size];
        }
    };

    public String getProfilepath() {
        return profilepath;
    }

    public String getCoipath() {
        return coipath;
    }

    public String getIdcopypath() {
        return idcopypath;
    }

    public String getContractpath() {
        return contractpath;
    }

    public String getConsentformpath() {
        return consentformpath;
    }

    public String getHousephotopath() {
        return housephotopath;
    }

    public void setProfilepath(String profilepath) {
        this.profilepath = profilepath;
    }

    public void setCoipath(String coipath) {
        this.coipath = coipath;
    }

    public void setIdcopypath(String idcopypath) {
        this.idcopypath = idcopypath;
    }

    public void setContractpath(String contractpath) {
        this.contractpath = contractpath;
    }

    public void setConsentformpath(String consentformpath) {
        this.consentformpath = consentformpath;
    }

    public void setHousephotopath(String housephotopath) {
        this.housephotopath = housephotopath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(profilepath);
        dest.writeString(coipath);
        dest.writeString(idcopypath);
        dest.writeString(contractpath);
        dest.writeString(consentformpath);
        dest.writeString(housephotopath);
    }
}
