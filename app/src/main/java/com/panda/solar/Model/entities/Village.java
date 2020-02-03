package com.panda.solar.Model.entities;

public class Village {

    private int id;
    private int parishid;
    private String name;

    public Village(int id, int parishid, String name) {
        this.id = id;
        this.parishid = parishid;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getParishid() {
        return parishid;
    }

    public String getName() {
        return name;
    }
}
