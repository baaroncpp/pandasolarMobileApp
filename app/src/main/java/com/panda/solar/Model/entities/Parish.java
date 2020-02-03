package com.panda.solar.Model.entities;

public class Parish {

    private int id;
    private int subcountyid;
    private String name;

    public Parish(int id, int subcountyid, String name) {
        this.id = id;
        this.subcountyid = subcountyid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getSubcountyid() {
        return subcountyid;
    }

    public String getName() {
        return name;
    }
}
