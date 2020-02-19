package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.Response;

public class SaleReview extends AppCompatActivity {

    private TextView saleTypeText;
    private TextView serialNumberText;
    private TextView productNameText;
    private TextView customerNameText;
    private TextView totalPriceText;
    private MaterialButton makeSaleBtn;
    private SaleViewModel saleViewModel;
    private LiveData<Sale> saleLiveData;
    private ProgressDialog dialog;
    private SweetAlertDialog sweetAlertDialog;
    private Response saleResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_review);

        sweetAlertDialog = Utils.customSweetAlertDialog(this);
        dialog = Utils.customerProgressBar(SaleReview.this);

        init();

        String customerName = getIntent().getStringExtra(Constants.CUSTOMER_NAME);
        Product saleProduct = getIntent().getExtras().getParcelable(Constants.PROD_SALE_OBJ);
        final DirectSaleModel directSaleModel = getIntent().getExtras().getParcelable(Constants.DIRECT_SALE_OBJ);

        saleTypeText.setText("Direct Sale");
        serialNumberText.setText(directSaleModel.getScannedserial());
        productNameText.setText(saleProduct.getName());
        customerNameText.setText(customerName);
        totalPriceText.setText(String.valueOf(saleProduct.getUnitcostselling()));

        makeSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sweetAlertDialog.show();

                saleViewModel = ViewModelProviders.of(SaleReview.this).get(SaleViewModel.class);
                saleLiveData = saleViewModel.makeDirectPayGoSale(directSaleModel);

                saleResponse = SaleViewModel.getSaleResponse();

                saleLiveData.observe(SaleReview.this, new Observer<Sale>() {
                    @Override
                    public void onChanged(@Nullable Sale sale) {
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    }
                });

                if(saleResponse != null){
                    if(!saleResponse.isSuccessful()){
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                        sweetAlertDialog.setTitleText("Something Wrong");
                    }
                    sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                    sweetAlertDialog.setTitleText("Successful");
                }else{
                    sweetAlertDialog.changeAlertType(SweetAlertDialog.ERROR_TYPE);
                    sweetAlertDialog.setTitleText("CONNECTION FAILURE");
                }
            }
        });
    }

    public void init(){
        saleTypeText = findViewById(R.id.review_sale_type);
        serialNumberText = findViewById(R.id.review_serial_number);
        productNameText = findViewById(R.id.review_product_name);
        customerNameText = findViewById(R.id.review_customer);
        totalPriceText = findViewById(R.id.review_total_price);
        makeSaleBtn = findViewById(R.id.make_direct_sale);
    }
}
