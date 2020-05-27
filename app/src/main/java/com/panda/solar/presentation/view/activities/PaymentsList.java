package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.app.ProgressDialog;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.PaymentsAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentsList extends AppCompatActivity {

    private PaymentsAdapter paymentsAdapter;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private PaymentViewModel paymentViewModel;
    private LiveData<String> responseMeassage;
    private LiveData<LeasePayment> liveLeasePayment;
    private LiveData<List<LeasePayment>> listPaymentLiveData;
    private ProgressDialog progressDialog;
    private TextView errorView;
    private List<LeasePayment> filteredPayment;
    private List<LeasePayment> payments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_list);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        payments = new ArrayList<>();
        filteredPayment = new ArrayList<>();
        errorView = findViewById(R.id.payment_error_view);
        recyclerView = findViewById(R.id.payments_recyclerview);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        progressDialog = Utils.customerProgressBar(this);

        responseMeassage = paymentViewModel.getResponseMessage();
        listPaymentLiveData = paymentViewModel.getAllLeasePayment(0, 100, "DESC");

        progressDialog.show();
        listPaymentLiveData.observe(this, new Observer<List<LeasePayment>>() {
            @Override
            public void onChanged(@Nullable List<LeasePayment> leasePayments) {
                payments.addAll(leasePayments);
                buildRecycler(leasePayments);
            }
        });

        responseMeassage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void buildRecycler(List<LeasePayment> leasePayments){


        if(!leasePayments.isEmpty()){
            //errorView.setVisibility(View.GONE);
            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            paymentsAdapter = new PaymentsAdapter(leasePayments);
            recyclerView.setAdapter(paymentsAdapter);

        }else{
            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Payments");
        }

    }

    public void handleResponse(String msg){

        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            progressDialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            progressDialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Payments Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("Connection Error");

            Toast.makeText(this,"Connection Error", Toast.LENGTH_SHORT).show();
        }
    }

    private void filter(String text){

        if(!filteredPayment.isEmpty()){
            filteredPayment.clear();
        }

        for(LeasePayment item : payments){
            if(item.getPayeename().toLowerCase().contains(text.toLowerCase()) || item.getPayeemobilenumber().toLowerCase().contains(text.toLowerCase())){
                filteredPayment.add(item);
            }
        }
        paymentsAdapter.filterList(filteredPayment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.payment_search, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search_payment).getActionView();

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
