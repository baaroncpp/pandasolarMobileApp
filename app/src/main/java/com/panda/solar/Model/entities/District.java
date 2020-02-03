package com.panda.solar.Model.entities;

public class District {

    private int id;
    private String name;
    private int regionid;
    private boolean review;

    public District(int id, String name, int regionid, boolean review) {
        this.id = id;
        this.name = name;
        this.regionid = regionid;
        this.review = review;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRegionid() {
        return regionid;
    }

    public boolean isReview() {
        return review;
    }
}
