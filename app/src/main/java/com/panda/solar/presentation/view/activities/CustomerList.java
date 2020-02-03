package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.CustomerListRecyclerViewAdapter;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.viewModel.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerList extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private CustomerListRecyclerViewAdapter customerAdapter;
    private LiveData<List<Customer>> customerList;
    private CustomerViewModel customerViewModel;
    private List<Customer> onClickCustomers = new ArrayList<>();

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        recyclerView = (RecyclerView)findViewById(R.id.customer_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customerAdapter = new CustomerListRecyclerViewAdapter(this);
        // note
        /*instance of the view  Model*/
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerList = customerViewModel.getCustomers();

        customerList.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                onClickCustomers.addAll(customers);
                customerAdapter.setCustomers(customers);
                recyclerView.setAdapter(customerAdapter);
            }
        });

        customerAdapter.setOnCustomerListener(new CustomerListRecyclerViewAdapter.OnCustomerListener() {
            @Override
            public void onCustomerClick(Customer customer) {
                Toast.makeText(CustomerList.this,"aaron test", Toast.LENGTH_SHORT).show();

                //Intent intent = new Intent(CustomerList.this, CustomerDetails.class);
                //intent.putExtra("customerObject",customer);
                //startActivityForResult(intent, 1);
            }
        });

    }



    public void init(){
        customerList = customerViewModel.getCustomers();
    }

/*
    private LoaderManager.LoaderCallbacks<List<Customer>> loaderCallbacks = new LoaderManager.LoaderCallbacks<List<Customer>>() {
        @NonNull
        @Override
        public Loader<List<Customer>> onCreateLoader(int i, @Nullable Bundle bundle) {
            return new CustomerLoader(getApplicationContext());
        }

        @Override
        public void onLoadFinished(@NonNull Loader<List<Customer>> loader, List<Customer> customer) {

            customerAdapter = new CustomerListRecyclerViewAdapter(customer,this);
            customers = customer;
            recyclerView.setAdapter(customerAdapter);
        }

        @Override
        public void onLoaderReset(@NonNull Loader<List<Customer>> loader) {
            customerAdapter.setCustomers(Collections.<Customer>emptyList());
        }
    };
*/

    /*@Override
    public void onCustomerClick(int position) {

        Customer customer = onClickCustomers.get(position);

        Intent intent = new Intent(this, CustomerDetails.class);
        intent.putExtra("customerObject",customer);
        startActivity(intent);
    }*/
}
