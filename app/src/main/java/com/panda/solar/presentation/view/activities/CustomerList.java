package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.CustomerListRecyclerViewAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;

public class CustomerList extends AppCompatActivity {

    private RecyclerView recyclerView ;
    private CustomerListRecyclerViewAdapter customerAdapter;
    private LiveData<List<Customer>> customerList;
    private CustomerViewModel customerViewModel;
    private List<Customer> onClickCustomers = new ArrayList<>();
    private ProgressDialog dialog;
    private TextView errorView;
    private LiveData<String> responseMsgLive;
    private FloatingActionButton registerCustomer;
    private ArrayList<Customer> filteredCustomer;
    private List<Customer> customers2;

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerList = customerViewModel.getCustomers(0, 100, "createdon", "DESC");

        dialog.show();
        customerList.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                customers2.addAll(customers);
                buildRecycler(customers);
            }
        });
        observeResponse();

        registerCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(CustomerList.this, AddCustomer.class);
                startActivity(intent);
            }
        });

    }

    public void init(){
        registerCustomer = findViewById(R.id.register_customer_btn);
        dialog = Utils.customerProgressBar(this);
        errorView = findViewById(R.id.customer_error_view);
        recyclerView = findViewById(R.id.customer_list_recycler);
        filteredCustomer = new ArrayList<>();
        customers2 = new ArrayList<>();
    }

    public void buildRecycler(final List<Customer> customers){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        customerAdapter = new CustomerListRecyclerViewAdapter(customers,this);
        onClickCustomers.addAll(customers);
        recyclerView.setAdapter(customerAdapter);

        customerAdapter.setOnCustomerClickListener(new CustomerListRecyclerViewAdapter.OnCustomerClickListener() {
            @Override
            public void onCustomerClick(int position) {

                Customer customer = customers.get(position);
                Intent intent = new Intent(CustomerList.this, CustomerDetails.class);

                if(!filteredCustomer.isEmpty()){
                    intent.putExtra(Constants.CUSTOMER_OBJECT,filteredCustomer.get(position));
                }else{
                    intent.putExtra(Constants.CUSTOMER_OBJECT,customer);
                }

                startActivity(intent);
            }
        });
    }

    public void observeResponse(){
        responseMsgLive = customerViewModel.getResponseMessage();
        responseMsgLive.observe(this, new Observer<String>() {
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

    private void filter(String text){

        if(!filteredCustomer.isEmpty()){
            filteredCustomer.clear();
        }

        for(Customer item : customers2){
            if(item.getUser().getLastname().toLowerCase().contains(text.toLowerCase()) || item.getUser().getFirstname().toLowerCase().contains(text.toLowerCase())){
                filteredCustomer.add(item);
            }
        }
        customerAdapter.filterList(filteredCustomer);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_search, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int menuOtemThatWasSelected = item.getItemId();

        if(menuOtemThatWasSelected == 1){
            Context context = CustomerList.this;
            Toast.makeText(context, "Search clicked", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

}
