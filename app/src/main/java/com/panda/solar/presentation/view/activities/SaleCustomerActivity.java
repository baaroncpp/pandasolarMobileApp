package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.CustomerAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;

import java.util.List;

public class SaleCustomerActivity extends AppCompatActivity {

    private RecyclerView saleCustomerRecycler;
    private CustomerAdapter customerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LiveData<List<Customer>> customerList;
    private CustomerViewModel customerViewModel;
    private LiveData<String> responseMessage;
    private TextView errorView;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_customer);

        progressDialog = Utils.customerProgressBar(this);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerList = customerViewModel.getCustomers(0, 10, "createdon", "DESC");

        progressDialog.show();
        customerList.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                buildRecyclerView(customers);
            }
        });
        observeResponse();
    }

    public void observeResponse(){
        responseMessage = customerViewModel.getResponseMessage();
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
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            progressDialog.dismiss();

            saleCustomerRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Items Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();

            saleCustomerRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO CONNECTION");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }else{
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO DATA AVAILABLE");
            saleCustomerRecycler.setVisibility(View.GONE);
        }
    }

    public void buildRecyclerView(final List<Customer> customers){

        saleCustomerRecycler = findViewById(R.id.sale_customer_recycler);
        layoutManager = new LinearLayoutManager(this);
        saleCustomerRecycler.setLayoutManager(layoutManager);

        customerAdapter = new CustomerAdapter(customers);
        saleCustomerRecycler.setAdapter(customerAdapter);

        customerAdapter.setOnCustomerClickListener(new CustomerAdapter.OnCustomerClickListener() {
            @Override
            public void onCustomerClick(int position) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(Constants.CUSTOMER_RESULT, customers.get(position));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
