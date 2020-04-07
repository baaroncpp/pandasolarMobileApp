package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

import com.panda.solar.Model.constants.IdType;
import com.panda.solar.Model.constants.UserType;

public class CustomerModel implements Parcelable {
	
	private String companyemail;
	private Date dateofbirth;
	private String email;
	private String firstname;
	private String lastname;
	private String middlename;
	private String password;
	private String primaryphone;
	private String username;
	private UserType usertype;
	private String sex;
	private String title;
	
	private String address;
	private String consentformpath;
	private float homelat;
	private float homelong;
	private String idcopypath;
	private String idnumber;
	private String idtype;
	private String profilephotopath;
	private String secondaryemail;
	private String secondaryphone;
	private short villageid;

	public CustomerModel(){super();}

    protected CustomerModel(Parcel in) {
        companyemail = in.readString();
        email = in.readString();
        firstname = in.readString();
        lastname = in.readString();
        middlename = in.readString();
        password = in.readString();
        primaryphone = in.readString();
        username = in.readString();
        sex = in.readString();
        title = in.readString();
        address = in.readString();
        consentformpath = in.readString();
        homelat = in.readFloat();
        homelong = in.readFloat();
        idcopypath = in.readString();
        idnumber = in.readString();
        idtype = in.readString();
        profilephotopath = in.readString();
        secondaryemail = in.readString();
        secondaryphone = in.readString();
        villageid = (short) in.readInt();
    }

    public static final Creator<CustomerModel> CREATOR = new Creator<CustomerModel>() {
        @Override
        public CustomerModel createFromParcel(Parcel in) {
            return new CustomerModel(in);
        }

        @Override
        public CustomerModel[] newArray(int size) {
            return new CustomerModel[size];
        }
    };

    public void setCompanyemail(String companyemail) {
        this.companyemail = companyemail;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public void setMiddlename(String middlename) {
        this.middlename = middlename;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPrimaryphone(String primaryphone) {
        this.primaryphone = primaryphone;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsertype(UserType usertype) {
        this.usertype = usertype;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setConsentformpath(String consentformpath) {
        this.consentformpath = consentformpath;
    }

    public void setHomelat(float homelat) {
        this.homelat = homelat;
    }

    public void setHomelong(float homelong) {
        this.homelong = homelong;
    }

    public void setIdcopypath(String idcopypath) {
        this.idcopypath = idcopypath;
    }

    public void setIdnumber(String idnumber) {
        this.idnumber = idnumber;
    }

    public void setIdtype(String idtype) {
        this.idtype = idtype;
    }

    public void setProfilephotopath(String profilephotopath) {
        this.profilephotopath = profilephotopath;
    }

    public void setSecondaryemail(String secondaryemail) {
        this.secondaryemail = secondaryemail;
    }

    public void setSecondaryphone(String secondaryphone) {
        this.secondaryphone = secondaryphone;
    }

    public void setVillageid(short villageid) {
        this.villageid = villageid;
    }

    public String getCompanyemail() {
        return companyemail;
    }

    public Date getDateofbirth() {
        return dateofbirth;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getPassword() {
        return password;
    }

    public String getPrimaryphone() {
        return primaryphone;
    }

    public String getUsername() {
        return username;
    }

    public UserType getUsertype() {
        return usertype;
    }

    public String getSex() {
        return sex;
    }

    public String getTitle() {
        return title;
    }

    public String getAddress() {
        return address;
    }

    public String getConsentformpath() {
        return consentformpath;
    }

    public float getHomelat() {
        return homelat;
    }

    public float getHomelong() {
        return homelong;
    }

    public String getIdcopypath() {
        return idcopypath;
    }

    public String getIdnumber() {
        return idnumber;
    }

    public String getIdtype() {
        return idtype;
    }

    public String getProfilephotopath() {
        return profilephotopath;
    }

    public String getSecondaryemail() {
        return secondaryemail;
    }

    public String getSecondaryphone() {
        return secondaryphone;
    }

    public short getVillageid() {
        return villageid;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(companyemail);
        dest.writeString(email);
        dest.writeString(firstname);
        dest.writeString(lastname);
        dest.writeString(middlename);
        dest.writeString(password);
        dest.writeString(primaryphone);
        dest.writeString(username);
        dest.writeString(sex);
        dest.writeString(title);
        dest.writeString(address);
        dest.writeString(consentformpath);
        dest.writeFloat(homelat);
        dest.writeFloat(homelong);
        dest.writeString(idcopypath);
        dest.writeString(idnumber);
        dest.writeString(idtype);
        dest.writeString(profilephotopath);
        dest.writeString(secondaryemail);
        dest.writeString(secondaryphone);
        dest.writeInt((int) villageid);
    }
}
