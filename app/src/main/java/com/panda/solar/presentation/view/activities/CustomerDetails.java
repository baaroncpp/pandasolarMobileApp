package com.panda.solar.presentation.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;

public class CustomerDetails extends AppCompatActivity {

    TextView customerName;
    TextView customerPhone;
    TextView customerEmail;
    TextView customerLocation;
    Customer customer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_details);
        //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        customer = getIntent().getParcelableExtra("customerObject");

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

       // initializeActivity(customer);
    }

    public void initializeActivity(Customer customer){
        customerName = (TextView)findViewById(R.id.customer_name);
        customerPhone = (TextView)findViewById(R.id.cutomer_phone);
        customerEmail = (TextView)findViewById(R.id.cutomer_email);
        customerLocation = (TextView)findViewById(R.id.cutomer_location);

        customerName.setText(customer.getUser().getLastname()+", "+customer.getUser().getFirstname());
        customerPhone.setText(customer.getUser().getPrimaryphone());
        customerEmail.setText(customer.getUser().getEmail());
        customerLocation.setText(customer.getVillageid());

    }

}
