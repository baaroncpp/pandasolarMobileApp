package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.SalesAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;

import java.util.List;

public class SalesList extends AppCompatActivity {

    private SalesAdapter salesAdapter;
    private LiveData<List<SaleModel>> liveSales;
    private SaleViewModel saleViewModel;
    private List<SaleModel> salesList;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerView;
    private TextView errorView;
    private ProgressDialog progressDialog;
    private LiveData<String> errorMsgLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        errorView = findViewById(R.id.sales_error_view);
        recyclerView = findViewById(R.id.sale_list_recycler);
        progressDialog = Utils.customerProgressBar(this);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String userType = Utils.getSharedPreference(Constants.USER_TYPE);

        saleViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);

        errorMsgLive = saleViewModel.getResponseMessage();
        errorMsgLive.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
        progressDialog.show();
        getSalesList(userType);
    }

    public void getSalesList(String userType){
        if(userType.equalsIgnoreCase("ADMIN")){

            liveSales = saleViewModel.getAllSales(0,10, "createdon", "DESC");

            liveSales.observe(this, new Observer<List<SaleModel>>() {
                @Override
                public void onChanged(@Nullable List<SaleModel> sales) {
                    buildRecycler(sales);
                }
            });

        }else{
            liveSales = saleViewModel.getAgentSales(Utils.getSharedPreference(Constants.USER_ID),0,10, "createdon", "DESC");

            liveSales.observe(this, new Observer<List<SaleModel>>() {
                @Override
                public void onChanged(@Nullable List<SaleModel> sales) {
                    buildRecycler(sales);
                }
            });
        }
    }

    public void buildRecycler(final List<SaleModel> sales){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        salesAdapter = new SalesAdapter(sales);
        recyclerView.setAdapter(salesAdapter);


        salesAdapter.setSaleOnClickListener(new SalesAdapter.SaleOnClickListener() {
            @Override
            public void onSaleItemClick(int position) {
                Intent intent = new Intent(SalesList.this, SaleDetail.class);
                intent.putExtra(Constants.SALE_OBJ, sales.get(position));
                intent.putExtra("saleDate", Utils.readableDate(sales.get(position).getCreatedon()));
                startActivity(intent);
            }
        });
    }

    public void handleResponse(String msg){

        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            progressDialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            progressDialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Items Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO CONNECTION");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }
}
