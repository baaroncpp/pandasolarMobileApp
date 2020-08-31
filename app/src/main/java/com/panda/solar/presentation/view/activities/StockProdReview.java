package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.PayGoProductModel;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.PayGoProductViewModel;

public class StockProdReview extends AppCompatActivity {

    private MaterialButton stockBtn;
    private TextView serialNumber;
    private TextView productName;
    private TextView leaseName;
    private PayGoProductViewModel payGoProductViewModel;
    private LiveData<String> responseMessageLiveData;
    private LiveData<PayGoProduct> payGoProductLiveData;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_prod_review);

        init();

        final PayGoProductModel payGoProductModel = getIntent().getParcelableExtra(Constants.PAYGO_MODEL);
        LeaseOffer leaseOffer = getIntent().getParcelableExtra(Constants.LEASEOFFER_OBJECT);

        if((payGoProductModel != null) && (leaseOffer != null)){
            serialNumber.setText(payGoProductModel.getTokenSerialNumber());
            productName.setText(leaseOffer.getProduct().getName());
            leaseName.setText(leaseOffer.getName());
        }else {
            finish();
            Toast.makeText(this,"SORRY TRY AGAIN LATER OR CONTACT YOUR ADMIN",Toast.LENGTH_LONG).show();
        }

        stockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();
                payGoProductLiveData = payGoProductViewModel.stockPayGoProduct(payGoProductModel);
                payGoProductLiveData.observe(StockProdReview.this, new Observer<PayGoProduct>() {
                    @Override
                    public void onChanged(@Nullable PayGoProduct payGoProduct) {
                       // observeResponse();
                    }
                });
            }
        });
        observeResponse();
    }

    public void observeResponse(){
        responseMessageLiveData = payGoProductViewModel.getResponseMessage();
        responseMessageLiveData.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            Intent intent = new Intent(StockProdReview.this, StockManagementActivity.class);
            startActivity(intent);
            dialog.dismiss();
            Toast.makeText(this,"Product successfully added to stock", Toast.LENGTH_LONG).show();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){

            LiveData<NetworkResponse> networkResponse = payGoProductViewModel.getNetworkResponse();

            networkResponse.observe(this, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(StockProdReview.this);
                    builder.setTitle("Error");
                    builder.setMessage(response.getBody());
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            dialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){
        stockBtn = findViewById(R.id.stock_paygo_prod);
        serialNumber = findViewById(R.id.stock_review_serial);
        productName = findViewById(R.id.stock_review_prodname);
        leaseName = findViewById(R.id.stock_review_lease);
        payGoProductViewModel = ViewModelProviders.of(this).get(PayGoProductViewModel.class);
        dialog = Utils.customerProgressBar(this);
    }
}
