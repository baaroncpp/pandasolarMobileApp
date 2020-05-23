package com.panda.solar.data.network;

import com.google.gson.JsonObject;
import com.panda.solar.Model.entities.CustList;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.Model.entities.PaymentList;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleList;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.Model.entities.StockProduct;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.Model.entities.User;
import com.panda.solar.Model.entities.Village;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by macosx on 02/05/2019.
 */

public interface PandaCoreAPI {

    @Headers("Content-Type: application/json")
    @POST("/token/get")
    Observable<NetworkResponse> login(@Body JsonObject body);

    @POST("/token/refresh")
    Call<Token> refreshJWT(@Body Token tokens);

    @POST("/token/get")
    Call<Token> bkLogin(@Body Login login);

    /*user endpoint*/
    @GET("v1/user/get/androiduser")
    Call<User> getUser();

    @GET("/v1/user/get")
    Call<User> getUserById(@Query("id") String id);

    @POST("v1/user/add")
    Call<User> addUser(@Body User user);

    @GET("")
    Call<List<User>> getUsers();

    /* customer endpoint*/
    @GET("v1/customermeta/get/all")
    Call<List<Customer>> getAllCustomers(@Query("page") int page,
                                   @Query("size")int size,
                                   @Query("sortby")String sortby,
                                   @Query("sortorder")String sortorder);

    @POST("/v1/customermeta/add/mobile")
    Call<CustomerMeta> addCustomer(@Body CustomerModel customer);


    /* product endpoint*/
    @GET("v1/product/get/")
    Call<Product> getProductById(@Query("id") String id);

    @GET("v1/product/get")
    Call<List<Product>> getAllProducts();

    @GET("/v1/product/get")
    Call<List<Product>> getAvailableProducts();

    @POST("v1/product/add")
    Call<Product> postProduct(@Body Product product);

    @GET("v1/product/get/{serial}")
    Call<Product> getProductBySerialNumnber(@Path("serial")String serialNumber);

    //payGoProduct
    @GET("v1/paygoproduct/get/{serial}")
    Call<PayGoProduct> getPayGoProduct(@Path("serial")String serialNumber);

    @GET("/v1/paygoproduct/isavailable/{serial}")
    Call<Boolean> payGoProductIsAvailable(@Path("serial")String serialNumber);

    @POST("v1/paygoproduct/stock/add")
    Call<PayGoProduct> stockPayGoProduct(@Body PayGoProductModel payGoProductModel);

    @GET("v1/paygoproduct/paygo/stock")
    Call<List<StockProduct>> getStockValues();

    /*sales endpoint*/
    @POST("v1/sales/add/direct")
    Call<Sale> makeDirectSale(@Body DirectSaleModel directSale);

    @POST("v1/sales/add/lease")
    Call<LeaseSale> makeLeaseSale(@Query("leaseoffer")int leaseoffer,
                                  @Query("agentid")String agentid,
                                  @Query("customerid")String customerid,
                                  @Query("cordlat")float cordlat,
                                  @Query("cordlong")float cordlong,
                                  @Query("deviceserial")String deviceserial);

    @GET("v1/sales/get/agent/{id}")
    Call<List<SaleModel>> getAgentSales(@Path("id")String id,
                                 @Query("page") int page,
                                 @Query("size") int size,
                                 @Query("sortby") String sortby,
                                 @Query("sortorder") String sortorder);

    @GET("v1/payments/get")
    Call<PaymentList> getAllLeasePayments(@Query("page") int page,
                                          @Query("size") int size,
                                          @Query("direction") String direction);

    @GET("v1/payments/get/{id}")
    Call<PaymentList> getAllAgentLeasePayments(@Path("id") String id,
                                               @Query("page") int page,
                                               @Query("size") int size,
                                               @Query("direction") String direction);

    @POST("v1/approvals/sale/approve/{id}")
    Call<Sale> approveSale(@Path("id") String id,
                           @Query("approvestatus")String approveStatus,
                           @Query("reviewdescription")String reviewDescription);

    @GET("v1/sales/get/allsales")
    Call<List<SaleModel>> getAllSales(@Query("page") int page,
                                      @Query("size") int size,
                                      @Query("sortby") String sortby,
                                      @Query("sortorder") String sortorder);

    @GET("v1/sales/agent/salesum/{id}")
    Call<Map<String, Integer>> agentSaleSum(@Path("id") String agentId);

    @GET("v1/sales/customer/salesum/{id}")
    Call<Map<String, Integer>> customerSaleSum(@Path("id") String customerId);


    /*Lease offer*/
    @GET("v1/leaseoffer/get")
    Call<List<LeaseOffer>> getLeaseOffers();

    /*file upload*/
    @GET("v1/uploadlink/{usertype}/{id}")
    Call<UploadLinks> getUploadLinks(@Path("usertype") String usertype, @Path("id") String id);

    @Multipart
    @POST("v1/customermeta/uploads/{id}")
    Call<FileResponse> uploadCustomerFile(@Path("id")String id, @Part MultipartBody.Part file, @Part("uploadType") RequestBody requestBody);

    @GET("v1/region/village/get/all")
    Call<List<Village>> getAllVillages();
}
