package com.panda.solar.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.panda.solar.presentation.view.activities.InstallationActivity;
import com.panda.solar.activities.R;


public class InstallationSearchCustomerFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private Button searchCustomerButton;
    private EditText installationCustomerSearchPhoneNumber;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_installation_search_customer, container, false);

        searchCustomerButton = (Button)view.findViewById(R.id.installation_search_customer_button);
        installationCustomerSearchPhoneNumber = (EditText)view.findViewById(R.id.installation_customer_phone_number);
        installationCustomerSearchPhoneNumber.addTextChangedListener(this);

        searchCustomerButton.setOnClickListener(this);
        return view;
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
    public void onResume() {
        super.onResume();
        if(getActivity() != null && ((InstallationActivity)getActivity()).getSupportActionBar() != null)
            ((InstallationActivity) getActivity()).getSupportActionBar().setTitle(R.string.customer);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.installation_search_customer_button:
                if((InstallationActivity)getActivity() != null){
                    // do search here
                    ((InstallationActivity)getActivity()).getViewPager().setCurrentItem(1);
                }

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.hashCode() == installationCustomerSearchPhoneNumber.getText().hashCode()){

        }
    }
}
