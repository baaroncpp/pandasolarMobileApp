package com.panda.solar.presentation.view.fragments.bottomNavigationFragements;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.panda.solar.Model.entities.AgentMeta;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.Model.entities.User;
import com.panda.solar.presentation.view.activities.AddCustomer;
import com.panda.solar.presentation.view.activities.CustomerList;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.DirectSale;
import com.panda.solar.presentation.view.activities.HomeActivity;
import com.panda.solar.presentation.view.activities.LeaseOfferList;
import com.panda.solar.presentation.view.activities.LoginActivity;
import com.panda.solar.presentation.view.activities.PaymentsList;
import com.panda.solar.presentation.view.activities.ProductListDashBoard;
import com.panda.solar.presentation.view.activities.SalesList;
import com.panda.solar.presentation.view.activities.SettingsActivity;
import com.panda.solar.presentation.view.activities.StockManagementActivity;
import com.panda.solar.utils.NotificationsUtil;
import com.panda.solar.utils.Utils;

public class HomeFragment extends Fragment {

    private CardView saleCard;
    private CardView productCard;
    private CardView stockCard;
    private CardView paymentsCard;
    private CardView customerCard;
    private CardView leaseOfferCard;
    private Context context;

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragement, container, false);

        context = getActivity();
        Toolbar toolbar = view.findViewById(R.id.home_toolbar);
        toolbar.inflateMenu(R.menu.appmenu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.appmenu_logout) {
                    Utils.logoutUtil(getActivity());

                    Intent intent = new Intent(getActivity(), LoginActivity.class);

                    // FLAG_ACTIVITY_CLEAR_TOP:- clears all activities stacked on top of the current activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().finish();
                }else if(item.getItemId() == R.id.appmenu_settings){
                    startActivity(new Intent(getActivity(), SettingsActivity.class));
                }
                return false;
            }
        });

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

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.appmenu, menu);//getMenuInflater().inflate(R.menu.appmenu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);

        /*if (item.getItemId() == R.id.appmenu_logout) {
            Utils.logoutUtil(getActivity(context));

            startActivity(new Intent(getActivity(), HomeActivity.class));
            getActivity().finish();
        }else if(item.getItemId() == R.id.appmenu_settings){
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }
        return true;*/
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