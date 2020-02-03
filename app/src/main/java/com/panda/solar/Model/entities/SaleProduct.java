package com.panda.solar.Model.entities;

import org.parceler.Parcel;

/**
 * Created by macosx on 27/01/2019.
 */

@Parcel
public class SaleProduct {

    public String name;
    public String imageUrl;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
