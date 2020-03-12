package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.ProductAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.ProductViewModel;
import java.util.List;

public class ProductListDashBoard extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private RecyclerView productRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private LiveData<List<Product>> productList;
    private ProductViewModel productViewModel;
    private TextView errorView;
    private LiveData<String> responseMsgLive;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_dash_board);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        productRecycler = findViewById(R.id.product_recyclerview_dash);
        progressDialog = Utils.customerProgressBar(this);
        errorView = findViewById(R.id.product_error_view);
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productList = productViewModel.getProducts();

        progressDialog.show();
        productList.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                buildRecyclerView(products);
            }
        });
        observeResponse();
    }

    public void observeResponse(){
        responseMsgLive = productViewModel.getResponseMessage();
        responseMsgLive.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            progressDialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            progressDialog.dismiss();

            productRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Items Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();

            productRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO CONNECTION");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

    public  void buildRecyclerView(List<Product> products){

        layoutManager = new LinearLayoutManager(this);
        productRecycler.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(products, Constants.PRODUCT_LIST_DASH);
        productRecycler.setAdapter(productAdapter);
    }
}