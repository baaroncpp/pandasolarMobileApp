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

import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.LeaseOfferAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.InternetConnection;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.LeaseOfferViewModel;

import java.util.List;

public class LeaseOfferList extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaseOfferAdapter leaseOfferAdapter;
    private LiveData<List<LeaseOffer>> leaseOfferList;
    private LeaseOfferViewModel leaseOfferViewModel;
    private List<LeaseOffer> leaseOffers;
    private RecyclerView.LayoutManager layoutManager;
    private TextView errorView;
    private ProgressDialog progressDialog;
    private LiveData<String> responseMsgLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_offer_list);

        progressDialog = Utils.customerProgressBar(this);
        errorView = findViewById(R.id.leaseoffer_error_view);
        recyclerView = findViewById(R.id.leaseoffer_recycler);
        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        recyclerView = findViewById(R.id.leaseoffer_recycler);

        leaseOfferViewModel = ViewModelProviders.of(this).get(LeaseOfferViewModel.class);
        leaseOfferList = leaseOfferViewModel.getAllLeaseOffers();

        progressDialog.show();
        leaseOfferList.observe(this, new Observer<List<LeaseOffer>>() {
            @Override
            public void onChanged(@Nullable List<LeaseOffer> leaseOffers) {
                buildRecycler(leaseOffers);
            }
        });
        observeResponse();
    }

    public void observeResponse(){
        responseMsgLive = leaseOfferViewModel.getResponseMessage();
        responseMsgLive.observe(this, new Observer<String>() {
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

    public void buildRecycler(List<LeaseOffer> leaseOfferList){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        leaseOfferAdapter = new LeaseOfferAdapter(leaseOfferList);
        recyclerView.setAdapter(leaseOfferAdapter);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!InternetConnection.checkConnection(this)){
            startActivity(new Intent(this, InternetError.class));
        }
    }
}
