package com.panda.solar.Model.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Product implements Parcelable {

    private String id;
    private String name;
    private String serialNumber;
    private String[] equipmentlist;
    private float unitcostselling;
    private String description;
    private String thumbnail;
    private Boolean isActive;
    private Boolean isleasable;
    private Boolean usestoken;
    private Date createdon;

    public Product(){
        super();
    }


    public Product(String id, String name, String serialNumber, String[] equipmentlist, float unitcostselling, String description, String thumbnail, Boolean isActive, Boolean isleasable, Boolean usestoken, Date createdon) {
        this.id = id;
        this.name = name;
        this.serialNumber = serialNumber;
        this.equipmentlist = equipmentlist;
        this.unitcostselling = unitcostselling;
        this.description = description;
        this.thumbnail = thumbnail;
        this.isActive = isActive;
        this.isleasable = isleasable;
        this.usestoken = usestoken;
        this.createdon = createdon;
    }

    protected Product(Parcel in) {
        id = in.readString();
        name = in.readString();
        serialNumber = in.readString();
        equipmentlist = in.createStringArray();
        unitcostselling = in.readFloat();
        description = in.readString();
        thumbnail = in.readString();
        byte tmpIsActive = in.readByte();
        isActive = tmpIsActive == 0 ? null : tmpIsActive == 1;
        byte tmpIsleasable = in.readByte();
        isleasable = tmpIsleasable == 0 ? null : tmpIsleasable == 1;
        byte tmpUsestoken = in.readByte();
        usestoken = tmpUsestoken == 0 ? null : tmpUsestoken == 1;
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public String[] getEquipmentlist() {
        return equipmentlist;
    }

    public float getUnitcostselling() {
        return unitcostselling;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public Boolean getActive() {
        return isActive;
    }

    public Boolean getIsleasable() {
        return isleasable;
    }

    public Boolean getUsestoken() {
        return usestoken;
    }

    public Date getCreatedon() {
        return createdon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUnitcostselling(float unitcostselling) {
        this.unitcostselling = unitcostselling;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIsleasable(Boolean isleasable) {
        this.isleasable = isleasable;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(serialNumber);
        dest.writeStringArray(equipmentlist);
        dest.writeFloat(unitcostselling);
        dest.writeString(description);
        dest.writeString(thumbnail);
        dest.writeByte((byte) (isActive == null ? 0 : isActive ? 1 : 2));
        dest.writeByte((byte) (isleasable == null ? 0 : isleasable ? 1 : 2));
        dest.writeByte((byte) (usestoken == null ? 0 : usestoken ? 1 : 2));
    }
}
