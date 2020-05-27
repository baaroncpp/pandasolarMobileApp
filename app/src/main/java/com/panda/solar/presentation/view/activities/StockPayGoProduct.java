package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.panda.solar.Model.constants.PayGoProductStatus;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.LeaseOfferViewModel;
import com.panda.solar.viewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class StockPayGoProduct extends AppCompatActivity {

    private MaterialButton scanBtn;
    private TextInputLayout serialNumberWrapper;
    private TextInputEditText serialNumber;
    private Spinner prodTypeSpinner;
    private MaterialButton stockBtn;
    private LeaseOfferViewModel leaseOfferViewModel;
    private LiveData<List<LeaseOffer>> leaseOfferLiveData;
    private List<LeaseOffer> resultLeaseOffers;
    private LiveData<String> responseMessageLiveData;
    private Intent resultIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_pay_go_product);

        init();
        setLeasableProducts();

        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCameraAvailable()) {
                    Intent intent = new Intent(StockPayGoProduct.this, BarCodeScanner.class);
                    startActivityForResult(intent, Constants.ZBAR_SCANNER_REQUEST);
                } else {
                    Toast.makeText(StockPayGoProduct.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        stockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateProduct() && validateSerialNumberView()){

                    PayGoProductModel payGoProductModel = new PayGoProductModel();
                    LeaseOffer leaseOffer = new LeaseOffer();

                    payGoProductModel.setPayGoProductStatus(PayGoProductStatus.AVAILABLE.name());
                    payGoProductModel.setTokenSerialNumber(serialNumber.getText().toString().trim());

                    for(LeaseOffer object : resultLeaseOffers){
                        if(prodTypeSpinner.getSelectedItem().toString().equals(object.getProduct().getName())){
                            payGoProductModel.setLeaseOfferid(object.getId());
                            leaseOffer = object;
                        }
                    }

                    Intent intent  = new Intent(StockPayGoProduct.this, StockProdReview.class);
                    intent.putExtra(Constants.PAYGO_MODEL, payGoProductModel);
                    intent.putExtra(Constants.LEASEOFFER_OBJECT, leaseOffer);

                    startActivity(intent);
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.ZBAR_SCANNER_REQUEST) {
            if (resultCode == RESULT_OK) {
                String serialNumberResult = data.getStringExtra(Constants.SCAN_RESULT);
                serialNumber.setText(serialNumberResult);
                Toast.makeText(this, serialNumberResult, Toast.LENGTH_SHORT).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void setLeasableProducts(){

        leaseOfferLiveData = leaseOfferViewModel.getAllLeaseOffers();
        leaseOfferLiveData.observe(this, new Observer<List<LeaseOffer>>() {
            @Override
            public void onChanged(@Nullable List<LeaseOffer> leaseOffers) {
                resultLeaseOffers.addAll(leaseOffers);
                initSpinner();
            }
        });
        observeProductReq();
    }

    public void init(){
        scanBtn = findViewById(R.id.paygo_scan_btn);
        serialNumberWrapper = findViewById(R.id.paygo_serialnumber_wrapper);
        serialNumber = findViewById(R.id.paygo_serialnumber);
        prodTypeSpinner = findViewById(R.id.paygo_producttype);
        stockBtn = findViewById(R.id.stock_paygo_btn);

        leaseOfferViewModel = ViewModelProviders.of(this).get(LeaseOfferViewModel.class);
        resultLeaseOffers = new ArrayList<>();
        resultIntent = new Intent(this, StockManagementActivity.class);
    }

    public void observeProductReq(){
        responseMessageLiveData = leaseOfferViewModel.getResponseMessage();

        responseMessageLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                if(s.equals(Constants.ERROR_RESPONSE)){
                    startActivity(resultIntent);
                    Toast.makeText(StockPayGoProduct.this, "CONNECTION ERROR, TRY AGAIN !!!", Toast.LENGTH_LONG).show();
                }else if(s.equals(Constants.FAILURE_RESPONSE)){
                    startActivity(resultIntent);
                    Toast.makeText(StockPayGoProduct.this, "NO INTERNET CONNECTION !!!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initSpinner() {
        List<String> leasebleProd = new ArrayList<>();
        leasebleProd.add("Choose Product");

        for(LeaseOffer object : resultLeaseOffers){
            leasebleProd.add(object.getProduct().getName());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, leasebleProd);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        prodTypeSpinner.setAdapter(arrayAdapter);
    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    public boolean validateProduct(){
        String inputText = prodTypeSpinner.getSelectedItem().toString().trim();

        if(inputText.equals("Choose Product")){
            Toast.makeText(this, "select product", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateSerialNumberView(){
        String inputText = serialNumber.getText().toString();

        if(inputText.isEmpty()){
            serialNumberWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            serialNumberWrapper.setErrorEnabled(true);
            serialNumberWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() != 15){
            serialNumberWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            serialNumberWrapper.setErrorEnabled(true);
            serialNumberWrapper.setError(Constants.VALID_SERIAL_INPUT);
            return false;
        }else{
            serialNumberWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            serialNumberWrapper.setErrorEnabled(false);
            return true;
        }
    }
}
