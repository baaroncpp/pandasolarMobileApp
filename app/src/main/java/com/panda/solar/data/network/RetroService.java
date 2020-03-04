package com.panda.solar.data.network;

import android.content.SharedPreferences;
import android.widget.Toast;

import com.panda.solar.utils.AppContext;
import com.panda.solar.utils.Constants;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.Context.MODE_PRIVATE;

public class RetroService {

    private static final String BASE_URL = "http://10.42.0.1:8993";
    private static String token = getToken();

    private static Retrofit getRetroInstance(){

        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(new Interceptor() {

            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        return new Retrofit.Builder().baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static PandaCoreAPI getPandaCoreAPI(){
        return getRetroInstance().create(PandaCoreAPI.class);
    }

    public static String getToken(){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        Toast.makeText(AppContext.getAppContext(), sharedPreferences.getString(Constants.JWT_TOKEN, null), Toast.LENGTH_SHORT).show();
        return sharedPreferences.getString(Constants.JWT_TOKEN, null);
    }

}
