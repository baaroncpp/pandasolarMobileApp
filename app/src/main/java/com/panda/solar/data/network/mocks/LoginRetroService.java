package com.panda.solar.data.network.mocks;

import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.utils.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginRetroService {

    private static Retrofit getRetroInstance(){

        return new Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static PandaCoreAPI getPandaCoreAPI(){
        return getRetroInstance().create(PandaCoreAPI.class);
    }

}
