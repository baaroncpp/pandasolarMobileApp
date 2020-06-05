package com.panda.solar.presentation.view.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;

import java.util.Date;

public class DirectSale extends AppCompatActivity {

    private MaterialButton productButtonView;
    private MaterialButton customerButtonView;
    private MaterialButton directSaleSubmitBtn;
    private TextInputEditText directSaledescription;
    private TextInputLayout directSaleDescriptionWrapper;

    private DirectSaleModel directSaleModel;
    private Customer customerResult;

    private String customerName;
    private Product productResult;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_sale);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();

        customerButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleCustomerActivity.class);
                startActivityForResult(intent, Constants.SALE_CUST_CODE);
            }
        });

        productButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleProductActivity.class);
                startActivityForResult(intent, Constants.SALE_PRO_CODE);
            }
        });

        directSaleSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleReview.class);
                if(validateProductBtn() && validateCustomerBtn() && validateDescriptionField()){

                    directSaleModel = new DirectSaleModel();
                    directSaleModel.setAgentid(""/*Utils.getSharedPreference(Constants.USER_ID)*/);
                    directSaleModel.setCreatedon(new Date());
                    directSaleModel.setScannedserial(productResult.getSerialNumber());
                    directSaleModel.setCustomerid(customerResult.getUserid());
                    directSaleModel.setDescription(directSaledescription.getText().toString());
                    directSaleModel.setLat((float)latitude);
                    directSaleModel.setLong_((float)longitude);
                    directSaleModel.setQuantity(1);

                    customerName = customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname();

                    intent.putExtra(Constants.CUSTOMER_NAME, customerName);
                    intent.putExtra(Constants.SALE_REVIEW, Constants.DIRECT_SALE_REVIEW);
                    intent.putExtra(Constants.DIRECT_SALE_OBJ, directSaleModel);
                    intent.putExtra(Constants.PROD_SALE_OBJ, productResult);
                    startActivity(intent);
                }
            }
        });
    }

    public void init(){

        customerButtonView = findViewById(R.id.add_customer_button);
        //locationButtonView = findViewById(R.id.set_location_button);
        directSaleSubmitBtn = findViewById(R.id.submit_direct_sale);
        directSaledescription = findViewById(R.id.direct_sale_description);
        directSaleDescriptionWrapper = findViewById(R.id.description_wrapper);
        productButtonView = findViewById(R.id.add_product_button);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }


    public boolean validateCustomerBtn(){
        String text = customerButtonView.getText().toString();

        if(!text.equalsIgnoreCase("Add Customer")){
            customerButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            customerButtonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            customerButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_add_person_add_yellow));
            customerButtonView.setAllCaps(false);
            return true;
        }else{
            customerButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            customerButtonView.setTextColor(getResources().getColor(R.color.dark_grey));
            customerButtonView.setIcon(getResources().getDrawable(R.drawable.ic_person_add_black_24dp));
            customerButtonView.setAllCaps(true);
            Toast.makeText(this, "Please Add a Customer", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    /*public boolean validateLocationBtn(){
        String text = locationButtonView.getText().toString();

        if(!text.equalsIgnoreCase("Set Location")){
            locationButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            locationButtonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            locationButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_location_yellow));
            locationButtonView.setAllCaps(false);
            return true;
        }else{
            locationButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            locationButtonView.setTextColor(getResources().getColor(R.color.dark_grey));
            locationButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_location_black));
            locationButtonView.setAllCaps(false);
            Toast.makeText(this, "Please Set The Customer Location", Toast.LENGTH_SHORT).show();
            return true;
        }
    }*/

    public boolean validateProductBtn(){
        String text = productButtonView.getText().toString();

        if(!text.equalsIgnoreCase("Add Product")){
            productButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            productButtonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            productButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_add_product_yellow));
            productButtonView.setAllCaps(false);
            return true;
        }else{
            productButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            productButtonView.setTextColor(getResources().getColor(R.color.dark_grey));
            productButtonView.setIcon(getResources().getDrawable(R.drawable.ic_add_product));
            productButtonView.setAllCaps(false);
            Toast.makeText(this, "Please Select a Product", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    public boolean validateDescriptionField(){
        String text = directSaledescription.getText().toString();
        if(text.isEmpty()){
            directSaleDescriptionWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            directSaledescription.setTextColor(getResources().getColor(R.color.dark_grey));
            Toast.makeText(this, "Please Enter Sale Description", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            directSaleDescriptionWrapper.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
            directSaledescription.setTextColor(getResources().getColor(R.color.colorPrimary));
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constants.ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    String serialNumberResult = data.getStringExtra(Constants.SCAN_RESULT);

                } else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case Constants.ZBAR_QR_SCANNER_REQUEST:

                break;

            case Constants.SALE_PRO_CODE:

                if(resultCode == RESULT_OK){
                    productResult = new Product();
                    productResult = data.getParcelableExtra(Constants.PRODUCT_RESULT);
                    productButtonView.setText(productResult.getName());
                    validateProductBtn();

                    Toast.makeText(DirectSale.this, productResult.getName(), Toast.LENGTH_SHORT).show();
                }
                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(DirectSale.this, "No product selected", Toast.LENGTH_SHORT).show();
                }
                break;

            case Constants.SALE_CUST_CODE:

                if(resultCode == RESULT_OK){
                    customerResult = data.getParcelableExtra(Constants.CUSTOMER_RESULT);
                    customerButtonView.setText(customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname());
                    validateCustomerBtn();
                    Toast.makeText(DirectSale.this, customerResult.getUser().getFirstname(), Toast.LENGTH_SHORT).show();
                }
                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(DirectSale.this, "No customer selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();

            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
        }
    };

    private void getLastLocation(){
        if (Utils.checkLocationPermissions(this)) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            Utils.requestLocationPermissions(this);
        }
    }

    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, locationCallback,
                Looper.myLooper()
        );

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
}
