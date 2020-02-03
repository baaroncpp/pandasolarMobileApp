package com.panda.solar.Model.entities;

import java.util.Date;

public class Product {

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
}
