package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class AndroidTokens implements Parcelable {

	private String userid;
	private String token;
	private Date createdon;

	public AndroidTokens(){super();}

	protected AndroidTokens(Parcel in) {
		userid = in.readString();
		token = in.readString();
	}

	public static final Creator<AndroidTokens> CREATOR = new Creator<AndroidTokens>() {
		@Override
		public AndroidTokens createFromParcel(Parcel in) {
			return new AndroidTokens(in);
		}

		@Override
		public AndroidTokens[] newArray(int size) {
			return new AndroidTokens[size];
		}
	};

	public String getUserid() {
		return userid;
	}

	public String getToken() {
		return token;
	}

	public Date getCreatedon() {
		return createdon;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setToken(String token) {
		this.token = token;
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
		dest.writeString(userid);
		dest.writeString(token);
	}
}
