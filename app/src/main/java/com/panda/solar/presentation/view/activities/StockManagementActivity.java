package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.StockProduct;
import com.panda.solar.activities.R;

import com.panda.solar.presentation.adapters.StockAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.PayGoProductViewModel;

import java.util.List;

public class StockManagementActivity extends AppCompatActivity {

    private FloatingActionButton stockFabBtn;
    private StockAdapter stockAdapter;
    private PayGoProductViewModel payGoProductViewModel;
    private LiveData<String> responseMessage;
    private RecyclerView recyclerView;
    private LiveData<List<StockProduct>> liveDataStockProduct;
    private ProgressDialog dialog;
    private TextView errorView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_management);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        init();


        dialog.show();
        liveDataStockProduct = payGoProductViewModel.getStockValues();
        liveDataStockProduct.observe(this, new Observer<List<StockProduct>>() {
            @Override
            public void onChanged(@Nullable List<StockProduct> stockProducts) {
                buildRecycler(stockProducts);
            }
        });

        observeResponse();

        stockFabBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockManagementActivity.this, StockPayGoProduct.class);
                startActivity(intent);
            }
        });
    }

    public void observeResponse(){
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
            dialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            dialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Items Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO CONNECTION");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

    public void buildRecycler(List<StockProduct> stockProducts){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        stockAdapter = new StockAdapter(stockProducts);
        recyclerView.setAdapter(stockAdapter);
    }

    public void init(){
        stockFabBtn = findViewById(R.id.stock_fab_btn);
        payGoProductViewModel = ViewModelProviders.of(this).get(PayGoProductViewModel.class);
        errorView = findViewById(R.id.stock_error_view);
        dialog = Utils.customerProgressBar(this);
        recyclerView = findViewById(R.id.stock_items_recycler);
    }

}
