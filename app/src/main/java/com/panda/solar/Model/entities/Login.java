package com.panda.solar.Model.entities;

import com.google.gson.annotations.SerializedName;

public class Login {

    private String username;
    private String password;

    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

}