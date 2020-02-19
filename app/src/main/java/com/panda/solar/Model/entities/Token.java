package com.panda.solar.Model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "token_table")
public class Token {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String token;

    public Token() {
        super();
    }

    public Token(String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public int getId(){return id;}
}
