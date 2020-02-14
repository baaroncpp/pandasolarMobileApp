package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.CustomerAdapter;
import com.panda.solar.viewModel.CustomerViewModel;

import java.util.List;

public class SaleCustomerActivity extends AppCompatActivity {

    private RecyclerView saleCustomerRecycler;
    private CustomerAdapter customerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LiveData<List<Customer>> customerList;
    private CustomerViewModel customerViewModel;
    private final static String CUSTOMER_RESULT = "customerResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_customer);

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerList = customerViewModel.getCustomers();

        customerList.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                buildRecyclerView(customers);
            }
        });
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
                resultIntent.putExtra(CUSTOMER_RESULT, customers.get(position));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}
