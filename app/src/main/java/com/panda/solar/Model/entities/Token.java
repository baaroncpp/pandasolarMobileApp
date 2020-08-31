package com.panda.solar.Model.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "token_table")
public class Token {

    @NonNull
    @PrimaryKey
    private String token;

    @Ignore
    public Token() {
        super();
    }

    public Token(String token) {

        this.token = token;
    }

    public String getToken() {
        return token;
    }

}
