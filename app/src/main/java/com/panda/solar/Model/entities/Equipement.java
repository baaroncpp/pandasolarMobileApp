package com.panda.solar.Model.entities;

import java.math.BigDecimal;
import java.util.Date;

public class Equipement {

    private String categoryId;
    private String name;
    private String model;
    private Date dateofmanufacture;
    private boolean available;
    private String description;
    private String serial;
    private BigDecimal quantity;

    public Equipement(String categoryId,
                      String name,
                      String model,
                      Date dateofmanufacture,
                      boolean available,
                      String description,
                      String serial,
                      BigDecimal quantity) {
        this.categoryId = categoryId;
        this.name = name;
        this.model = model;
        this.dateofmanufacture = dateofmanufacture;
        this.available = available;
        this.description = description;
        this.serial = serial;
        this.quantity = quantity;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getName() {
        return name;
    }

    public String getModel() {
        return model;
    }

    public Date getDateofmanufacture() {
        return dateofmanufacture;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getDescription() {
        return description;
    }

    public String getSerial() {
        return serial;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }
}
