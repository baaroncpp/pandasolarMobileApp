package com.panda.solar.data.network;

import com.google.gson.JsonObject;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.Model.entities.Token;

import java.util.List;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by macosx on 02/05/2019.
 */

public interface PandaCoreAPI {


    @Headers("Content-Type: application/json")
    @POST("/token/get")
    Observable<NetworkResponse> login(@Body JsonObject body);

    @GET("/token/refresh")
    Call<Token> refreshJWT(@Header("Bearer ") @Body Token token);

    @GET("/token/get")
    Call<Token> bkLogin(@Body Login login);


    /* customer endpoint*/

    @GET("/customer/")
    Call<List<Customer>> getCustomers();

    @POST("")
    Call<Customer> getCustomer(@Body Customer customer);


    /* product endpoint*/

    @GET("/v1/product/get")
    Call<List<Product>> getAllProducts();

    @GET("/v1/product/get")
    Call<List<Product>> getAvailableProducts();

    @POST("/v1/product/add")
    Call<Product> postProduct(@Body Product product);

}
