package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;
import com.panda.solar.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private ProgressDialog dialog;
    private TextView username;
    private TextView location;
    private TextView email;
    private TextView phoneNumber;
    private TextView profile_status;
    private TextView directSales;
    private TextView leaseSales;
    private CircleImageView profileView;
    private TextView userType;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            //getActionBar().
        }

        init();

        LiveData<User> user = userViewModel.getAndroidUser();
        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                setProfileViews(user);
            }
        });

        observeResponse();
    }

    public void setProfileViews(User user){
        username.setText(user.getFirstname()+" "+user.getLastname());
        location.setText("Home");
        email.setText(user.getEmail());
        phoneNumber.setText("+"+Utils.insertCharacterForEveryNDistance(3, user.getPrimaryphone(), ' '));
        profile_status.setText(accountStatus(user.isIsactive()));
        userType.setText(user.getUsertype());

        Picasso.with(this).load(user.getProfilepath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(profileView);

        setSaleValues(user.getId());
    }

    public void init(){
        username = findViewById(R.id.user_name);
        location = findViewById(R.id.profile_location);
        email = findViewById(R.id.profile_email);
        phoneNumber = findViewById(R.id.profile_phone);
        profile_status = findViewById(R.id.profile_status);
        dialog = Utils.customerProgressBar(this);
        profileView = findViewById(R.id.user_profile_view);
        leaseSales = findViewById(R.id.agent_leasesale_sum);
        directSales = findViewById(R.id.agent_directsale_sum);
        userType = findViewById(R.id.user_type);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
    }

    public String accountStatus(boolean val){
        if(val){
            return "Active";
        }else{
            return "Not Active";
        }
    }

    public void setSaleValues(String agentId){
        SaleViewModel saleViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);
        LiveData<Map<String, Integer>> salesValues = saleViewModel.getAgentSalesSum(agentId);

        salesValues.observe(this, new Observer<Map<String, Integer>>() {
            @Override
            public void onChanged(@Nullable Map<String, Integer> stringIntegerMap) {
                leaseSales.setText(stringIntegerMap.get("LEASE").toString());
                directSales.setText(stringIntegerMap.get("DIRECT").toString());
            }
        });
    }

    public void observeResponse(){
        LiveData<String> responseMessage = userViewModel.getResponseMessage();
        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            dialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            startActivity(new Intent(this, HomeActivity.class));
            dialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

}
