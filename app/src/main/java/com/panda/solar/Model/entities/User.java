package com.panda.solar.Model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Date;

@Entity(tableName = "user_table")
public class User implements Parcelable {

    @PrimaryKey
    private String id;
    private String username;
    private boolean isactive;
    private boolean isapproved;
    private Date createdon;
    private String usertype;
    private String firstname;
    private String middlename;
    private String lastname;
    private String email;
    private String primaryphone;
    private String companyemail;
    private Date dateofbirth;
    private Date updatedon;
    private String profilepath;
    private String coipath;
    private String idcopypath;
    private String contractpath;
    private String consentformpath;
    private String housephotopath;

    public User() { super();}

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        isactive = in.readByte() != 0;
        isapproved = in.readByte() != 0;
        usertype = in.readString();
        firstname = in.readString();
        middlename = in.readString();
        lastname = in.readString();
        email = in.readString();
        primaryphone = in.readString();
        companyemail = in.readString();
        profilepath = in.readString();
        coipath = in.readString();
        idcopypath = in.readString();
        contractpath = in.readString();
        consentformpath = in.readString();
        housephotopath = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public boolean isIsapproved() {
        return isapproved;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public String getUsertype() {
        return usertype;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getEmail() {
        return email;
    }

    public String getPrimaryphone() {
        return primaryphone;
    }

    public String getCompanyemail() {
        return companyemail;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public Date getUpdatedon() {
        return updatedon;
    }

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

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public void setIsapproved(boolean isapproved) {
        this.isapproved = isapproved;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrimaryphone(String primaryphone) {
        this.primaryphone = primaryphone;
    }

    public void setCompanyemail(String companyemail) {
        this.companyemail = companyemail;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
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
        dest.writeString(id);
        dest.writeString(username);
        dest.writeByte((byte) (isactive ? 1 : 0));
        dest.writeByte((byte) (isapproved ? 1 : 0));
        dest.writeString(usertype);
        dest.writeString(firstname);
        dest.writeString(middlename);
        dest.writeString(lastname);
        dest.writeString(email);
        dest.writeString(primaryphone);
        dest.writeString(companyemail);
        dest.writeString(profilepath);
        dest.writeString(coipath);
        dest.writeString(idcopypath);
        dest.writeString(contractpath);
        dest.writeString(consentformpath);
        dest.writeString(housephotopath);
    }
}
