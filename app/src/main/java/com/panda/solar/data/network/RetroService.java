package com.panda.solar.data.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroService {

    private static final String BASE_URL = "http://40.89.167.141:8906";

    public RetroService(){

    }

    private static Retrofit getRetroInstance(){
        return new Retrofit.Builder().baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static PandaCoreAPI getPandaCoreAPI(){
        return getRetroInstance().create(PandaCoreAPI.class);
    }
}
