package com.panda.solar.Model.entities;

import org.parceler.Parcel;

/**
 * Created by macosx on 27/01/2019.
 */

@Parcel
public class Place {

    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
