package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.PayGoProductViewModel;

public class LeaseSaleActivity extends AppCompatActivity {

    private MaterialButton scanSerialNumberViewBtn;
    private TextInputEditText serialNumberView;
    private MaterialButton leaseCustomerBtn;
    private TextInputEditText leaseSaleDescriptionView;
    private TextInputLayout leaseDescriptionWrapper;
    private TextInputLayout serialNumberTextWrapper;
    private MaterialButton makeLeaseSaleBtn;

    private Customer customerResult;
    private LeaseSaleModel leaseSaleModel;
    private PayGoProduct payGoProd;
    private PayGoProductViewModel payGoProductViewModel;
    private Boolean productExists;
    private LiveData<String> responseMessage;
    private ProgressDialog progressDialog;
    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private LiveData<Boolean> existsLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_sale);

        init();
        getLastLocation();

        scanSerialNumberViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (isCameraAvailable()) {
                Intent intent = new Intent(LeaseSaleActivity.this, BarCodeScanner.class);
                startActivityForResult(intent, Constants.ZBAR_SCANNER_REQUEST);
            } else {
                Toast.makeText(LeaseSaleActivity.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
            }
            }
        });

        leaseCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LeaseSaleActivity.this, SaleCustomerActivity.class);
                startActivityForResult(intent, Constants.SALE_CUST_CODE);
            }
        });

        makeLeaseSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateCustomerBtn() && validateDescriptionField() && validateSerialEditView()){

                    //getPayGoProduct(serialNumberView.getText().toString());

                    if(payGoProd == null) {
                        String sn = serialNumberView.getText().toString();

                        progressDialog.show();
                        getPayGoProduct(sn);
                        //saleReview();
                       /* productExists(sn);

                        if(productExists){
                            saleReview();
                        }else{
                            Toast.makeText(LeaseSaleActivity.this, "Product not registered by PANDASOLAR", Toast.LENGTH_SHORT).show();
                        }*/
                    }else{
                        saleReview();
                    }
                }
            }
        });

    }

    public void saleReview(){
        leaseSaleModel = new LeaseSaleModel();
        //leaseSaleModel.setAgentid(Utils.getSharedPreference(Constants.USER_ID));
        leaseSaleModel.setCordlat((float)latitude);
        leaseSaleModel.setCordlong((float)longitude);
        leaseSaleModel.setCustomerid(customerResult.getUserid());
        leaseSaleModel.setDeviceserial(serialNumberView.getText().toString());
        leaseSaleModel.setLeaseoffer(payGoProd.getLeaseOffer().getId());

        Intent intent = new Intent(LeaseSaleActivity.this, SaleReview.class);
        intent.putExtra(Constants.SALE_REVIEW, Constants.LEASE_SALE_REVIEW);
        intent.putExtra(Constants.SALE_CUSTOMER, customerResult);
        intent.putExtra(Constants.SALE_PRODCUT, payGoProd);
        intent.putExtra(Constants.LEASE_SALE_OBJ, leaseSaleModel);
        startActivity(intent);
    }

    public void getPayGoResponse(){
        payGoProductViewModel = ViewModelProviders.of(this).get(PayGoProductViewModel.class);
        responseMessage = payGoProductViewModel.getResponseMessage();

        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            progressDialog.dismiss();
            saleReview();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){

            LiveData<NetworkResponse> networkResponse = payGoProductViewModel.getNetworkResponse();

            networkResponse.observe(this, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LeaseSaleActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage(response.getBody());
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //finish();
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            progressDialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG, TRY AGAIN", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }
    public void getPayGoProduct(String scannedserial){
        LiveData<PayGoProduct> payGoProduct = payGoProductViewModel.getPayGoProduct(scannedserial);
        payGoProduct.observe(this, new Observer<PayGoProduct>() {
            @Override
            public void onChanged(@Nullable PayGoProduct payGoProduct) {
                payGoProd = payGoProduct;
                getPayGoResponse();
            }
        });

    }

   /* public boolean productExists(final String scannedSerial){

        final String serial = scannedSerial;

        existsLive = payGoProductViewModel.payGoProductIsAvailable(scannedSerial);
        existsLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                productExists = aBoolean;
                if(aBoolean == true){
                    getPayGoProduct(serial);
                }
            }
        });
        getPayGoResponse();
        return productExists;
    }*/


    public boolean validateCustomerBtn(){
        String text = leaseCustomerBtn.getText().toString();

        if(!text.equalsIgnoreCase("Add Customer")){
            leaseCustomerBtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            leaseCustomerBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
            leaseCustomerBtn.setIcon(getResources().getDrawable(R.drawable.ic_panda_add_person_add_yellow));
            leaseCustomerBtn.setAllCaps(false);
            return true;
        }else{
            leaseCustomerBtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            leaseCustomerBtn.setTextColor(getResources().getColor(R.color.dark_grey));
            leaseCustomerBtn.setIcon(getResources().getDrawable(R.drawable.ic_person_add_black_24dp));
            leaseCustomerBtn.setAllCaps(true);
            Toast.makeText(this, "Please Add a Customer", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateDescriptionField(){
        String text = leaseSaleDescriptionView.getText().toString();
        if(text.isEmpty()){
            leaseDescriptionWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            leaseSaleDescriptionView.setTextColor(getResources().getColor(R.color.dark_grey));
            Toast.makeText(this, "Please Enter Sale Description", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            leaseDescriptionWrapper.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
            leaseSaleDescriptionView.setTextColor(getResources().getColor(R.color.colorPrimary));
            return true;
        }
    }

    public boolean validateSerialEditView(){
        String text = serialNumberView.getText().toString();
        if(text.isEmpty()){
            serialNumberTextWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            serialNumberView.setTextColor(getResources().getColor(R.color.dark_grey));
            Toast.makeText(this, "Please Enter Product Serial", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            serialNumberTextWrapper.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
            serialNumberView.setTextColor(getResources().getColor(R.color.colorPrimary));
            return true;
        }
    }

    public void init(){

        payGoProductViewModel = ViewModelProviders.of(this).get(PayGoProductViewModel.class);

        scanSerialNumberViewBtn = findViewById(R.id.scanner_button);
        serialNumberView = findViewById(R.id.lease_sale_serialnumber);
        leaseSaleDescriptionView = findViewById(R.id.lease_sale_description);
        leaseCustomerBtn = findViewById(R.id.add_lease_customer_button);
        leaseDescriptionWrapper = findViewById(R.id.description_text_wrapper);
        serialNumberTextWrapper = findViewById(R.id.serialnumber_text_wrapper);
        makeLeaseSaleBtn = findViewById(R.id.submit_lease_sale_btn);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        progressDialog = Utils.customerProgressBar(this);

        productExists = true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constants.ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    String serialNumberResult = data.getStringExtra(Constants.SCAN_RESULT);
                    progressDialog.show();

                    if(!serialNumberResult.isEmpty()){
                        serialNumberView.setText(serialNumberResult);
                        progressDialog.dismiss();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(this, "Serial Number not captured, Try again", Toast.LENGTH_SHORT).show();
                    }

                    /*if(productExists(serialNumberResult)){
                        serialNumberView.setText(serialNumberResult);
                        validateSerialEditView();
                    }else{
                        Toast.makeText(this, "Product not registered by PANDASOLAR", Toast.LENGTH_SHORT).show();
                    }*/
                } else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case Constants.ZBAR_QR_SCANNER_REQUEST:

                break;

            case Constants.SALE_CUST_CODE:

                if(resultCode == RESULT_OK){
                    customerResult = data.getParcelableExtra(Constants.CUSTOMER_RESULT);
                    leaseCustomerBtn.setText(customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname());
                    validateCustomerBtn();
                    Toast.makeText(LeaseSaleActivity.this, customerResult.getUser().getFirstname(), Toast.LENGTH_SHORT).show();
                }
                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(LeaseSaleActivity.this, "No customer selected", Toast.LENGTH_SHORT).show();
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
