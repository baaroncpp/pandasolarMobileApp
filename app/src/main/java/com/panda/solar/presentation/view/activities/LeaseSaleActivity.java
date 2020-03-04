package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.viewModel.PayGoProductViewModel;

public class LeaseSaleActivity extends AppCompatActivity {

    private MaterialButton scanSerialNumberViewBtn;
    private TextInputEditText serialNumberView;
    //private MaterialButton leaseOfferBtn;
    private MaterialButton setLocationBtn;
    private MaterialButton leaseCustomerBtn;
    private TextInputEditText leaseSaleDescriptionView;
    private TextInputLayout leaseDescriptionWrapper;
    private TextInputLayout serialNumberTextWrapper;
    private MaterialButton makeLeaseSaleBtn;

    private Customer customerResult;
    //private LeaseOffer leaseOffer;
    private LeaseSaleModel leaseSaleModel;
    private PayGoProduct payGoProd;
    private PayGoProductViewModel payGoProductViewModel;
    private Boolean productExists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_sale);

        init();

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
                if(validateCustomerBtn() && validateDescriptionField() && validateLocationBtn() && validateSerialEditView()){
                    leaseSaleModel = new LeaseSaleModel();
                    leaseSaleModel.setAgentid("fc179a74c902420bba3d16dfef1522af");
                    leaseSaleModel.setCordlat(56);
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
            }
        });
    }

    public void getPayGoProduct(String scannedserial){
        LiveData<PayGoProduct> payGoProduct = payGoProductViewModel.getPayGoProduct(scannedserial);
        payGoProduct.observe(this, new Observer<PayGoProduct>() {
            @Override
            public void onChanged(@Nullable PayGoProduct payGoProduct) {
                payGoProd = payGoProduct;
            }
        });
        //return payGoProd;
    }

    public Boolean productExists(final String scannedSerial){
        final String serial = scannedSerial;

        LiveData<Boolean> existsLive = payGoProductViewModel.payGoProductIsAvailable(scannedSerial);
        existsLive.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                productExists = aBoolean;
                if(aBoolean == true){
                    getPayGoProduct(serial);
                }
            }
        });
        return productExists;
    }

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

    public boolean validateLocationBtn(){
        String text = setLocationBtn.getText().toString();

        if(!text.equalsIgnoreCase("Set Location")){
            setLocationBtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            setLocationBtn.setTextColor(getResources().getColor(R.color.colorPrimary));
            setLocationBtn.setIcon(getResources().getDrawable(R.drawable.ic_panda_location_yellow));
            setLocationBtn.setAllCaps(false);
            return true;
        }else{
            setLocationBtn.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            setLocationBtn.setTextColor(getResources().getColor(R.color.dark_grey));
            setLocationBtn.setIcon(getResources().getDrawable(R.drawable.ic_panda_location_black));
            setLocationBtn.setAllCaps(false);
            Toast.makeText(this, "Please Set The Customer Location", Toast.LENGTH_SHORT).show();
            return true;
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

    public boolean validateProductToken(String tokenSerialNumber){
        return true;
    }

    public void init(){

        productExists = false;
        payGoProductViewModel = ViewModelProviders.of(this).get(PayGoProductViewModel.class);

        scanSerialNumberViewBtn = findViewById(R.id.scanner_button);
        serialNumberView = findViewById(R.id.lease_sale_serialnumber);
        //leaseOfferBtn = findViewById(R.id.add_lease_offer_button);
        setLocationBtn = findViewById(R.id.set_location_button);
        leaseSaleDescriptionView = findViewById(R.id.lease_sale_description);
        leaseCustomerBtn = findViewById(R.id.add_lease_customer_button);
        leaseDescriptionWrapper = findViewById(R.id.description_text_wrapper);
        serialNumberTextWrapper = findViewById(R.id.serialnumber_text_wrapper);
        makeLeaseSaleBtn = findViewById(R.id.submit_lease_sale_btn);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constants.ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    String serialNumberResult = data.getStringExtra(Constants.SCAN_RESULT);

                    if(productExists(serialNumberResult)){
                        serialNumberView.setText(serialNumberResult);
                        validateSerialEditView();
                    }else{
                        Toast.makeText(this, "Product not registered by PANDASOLAR", Toast.LENGTH_SHORT).show();
                    }
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
}
