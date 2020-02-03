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

public class HomeFragment extends Fragment {

    private CardView saleCard;
    private CardView stockManagementCard;
    private CardView installationCard;
    private CardView repairCard;
    private CardView customerCard;

    public HomeFragment() { }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.home_fragement, container, false);

        //initViews(view);
        customerCard = (CardView)view.findViewById(R.id.customer_card);
        saleCard = (CardView)view.findViewById(R.id.sale_card);
        installationCard = (CardView)view.findViewById(R.id.installation_card);

        customerCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
               /*CustomerList customerList = new CustomerList();
               FragmentManager manager = getFragmentManager();
               manager.beginTransaction().replace(R.id.fragment_container, customerList, customerList.getTag()).commit();
*/
                Intent intent = new Intent(getActivity(), CustomerList.class);
                startActivity(intent);
            }
        });

        saleCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(getActivity(), "customers", Toast.LENGTH_SHORT).show();
                SalesFragment salesFragment = new SalesFragment();
                FragmentManager manager = getFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container, salesFragment, salesFragment.getTag()).commit();
            }
        });

        installationCard.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(getActivity(), "customers", Toast.LENGTH_SHORT).show();
                //SalesFragment salesFragment = new SalesFragment();
                //FragmentManager manager = getFragmentManager();
                //manager.beginTransaction().replace(R.id.fragment_container, salesFragment, salesFragment.getTag()).commit();

                Intent intent = new Intent(getActivity(), AddCustomer.class);
                startActivity(intent);
            }
        });


        return view;
    }


    private void initViews(View view){
        saleCard = (CardView)view.findViewById(R.id.sale);
        stockManagementCard = (CardView)view.findViewById(R.id.stock_management_card);
        installationCard = (CardView)view.findViewById(R.id.installation_card);
        repairCard = (CardView)view.findViewById(R.id.repair_card);
        customerCard = (CardView)view.findViewById(R.id.customer_card);

    }


}
