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
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.LeaseSale;
import com.panda.solar.Model.entities.LeaseSaleModel;
import com.panda.solar.Model.entities.PayGoProduct;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
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
    private TextView productNmaeTitle;
    private MaterialButton makeSaleBtn;
    private SaleViewModel saleViewModel;
    private LeaseSale leaseSaleResult;
    private ProgressDialog progressDialog;
    private Intent intent;
    private LiveData<Sale> saleLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_review);

        saleViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);

        progressDialog = Utils.customerProgressBar(this);

        init();
        String saleCode = getIntent().getStringExtra(Constants.SALE_REVIEW);

        if(saleCode.equals(Constants.LEASE_SALE_REVIEW)){
            intent = new Intent(this, LeaseSale.class);
            leaseSale();
        }else if(saleCode.equals(Constants.DIRECT_SALE_REVIEW)){
            intent = new Intent(this, LeaseSale.class);
            directSale();
        }else if(saleCode.equals(Constants.NON_PAYGO_SALE_REVIEW)){
            intent = new Intent(this, DirectSale.class);
            nonPayGoSale();
        }
    }

    public void leaseSale(){

        final LeaseSaleModel leaseSaleModel = getIntent().getExtras().getParcelable(Constants.LEASE_SALE_OBJ);
        Customer customer = getIntent().getExtras().getParcelable(Constants.SALE_CUSTOMER);
        PayGoProduct payGoProduct = getIntent().getExtras().getParcelable(Constants.SALE_PRODCUT);

        productNmaeTitle.setText("Lease Offer");
        saleTypeText.setText("Lease Sale");
        serialNumberText.setText(leaseSaleModel.getDeviceserial());
        productNameText.setText(payGoProduct.getLeaseOffer().getProduct().getName());
        customerNameText.setText(customer.getUser().getFirstname()+" "+customer.getUser().getLastname());
        //totalPriceText.setText(Utils.moneyFormatter(payGoProduct.getLeaseOffer().getProduct().getUnitcostselling()));

        // Get mark up on sale
        final float markup = payGoProduct.getLeaseOffer().getProduct().getUnitcostselling() * ((float) payGoProduct.getLeaseOffer().getPercentlease() / 100);
        float totalAmount = payGoProduct.getLeaseOffer().getProduct().getUnitcostselling() + markup;

        totalPriceText.setText(Utils.moneyFormatter(totalAmount));

        makeSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                LiveData<LeaseSale> leaseSaleLive = saleViewModel.makeLeaseSale(leaseSaleModel);

                leaseSaleLive.observe(SaleReview.this, new Observer<LeaseSale>() {
                    @Override
                    public void onChanged(@Nullable LeaseSale leaseSale) {
                        leaseSaleResult = leaseSale;
                        if(leaseSale != null){
                            //observeResponse();
                        }
                    }
                });
                observeResponse();
            }
        });
    }

    public void directSale(){

        String customerName = getIntent().getStringExtra(Constants.CUSTOMER_NAME);
        Product saleProduct = getIntent().getExtras().getParcelable(Constants.PROD_SALE_OBJ);
        final DirectSaleModel directSaleModel = getIntent().getExtras().getParcelable(Constants.DIRECT_SALE_OBJ);

        productNmaeTitle.setText("Product Name");
        saleTypeText.setText("Direct Sale");
        serialNumberText.setText(saleProduct.getSerialNumber());
        productNameText.setText(saleProduct.getName());
        customerNameText.setText(customerName);
        totalPriceText.setText(Utils.moneyFormatter(saleProduct.getUnitcostselling()));

        makeSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                saleLiveData = saleViewModel.makeDirectPayGoSale(directSaleModel);

                saleLiveData.observe(SaleReview.this, new Observer<Sale>() {
                    @Override
                    public void onChanged(@Nullable Sale sale) {
                        //observeResponse();
                    }
                });
                observeResponse();
            }
        });
    }

    public void nonPayGoSale(){

        String customerName = getIntent().getStringExtra(Constants.CUSTOMER_NAME);
        Product saleProduct = getIntent().getExtras().getParcelable(Constants.PROD_SALE_OBJ);
        final DirectSaleModel directSaleModel = getIntent().getExtras().getParcelable(Constants.DIRECT_SALE_OBJ);

        productNmaeTitle.setText("Product Name");
        saleTypeText.setText("Non PayGo Sale");
        serialNumberText.setText(saleProduct.getSerialNumber());
        productNameText.setText(saleProduct.getName());
        customerNameText.setText(customerName);
        totalPriceText.setText(Utils.moneyFormatter(saleProduct.getUnitcostselling()));

        makeSaleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                saleLiveData = saleViewModel.makeNonPayGoSale(directSaleModel);
                saleLiveData.observe(SaleReview.this, new Observer<Sale>() {
                    @Override
                    public void onChanged(@Nullable Sale sale) {

                    }
                });
                observeResponse();
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
        productNmaeTitle = findViewById(R.id.product_name_title);
    }

    public void observeResponse(){
        LiveData<String> liveResponseMsg = saleViewModel.getResponseMessage();
        liveResponseMsg.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String message){

        if(message.equals(Constants.SUCCESS_RESPONSE)){
            startActivity(new Intent(this, HomeActivity.class));
            progressDialog.dismiss();
            Toast.makeText(this,"SALE SUCCESSFULL", Toast.LENGTH_SHORT).show();
        }else if(message.equals(Constants.ERROR_RESPONSE)){

            LiveData<NetworkResponse> networkResponse = saleViewModel.getNetworkResponse();

            networkResponse.observe(this, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(SaleReview.this);
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

            progressDialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(message.equals(Constants.FAILURE_RESPONSE)){
            //startActivity(intent);
            progressDialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }
}
