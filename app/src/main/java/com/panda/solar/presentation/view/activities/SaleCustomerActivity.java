package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.CustomerAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.InternetConnection;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;

import java.util.ArrayList;
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
    //private TextInputEditText customerSearch;
    private ArrayList<Customer> filteredCustomer;
    private List<Customer> customers2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_customer);

        saleCustomerRecycler = findViewById(R.id.sale_customer_recycler);
        errorView = findViewById(R.id.sale_customer_error_view);
        filteredCustomer = new ArrayList<>();
        customers2 = new ArrayList<>();
        //customerSearch = findViewById(R.id.search_cust_sale);
        progressDialog = Utils.customerProgressBar(this);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        customerList = customerViewModel.getCustomers(0, 10, "createdon", "DESC");

        progressDialog.show();
        customerList.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                customers2.addAll(customers);
                buildRecyclerView(customers);
            }
        });
        observeResponse();

       /* customerSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });*/
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
        }/*else{
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO DATA AVAILABLE");
            saleCustomerRecycler.setVisibility(View.GONE);
        }*/
    }

    private void filter(String text){
        ArrayList<Customer> filteredList = new ArrayList<>();

        if(!filteredCustomer.isEmpty()){
            filteredCustomer.clear();
        }

        for(Customer item : customers2){
            if(item.getUser().getLastname().toLowerCase().contains(text.toLowerCase()) || item.getUser().getFirstname().toLowerCase().contains(text.toLowerCase())){
                filteredCustomer.add(item);
                filteredList.add(item);
            }
        }
        customerAdapter.filterList(filteredList);
    }

    public void buildRecyclerView(final List<Customer> customers){

        //saleCustomerRecycler = findViewById(R.id.sale_customer_recycler);
        layoutManager = new LinearLayoutManager(this);
        saleCustomerRecycler.setLayoutManager(layoutManager);

        customerAdapter = new CustomerAdapter(customers);
        saleCustomerRecycler.setAdapter(customerAdapter);

        customerAdapter.setOnCustomerClickListener(new CustomerAdapter.OnCustomerClickListener() {
            @Override
            public void onCustomerClick(int position) {
                Intent resultIntent = new Intent();

                if(!filteredCustomer.isEmpty()){
                    resultIntent.putExtra(Constants.CUSTOMER_RESULT, filteredCustomer.get(position));
                }else{
                    resultIntent.putExtra(Constants.CUSTOMER_RESULT, customers.get(position));
                }

                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!InternetConnection.checkConnection(this)){
            startActivity(new Intent(this, InternetError.class));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sale_customer_search, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search_customer_sale).getActionView();

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
}
