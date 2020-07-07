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
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.SalesAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.InternetConnection;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;
import com.panda.solar.viewModel.UserViewModel;

import java.util.ArrayList;
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
    private String userType;
    private List<SaleModel> filteredSale;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_list);

        filteredSale = new ArrayList<>();
        salesList = new ArrayList<>();
        errorView = findViewById(R.id.sales_error_view);
        recyclerView = findViewById(R.id.sale_list_recycler);
        progressDialog = Utils.customerProgressBar(this);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        /*if(Utils.getSharedPreference(Constants.USER_TYPE).equals("")){
            getUserDetails();
        }*/

        //userType = Utils.getSharedPreference(Constants.USER_TYPE);

        saleViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);

        errorMsgLive = saleViewModel.getResponseMessage();
        errorMsgLive.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
        progressDialog.show();
        getSalesList();
    }

    public void getSalesList(){

        liveSales = saleViewModel.getAllSales(0,100, "createdon", "DESC");
        liveSales.observe(this, new Observer<List<SaleModel>>() {
            @Override
            public void onChanged(@Nullable List<SaleModel> sales) {
                salesList.addAll(sales);
                buildRecycler(sales);
            }
        });

    }

    public void buildRecycler(final List<SaleModel> sales){

        if(!sales.isEmpty()){

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            salesAdapter = new SalesAdapter(sales, this);
            recyclerView.setAdapter(salesAdapter);

            salesAdapter.setSaleOnClickListener(new SalesAdapter.SaleOnClickListener() {
                @Override
                public void onSaleItemClick(int position) {
                    Intent intent = new Intent(SalesList.this, SaleDetail.class);

                    if(!filteredSale.isEmpty()){
                        intent.putExtra(Constants.SALE_OBJ, filteredSale.get(position));
                        intent.putExtra("saleDate", Utils.readableDate(filteredSale.get(position).getCreatedon()));
                    }else{
                        intent.putExtra(Constants.SALE_OBJ, sales.get(position));
                        intent.putExtra("saleDate", Utils.readableDate(sales.get(position).getCreatedon()));
                    }

                    startActivity(intent);
                }
            });

        }else{
            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Sales");
        }

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
            errorView.setText("Connection Error");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

    private void filter(String text){

        if(!filteredSale.isEmpty()){
            filteredSale.clear();
        }

        for(SaleModel item : salesList){
            if(item.getCustomer().getUser().getFirstname().toLowerCase().contains(text.toLowerCase()) || item.getCustomer().getUser().getLastname().toLowerCase().contains(text.toLowerCase())){
                filteredSale.add(item);
            }
        }
        salesAdapter.filterList(filteredSale);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.sale_search, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search_sale).getActionView();

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

    public void getUserDetails(){

        UserViewModel uViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        LiveData<User> liveUser = uViewModel.getUser();
        liveUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Utils.saveUserDetails(user);
            }
        });

        LiveData<String> response2 = uViewModel.getResponseMessage();
        response2.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if(s.equals(Constants.ERROR_RESPONSE)){
                    finish();
                    Toast.makeText(SalesList.this,"BAD CREDENTIALS TRY AGAIN", Toast.LENGTH_LONG).show();
                }else if(s.equals(Constants.FAILURE_RESPONSE)){
                    finish();
                    Toast.makeText(SalesList.this,"CONNECTION FAILURE", Toast.LENGTH_LONG).show();
                }else{
                    finish();
                    Toast.makeText(SalesList.this,"TRY AGAIN LATER", Toast.LENGTH_LONG).show();
                }

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
}
