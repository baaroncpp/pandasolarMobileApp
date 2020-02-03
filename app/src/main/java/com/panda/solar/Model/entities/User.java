package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class User implements Parcelable {

    private String id;
    private String username;
    private String password;
    private boolean isactive;
    private boolean isapproved;
    private Date passwordreseton;
    private Date createdon;
    private String usertype;
    private String firstname;
    private String middlename;
    private String lastname;
    private String email;
    private String primaryphone;
    private String companyemail;
    private Date dateofbirth;
    private Date lastlogon;
    private Date updatedon;

    public User() { super();}

    protected User(Parcel in) {
        id = in.readString();
        username = in.readString();
        password = in.readString();
        isactive = in.readByte() != 0;
        isapproved = in.readByte() != 0;
        usertype = in.readString();
        firstname = in.readString();
        middlename = in.readString();
        lastname = in.readString();
        email = in.readString();
        primaryphone = in.readString();
        companyemail = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(username);
        parcel.writeString(password);
        parcel.writeByte((byte) (isactive ? 1 : 0));
        parcel.writeByte((byte) (isapproved ? 1 : 0));
        parcel.writeString(usertype);
        parcel.writeString(firstname);
        parcel.writeString(middlename);
        parcel.writeString(lastname);
        parcel.writeString(email);
        parcel.writeString(primaryphone);
        parcel.writeString(companyemail);
    }

    public User(String id, String username, String password, boolean isactive, boolean isapproved, Date passwordreseton, Date createdon, String usertype, String firstname, String middlename, String lastname, String email, String primaryphone, String companyemail, Date dateofbirth, Date lastlogon, Date updatedon) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.isactive = isactive;
        this.isapproved = isapproved;
        this.passwordreseton = passwordreseton;
        this.createdon = createdon;
        this.usertype = usertype;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.email = email;
        this.primaryphone = primaryphone;
        this.companyemail = companyemail;
        this.dateofbirth = dateofbirth;
        this.lastlogon = lastlogon;
        this.updatedon = updatedon;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isIsactive() {
        return isactive;
    }

    public boolean isIsapproved() {
        return isapproved;
    }

    public Date getPasswordreseton() {
        return passwordreseton;
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

    public Date getLastlogon() {
        return lastlogon;
    }

    public Date getUpdatedon() {
        return updatedon;
    }

    //setters


    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setIsactive(boolean isactive) {
        this.isactive = isactive;
    }

    public void setIsapproved(boolean isapproved) {
        this.isapproved = isapproved;
    }

    public void setPasswordreseton(Date passwordreseton) {
        this.passwordreseton = passwordreseton;
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

    public void setLastlogon(Date lastlogon) {
        this.lastlogon = lastlogon;
    }

    public void setUpdatedon(Date updatedon) {
        this.updatedon = updatedon;
    }


}
