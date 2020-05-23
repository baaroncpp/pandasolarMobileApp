package com.panda.solar.presentation.view.fragments.bottomNavigationFragements;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.HomeActivity;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;
import com.panda.solar.viewModel.UserViewModel;
import com.squareup.picasso.Picasso;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {

    private LiveData<User> liveUser;
    private ProgressDialog dialog;
    private UserViewModel userViewModel;
    private TextView username;
    private TextView location;
    private TextView email;
    private TextView phoneNumber;
    private TextView profile_status;
    private TextView directSales;
    private TextView leaseSales;
    private CircleImageView profileView;
    private TextView userType;
    private LiveData<String> responseMessage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragement, container, false);
        super.onViewCreated(view, savedInstanceState);

        init(view);

        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        liveUser = userViewModel.getUser();
        dialog.show();
        liveUser.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                setProfileViews(user);
            }
        });
        observeResponse();
        return view;
    }

    public void setProfileViews(User user){
        username.setText(user.getFirstname()+" "+user.getLastname());
        location.setText("Home");
        email.setText(user.getEmail());
        phoneNumber.setText("+"+Utils.insertCharacterForEveryNDistance(3, user.getPrimaryphone(), ' '));
        profile_status.setText(accountStatus(user.isIsactive()));

        Picasso.with(getActivity()).load(user.getProfilepath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(profileView);

        setSaleValues(user.getId());
    }

    public void init(View view){
        username = view.findViewById(R.id.user_name);
        location = view.findViewById(R.id.profile_location);
        email = view.findViewById(R.id.profile_email);
        phoneNumber = view.findViewById(R.id.profile_phone);
        profile_status = view.findViewById(R.id.profile_status);
        dialog = Utils.customerProgressBar(getActivity());
        profileView = view.findViewById(R.id.user_profile_view);
        leaseSales = view.findViewById(R.id.agent_leasesale_sum);
        directSales = view.findViewById(R.id.agent_directsale_sum);
    }

    public void observeResponse(){
        responseMessage = userViewModel.getResponseMessage();
        responseMessage.observe(getActivity(), new Observer<String>() {
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
            Toast.makeText(getActivity(),"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(getActivity(),"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
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
}
