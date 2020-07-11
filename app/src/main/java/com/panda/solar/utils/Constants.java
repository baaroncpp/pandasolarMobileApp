package com.panda.solar.utils;

import retrofit2.http.PUT;

/**
 * Created by macosx on 27/01/2019.
 */

public class Constants {

    public static final String SALE_PRODCUT = "sale_product";
    public static final String DISTRICT_PLACE = "district_place";
    public static final String COUNTY_PLACE = "county_place";
    public static final String SUB_COUNTY_PLACE = "sub_county_place";
    public static final String VILLAGE_PLACE = "village_place";
    public static final String PLACE_NAME = "place_name";
    public static final String PLACE_BUNDLE = "place_bundle";

    public static final String ASSET_FINANCING = "asset_financing";
    public static final String DIRECT_SALE = "direct_sale";
    public static final String SALE_TYPE = "sale_type";

    public static final String VILLAGE_RESULT ="villageObjectResult";
    public final static String PRODUCT_RESULT = "saleProductResult";
    public final static String SCAN_RESULT = "barcodeScanResult";
    public final static String CUSTOMER_RESULT = "customerResult";
    public final static String CUSTOMER_NAME = "customer_name";
    public final static String DIRECT_SALE_OBJ = "direct_sale_object";
    public final static String PROD_SALE_OBJ = "product_sale_object";

    public static final String BAD_REQUEST = "BAD REQUEST";
    public final static String LEASE_SALE_OBJ = "lease_sale_object";
    public final static String LEASE_OFFER = "lease_offer";

    public final static String SALE_RESULT = "sale_result";
    public final static String SALE_OBJ = "sale_object";

    public final static String SALE_REVIEW = "sale_review";
    public final static String SALE_CUSTOMER = "sale_customer";

    public final static String LEASE_SALE_REVIEW = "lease_sale_review";
    public final static String DIRECT_SALE_REVIEW = "direct_sale_review";
    public final static String NON_PAYGO_SALE_REVIEW = "non_paygo_sale_review";


    public static final int ZBAR_SCANNER_REQUEST = 0;
    public final static int SALE_PRO_CODE = 1;
    public static final int ZBAR_QR_SCANNER_REQUEST = 2;
    public final static int SALE_CUST_CODE = 3;
    public static final int PRODUCT_LIST_SALE = 4;
    public static final int PRODUCT_LIST_DASH = 5;

    public static final String SHARED_PREF = "com.pandasolar.shared_pref_key";
    public static final String JWT_TOKEN = "jwt_token";
    public static final String USER_ID = "user_id";
    public static final String USER_TYPE = "user_type";

    public static final String ERROR_RESPONSE = "something wrong";
    public static final String SUCCESS_RESPONSE = "success";
    public static final String FAILURE_RESPONSE = "connection failure";

    public static final String CUSTOMER_PROFILE_PATH = "customer_profile_path";
    public static final String CUSTOMER_ID_PATH = "customer_id_path";
    public static final String CUSTOMER_COI_PATH = "customer_coi_path";
    public static final int PICK_PROFILE_IMAGE_REQUEST = 6;
    public static final int PICK_COI_IMAGE_REQUEST = 7;
    public static final int PICK_ID_IMAGE_REQUEST = 8;

    public static final String FIELD_REQUIRED = "Field required";
    public static final String SHORT_INPUT = "too short, at least 4 characters";
    public static final String VALID_SERIAL_INPUT = "serial number must have 15 characters";
    public static final String CUSTOMER_MODEL_OBJECT = "customerModel_object";
    public static final String CUSTOMER_OBJECT = "customer_object";
    public static final String PAYGO_MODEL = "paygo_product_object";
    public static final String LEASEOFFER_OBJECT= "lease_offer_object";
    public static final String CUSTOMER_ID = "customer_id";
    public static final String FCM_DEVICE_TOKEN = "firebase_device_token";
    public static final String DASH_PAYMENTS = "dash_payments";
    public static final String REPORT_PAYMENTS = "report_payments";

    public static final String BASE_URL = "http://40.89.167.141:8906";
    //public static final String BASE_URL = "http://10.42.0.1:8993";
    public static final int CUSTOMER_REVIEW_CODE = 9;
    public static final int WRITE_STORAGE_PERMISSION_CODE = 10;
    public static final int CAMERA_PERMISSION_CODE = 11;
    public static final int READ_STORAGE_PERMISSION_CODE = 12;
    public static final int LOCATION_PERMISSION_CODE = 13;
    public static final int VILLAGE_OBJECT_CODE = 14;
}

