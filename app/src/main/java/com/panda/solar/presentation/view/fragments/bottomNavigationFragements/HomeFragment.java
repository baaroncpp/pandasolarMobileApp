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
import android.widget.Button;

import com.panda.solar.Model.entities.AgentMeta;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.Model.entities.User;
import com.panda.solar.presentation.view.activities.AddCustomer;
import com.panda.solar.presentation.view.activities.CustomerList;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.DirectSale;
import com.panda.solar.presentation.view.activities.LeaseOfferList;
import com.panda.solar.presentation.view.activities.PaymentsList;
import com.panda.solar.presentation.view.activities.ProductListDashBoard;
import com.panda.solar.presentation.view.activities.SalesList;
import com.panda.solar.presentation.view.activities.StockManagementActivity;
import com.panda.solar.utils.NotificationsUtil;

public class HomeFragment extends Fragment {

    private CardView saleCard;
    private CardView productCard;
    private CardView stockCard;
    private CardView paymentsCard;
    private CardView customerCard;
    private CardView leaseOfferCard;
    private Button notify;

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
                Intent intent = new Intent(getActivity(), SalesList.class);
                startActivity(intent);
            }
        });

        stockCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getActivity(), StockManagementActivity.class);
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

        paymentsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentsList.class);
                startActivity(intent);
            }
        });

        notify = view.findViewById(R.id.notify_btn);


        User user = new User();
        user.setFirstname("Bukenya");
        user.setLastname("Aaron");

        AgentMeta agentMeta = new AgentMeta();
        agentMeta.setUser(user);

        final SaleModel saleModel = new SaleModel();

        saleModel.setAgent(agentMeta);
        saleModel.setSaletype("Lease");

        notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationsUtil.notifyOnNewSale(getActivity(), saleModel);
            }
        });

        return view;
    }

    private void initViews(View view){
        customerCard = view.findViewById(R.id.customer_card);
        saleCard = view.findViewById(R.id.sale_card);
        stockCard = view.findViewById(R.id.home_stock_card);
        leaseOfferCard = view.findViewById(R.id.lease_offer_card);
        productCard = view.findViewById(R.id.product_dashboard_card);
        paymentsCard = view.findViewById(R.id.lease_payments_card);
    }
}