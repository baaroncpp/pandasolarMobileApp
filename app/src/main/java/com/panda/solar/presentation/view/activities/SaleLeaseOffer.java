package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.LeaseOfferAdapter;
import com.panda.solar.viewModel.LeaseOfferViewModel;

import java.util.List;

public class SaleLeaseOffer extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LeaseOfferAdapter leaseOfferAdapter;
    private LiveData<List<LeaseOffer>> leaseOfferList;
    private LeaseOfferViewModel leaseOfferViewModel;
    private List<LeaseOffer> leaseOffers;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_lease_offer);

        leaseOfferViewModel = ViewModelProviders.of(this).get(LeaseOfferViewModel.class);
        leaseOfferList = leaseOfferViewModel.getAllLeaseOffers();

        leaseOfferList.observe(this, new Observer<List<LeaseOffer>>() {
            @Override
            public void onChanged(@Nullable List<LeaseOffer> leaseOffers) {
                buildRecycler(leaseOffers);
            }
        });
    }

    public void buildRecycler(List<LeaseOffer> leaseOffers) {

        recyclerView = findViewById(R.id.sale_lease_offer_recycler);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        leaseOfferAdapter = new LeaseOfferAdapter(leaseOffers);
        recyclerView.setAdapter(leaseOfferAdapter);
    }
}
