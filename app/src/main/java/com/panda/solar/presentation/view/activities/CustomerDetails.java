package com.panda.solar.presentation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerDetails extends AppCompatActivity {

    private TextView customerName;
    private TextView customerPhone;
    private TextView customerEmail;
    private TextView customerLocation;
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

        fileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CustomerDetails.this, AddCustMeta.class);
                intent.putExtra(Constants.CUSTOMER_ID, customer.getUser().getId());
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
        fileBtn = findViewById(R.id.cust_files_btn);
    }

}
