package com.panda.solar.presentation.view.fragments.bottomNavigationFragements;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.UserViewModel;

public class ProfileFragment extends Fragment {

    private LiveData<User> liveUser;
    private ProgressDialog dialog;
    private UserViewModel userViewModel;
    private TextView username;
    private TextView location;
    private TextView email;
    private TextView phoneNumber;
    private TextView profile_status;
    private TextView userType;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragement, container, false);
        super.onViewCreated(view, savedInstanceState);

        dialog = Utils.customerProgressBar(getActivity());
        init(view);

        dialog.show();
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);

        liveUser = userViewModel.getUser();

        liveUser.observe(getActivity(), new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                dialog.dismiss();
                setProfileViews(user);
            }
        });
        //onStart();
        return view;
    }

    public void setProfileViews(User user){
        username.setText(user.getFirstname()+" "+user.getLastname());
        location.setText("Home");
        email.setText(user.getEmail());
        phoneNumber.setText("+"+Utils.insertCharacterForEveryNDistance(3, user.getPrimaryphone(), ' '));
        profile_status.setText(accountStatus(user.isIsactive()));

    }

    public void init(View view){
        username = view.findViewById(R.id.user_name);
        location = (TextView)view.findViewById(R.id.profile_location);
        email = (TextView)view.findViewById(R.id.profile_email);
        phoneNumber = (TextView)view.findViewById(R.id.profile_phone);
        profile_status = (TextView)view.findViewById(R.id.profile_status);
        //userType = (TextView)view.findViewById(R.id.pr
    }

    public String accountStatus(boolean val){
        if(val){
            return "Active";
        }else{
            return "Not Active";
        }

    }
}
