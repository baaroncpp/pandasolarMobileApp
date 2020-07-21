package com.panda.solar.Model.entities;

public class KeyValueModel {

    private String name;
    private Long value;

    public KeyValueModel(String name, long value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Long getValue() {
        return value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
