package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.panda.solar.Model.constants.AgentType;
import com.panda.solar.Model.constants.IdType;

import java.util.Date;

public class AgentMeta implements Parcelable {

    private String userid;
    private User user;
    private String profilepath;
    private String contractdocpath;
    private int villageid;
    private String address;
    private Date createdon;
    private float agentcommissionrate;
    private boolean isdeactivated;
    private Date deactivatedon;
    private boolean isactive;
    private Date activatedon;
    private float shoplong;
    private float shoplat;
    private String companyname;
    private String postaladdress;
    private String tinnumber;
    private String coipath;
    private AgentType agenttype;
    private IdType idtype;
    private String idnumber;

    public AgentMeta() {
        super();
    }

    protected AgentMeta(Parcel in) {
        userid = in.readString();
        user = in.readParcelable(User.class.getClassLoader());
        profilepath = in.readString();
        contractdocpath = in.readString();
        villageid = in.readInt();
        address = in.readString();
        agentcommissionrate = in.readFloat();
        isdeactivated = in.readByte() != 0;
        isactive = in.readByte() != 0;
        shoplong = in.readFloat();
        shoplat = in.readFloat();
        companyname = in.readString();
        postaladdress = in.readString();
        tinnumber = in.readString();
        coipath = in.readString();
        idnumber = in.readString();
    }

    public static final Creator<AgentMeta> CREATOR = new Creator<AgentMeta>() {
        @Override
        public AgentMeta createFromParcel(Parcel in) {
            return new AgentMeta(in);
        }

        @Override
        public AgentMeta[] newArray(int size) {
            return new AgentMeta[size];
        }
    };

    public String getUserid() {
        return userid;
    }

    public User getUser() {
        return user;
    }

    public String getProfilepath() {
        return profilepath;
    }

    public String getContractdocpath() {
        return contractdocpath;
    }

    public int getVillageid() {
        return villageid;
    }

    public String getAddress() {
        return address;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public float getAgentcommissionrate() {
        return agentcommissionrate;
    }

    public boolean isIsdeactivated() {
        return isdeactivated;
    }

    public Date getDeactivatedon() {
        return deactivatedon;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public Date getActivatedon() {
        return activatedon;
    }

    public float getShoplong() {
        return shoplong;
    }

    public float getShoplat() {
        return shoplat;
    }

    public String getCompanyname() {
        return companyname;
    }

    public String getPostaladdress() {
        return postaladdress;
    }

    public String getTinnumber() {
        return tinnumber;
    }

    public String getCoipath() {
        return coipath;
    }

    public AgentType getAgenttype() {
        return agenttype;
    }

    public IdType getIdtype() {
        return idtype;
    }

    public String getIdnumber() {
        return idnumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(userid);
        dest.writeParcelable(user, flags);
        dest.writeString(profilepath);
        dest.writeString(contractdocpath);
        dest.writeInt(villageid);
        dest.writeString(address);
        dest.writeFloat(agentcommissionrate);
        dest.writeByte((byte) (isdeactivated ? 1 : 0));
        dest.writeByte((byte) (isactive ? 1 : 0));
        dest.writeFloat(shoplong);
        dest.writeFloat(shoplat);
        dest.writeString(companyname);
        dest.writeString(postaladdress);
        dest.writeString(tinnumber);
        dest.writeString(coipath);
        dest.writeString(idnumber);
    }
}
