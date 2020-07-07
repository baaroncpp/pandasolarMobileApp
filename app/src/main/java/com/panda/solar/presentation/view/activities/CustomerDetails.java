package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.InternetConnection;
import com.panda.solar.viewModel.SaleViewModel;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerDetails extends AppCompatActivity {

    private TextView customerName;
    private TextView customerPhone;
    private TextView customerEmail;
    private TextView customerLocation;
    private TextView leaseSales;
    private TextView directSales;
    private MaterialButton fileBtn;
    private CircleImageView customerProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_details);

        initializeActivity();
         final Customer customer = getIntent().getExtras().getParcelable(Constants.CUSTOMER_OBJECT);

        customerName.setText(customer.getUser().getLastname()+", "+customer.getUser().getFirstname());
        customerPhone.setText(customer.getUser().getPrimaryphone());
        customerEmail.setText(customer.getUser().getEmail());
        customerLocation.setText(customer.getAddress());
        Picasso.with(this).load(customer.getProfilephotopath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(customerProfile);

        setSaleValues(customer.getUserid());

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetails.this, AddCustMeta.class);
                intent.putExtra(Constants.CUSTOMER_OBJECT, customer);
                startActivity(intent);
            }
        });
    }

    public void initializeActivity(){
        customerProfile = findViewById(R.id.profile_customer_details);
        customerName = findViewById(R.id.cust_detail_name);
        customerPhone = findViewById(R.id.cust_detail_phone);
        customerEmail = findViewById(R.id.cust_detail_email);
        customerLocation = findViewById(R.id.cust_detail_location);
        leaseSales = findViewById(R.id.customer_leasesales_sum);
        directSales = findViewById(R.id.customer_directsales_sum);
        fileBtn = findViewById(R.id.cust_files_btn);
    }

    public void setSaleValues(String customerId){
        SaleViewModel saleViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);
        LiveData<Map<String, Integer>> salesValues = saleViewModel.getCustomerSalesSum(customerId);

        salesValues.observe(this, new Observer<Map<String, Integer>>() {
            @Override
            public void onChanged(@Nullable Map<String, Integer> stringIntegerMap) {
                leaseSales.setText(stringIntegerMap.get("LEASE").toString()+" lease sales");
                directSales.setText(stringIntegerMap.get("DIRECT").toString()+" direct sales");
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!InternetConnection.checkConnection(this)){
            startActivity(new Intent(this, InternetError.class));
        }
    }

}
