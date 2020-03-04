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
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.CustomerListRecyclerViewAdapter;
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
    private Toolbar toolbar;

    public static final String SHARED_PREF = "shared_pref";
    public static final String JWT_TOKEN = "jwt_token";

    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_list);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        dialog = new ProgressDialog(CustomerList.this);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        recyclerView = (RecyclerView)findViewById(R.id.customer_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        customerAdapter = new CustomerListRecyclerViewAdapter(this);
        // note
        /*instance of the view  Model*/
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);

        customerList = customerViewModel.getCustomers(0, 10, "createdon", "DESC");

        customerList.observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable List<Customer> customers) {
                onClickCustomers.addAll(customers);
                customerAdapter.setCustomers(customers);
                recyclerView.setAdapter(customerAdapter);
                dialog.dismiss();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.customer_search, menu);

        MenuItem menuItem = menu.findItem(R.id.search);

        //associating search configurations with the search view

       // SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView)menu.findItem(R.id.search).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                customerAdapter.getFilter().filter(newText);
                return false;
            }
        });

        //searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        return true;//super.onCreateOptionsMenu(menu);
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

    /*public String loadJWTData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREF, MODE_PRIVATE);
        jwtToken = sharedPreferences.getString(JWT_TOKEN,"");

        return jwtToken;
    }*/
}
