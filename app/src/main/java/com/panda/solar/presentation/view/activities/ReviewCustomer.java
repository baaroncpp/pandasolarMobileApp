package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.constants.UserType;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;

public class ReviewCustomer extends AppCompatActivity {

    private TextView customerName;
    private TextView phoneNumber;
    private TextView email;
    private TextView address;
    private TextView idType;
    private TextView idNumber;
    private TextView secPhone;
    private TextView secEmail;
    private MaterialButton createCustomerBtn;

    private CustomerViewModel customerViewModel;
    private ProgressDialog dialog;
    private LiveData<String> responseMessage;
    private LiveData<CustomerMeta> liveCustomerMeta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_customer);

        init();

        final CustomerModel customerModel = getIntent().getExtras().getParcelable(Constants.CUSTOMER_MODEL_OBJECT);
        customerModel.setUsertype(UserType.CUSTOMER);
        customerModel.setVillageid((short)1);

        customerName.setText(customerModel.getFirstname()+" "+customerModel.getLastname()+" "+customerModel.getMiddlename());
        phoneNumber.setText(customerModel.getPrimaryphone());
        email.setText(customerModel.getEmail());
        address.setText(customerModel.getAddress());
        idType.setText(customerModel.getIdtype());
        idNumber.setText(customerModel.getIdnumber());
        secPhone.setText(customerModel.getSecondaryphone());
        secEmail.setText(customerModel.getSecondaryemail());

        createCustomerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.show();

                liveCustomerMeta = customerViewModel.addCustomerMeta(customerModel);

                liveCustomerMeta.observe(ReviewCustomer.this, new Observer<CustomerMeta>() {
                    @Override
                    public void onChanged(@Nullable CustomerMeta customerMeta) {

                    }
                });
            }
        });
        observeResponse();
    }

    public void init(){
        customerName = findViewById(R.id.customer_name_view);
        phoneNumber = findViewById(R.id.phone_number_view);
        email = findViewById(R.id.email_view);
        address = findViewById(R.id.customer_address);
        idType = findViewById(R.id.id_type_title);
        idNumber = findViewById(R.id.id_number_view);
        secPhone = findViewById(R.id.sec_number_view);
        secEmail = findViewById(R.id.sec_email_view);
        createCustomerBtn = findViewById(R.id.create_customer_btn);
        dialog = Utils.customerProgressBar(this);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
    }

    public void observeResponse(){
        responseMessage = customerViewModel.getResponseMessage();
        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            Intent intent = new Intent(ReviewCustomer.this, CustomerList.class);
            startActivity(intent);
            dialog.dismiss();
            Toast.makeText(this,"Customer successfully added", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){

            LiveData<NetworkResponse> networkResponse = customerViewModel.getNetworkResponse();

            networkResponse.observe(this, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ReviewCustomer.this);
                    builder.setTitle("Error");
                    builder.setMessage(response.getBody());
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });

            dialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }


}
