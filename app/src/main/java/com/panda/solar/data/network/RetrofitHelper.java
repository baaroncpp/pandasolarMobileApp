package com.panda.solar.data.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.panda.solar.utils.CallbackWrapper;
import com.panda.solar.utils.TLSSocketFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by macosx on 02/05/2019.
 */

public class RetrofitHelper  {


    private Retrofit retrofit;
    private Gson gson;


    public RetrofitHelper(){

        gson = new GsonBuilder().setPrettyPrinting().create();
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.client(getClient().build());
        builder.addConverterFactory(GsonConverterFactory.create(new Gson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        builder.baseUrl("http://40.89.167.141:8906");

        retrofit = builder.build();
    }


    public void login(JsonObject object, final NetworkCallback networkCallback) {


        retrofit.create(PandaCoreAPI.class).login(object)
                //.subscribeOn(Schedulers.io())
                //.observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new CallbackWrapper<NetworkResponse>(networkCallback) {
                    @Override
                    protected void onSuccess(NetworkResponse networkResponse) {
                        if (networkResponse.getCode() == HttpURLConnection.HTTP_OK) {
                            if(!isJson(networkResponse.getBody())){
                                networkResponse.setCode(400);
                                networkResponse.setBody("Failed");
                            }
                        }
                        else {
                            networkResponse.setBody("Failed");
                        }
                        networkCallback.onCallback(networkResponse);
                    }
                });


    }


    private static OkHttpClient.Builder getClient(){

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        try {
            X509TrustManager trustManager = getTrustManager();
            if (trustManager != null) {
                TLSSocketFactory socketFactory = new TLSSocketFactory();
                httpClient.sslSocketFactory(socketFactory, getTrustManager());
            }
        } catch (KeyManagementException | IllegalStateException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request request = chain.request();

                Request proceedingRequest = logRequestToLogEntries(request);

                Response response = chain.proceed(proceedingRequest);

                int code = response.code();
                String body = response.body().string();

                logResponseToLogEntries(response, body);

                JsonObject object = new JsonObject();
                object.addProperty("code", code);
                object.addProperty("body", body);

                return response.newBuilder().body(ResponseBody.create(MediaType.parse("application/json"), object.toString())).code(code).build();

            }
        });

        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.connectTimeout(60, TimeUnit.SECONDS);



        return  httpClient;
    }

    private static String bodyToString(final RequestBody request){
        try {
            final RequestBody copy = request;
            final Buffer buffer = new Buffer();
            if(copy != null)
                copy.writeTo(buffer);
            else
                return "";
            return buffer.readUtf8();
        }
        catch (final IOException e) {
            return "did not work";
        }
    }

    private static X509TrustManager getTrustManager() {
        TrustManagerFactory trustManagerFactory = null;
        try {
            trustManagerFactory =
                    TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
            if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
                //throw new IllegalStateException(
                //    "Unexpected default trust managers:" + Arrays.toString(trustManagers));
                return null;
            }
            return (X509TrustManager) trustManagers[0];
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        } catch (KeyStoreException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Request logRequestToLogEntries(Request request){


        String interceptedRequestBody = null;

        String url = request.url().toString();

        Request proceedingRequest = null;
        Headers interceptedRequestHeaders = request.headers();


        if(request.method().equals("POST")){
            interceptedRequestBody = bodyToString(request.body());

            MediaType mediaType = request.body().contentType();
            proceedingRequest = new Request.Builder().headers(interceptedRequestHeaders).url(url).post(RequestBody.create(mediaType, interceptedRequestBody)).build();
        }
        else if(request.method().equals("PUT")){
            interceptedRequestBody = bodyToString(request.body());

            MediaType mediaType = request.body().contentType();
            proceedingRequest = new Request.Builder().headers(interceptedRequestHeaders).url(url).put(RequestBody.create(mediaType, interceptedRequestBody)).build();
        }
        else{

            proceedingRequest = new Request.Builder().headers(interceptedRequestHeaders).url(url).get().build();
        }

        return proceedingRequest;
    }

    private static void logResponseToLogEntries(Response response, String body){

        Headers headers = response.headers();
        String url = response.request().url().toString();
        String method = response.request().method();
        int code = response.code();

        System.out.println(body);


    }

    private boolean isJson(String s){
        try{
            new JSONArray(s);
            return true;
        }catch (JSONException e){
            try {
                new JSONObject(s);
                return true;
            } catch (JSONException e1) {
                return false;
            }
        }
    }


}
