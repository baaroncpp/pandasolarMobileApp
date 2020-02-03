package com.panda.solar.presentation.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.panda.solar.presentation.view.activities.HomeActivity;
import com.panda.solar.activities.R;


public class CustomerLoginFragment extends Fragment implements View.OnClickListener {

    public CustomerLoginFragment() {
        // Required empty public constructor
    }

    private Button loginButton;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_customer_login, container, false);
        initViews(view);
        return view;
    }

    private void initViews(View v){

        loginButton = (Button)v.findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                startHomeActivity();
                break;
        }
    }

    private void startHomeActivity(){

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
