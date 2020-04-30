package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import com.panda.solar.Model.constants.PayGoProductStatus;

public class PayGoProductModel implements Parcelable {
	private String tokenSerialNumber;	
	private int leaseOfferid;
	private String payGoProductStatus;

	public PayGoProductModel(){super();}

	protected PayGoProductModel(Parcel in) {
		tokenSerialNumber = in.readString();
		leaseOfferid = in.readInt();
		payGoProductStatus = in.readString();
	}

	public static final Creator<PayGoProductModel> CREATOR = new Creator<PayGoProductModel>() {
		@Override
		public PayGoProductModel createFromParcel(Parcel in) {
			return new PayGoProductModel(in);
		}

		@Override
		public PayGoProductModel[] newArray(int size) {
			return new PayGoProductModel[size];
		}
	};

	public String getTokenSerialNumber() {
		return tokenSerialNumber;
	}

	public int getLeaseOfferid() {
		return leaseOfferid;
	}

	public String getPayGoProductStatus() {
		return payGoProductStatus;
	}

	public void setTokenSerialNumber(String tokenSerialNumber) {
		this.tokenSerialNumber = tokenSerialNumber;
	}

	public void setLeaseOfferid(int leaseOfferid) {
		this.leaseOfferid = leaseOfferid;
	}

	public void setPayGoProductStatus(String payGoProductStatus) {
		this.payGoProductStatus = payGoProductStatus;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(tokenSerialNumber);
		dest.writeInt(leaseOfferid);
		dest.writeString(payGoProductStatus);
	}
}
