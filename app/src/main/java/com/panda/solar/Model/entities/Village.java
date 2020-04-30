package com.panda.solar.Model.entities;


import android.os.Parcel;
import android.os.Parcelable;

public class Village implements Parcelable {

	private int id;
	private int parishid;
	private String name;

	public Village(){super();}

	protected Village(Parcel in) {
		id = in.readInt();
		parishid = in.readInt();
		name = in.readString();
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(id);
		dest.writeInt(parishid);
		dest.writeString(name);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	public static final Creator<Village> CREATOR = new Creator<Village>() {
		@Override
		public Village createFromParcel(Parcel in) {
			return new Village(in);
		}

		@Override
		public Village[] newArray(int size) {
			return new Village[size];
		}
	};

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParishid() {
		return parishid;
	}

	public void setParishid(int parishid) {
		this.parishid = parishid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
