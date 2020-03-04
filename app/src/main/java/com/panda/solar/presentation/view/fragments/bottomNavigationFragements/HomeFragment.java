package com.panda.solar.presentation.view.fragments.bottomNavigationFragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panda.solar.presentation.view.activities.AddCustomer;
import com.panda.solar.presentation.view.activities.CustomerList;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.DirectSale;
import com.panda.solar.presentation.view.activities.LeaseOfferList;
import com.panda.solar.presentation.view.activities.ProductListDashBoard;

public class HomeFragment extends Fragment {

    private CardView saleCard;
    private CardView productCard;
    private CardView installationCard;
    //private CardView repairCard;
    private CardView customerCard;
    private CardView leaseOfferCard;

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragement, container, false);

        initViews(view);

        customerCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), CustomerList.class);
                startActivity(intent);
            }
        });

        saleCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                SalesFragment salesFragment = new SalesFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, salesFragment, salesFragment.getTag()).commit();
            }
        });

        installationCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), AddCustomer.class);
                startActivity(intent);
            }
        });

        leaseOfferCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), LeaseOfferList.class);
                startActivity(intent);
            }
        });

        productCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductListDashBoard.class);
                startActivity(intent);
            }
        });
        return view;
    }

    private void initViews(View view){
        customerCard = (CardView)view.findViewById(R.id.customer_card);
        saleCard = (CardView)view.findViewById(R.id.sale_card);
        installationCard = (CardView)view.findViewById(R.id.installation_card);
        leaseOfferCard = (CardView)view.findViewById(R.id.lease_offer_card);
        productCard = view.findViewById(R.id.product_dashboard_card);
    }
}