package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.InternetConnection;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.UserViewModel;

public class ChangePassword extends AppCompatActivity {

    private UserViewModel userViewModel;
    private LiveData<String> responseMessage;

    private TextInputEditText currentPassword;
    private TextInputEditText newPassword;
    private TextInputEditText confirmPassword;
    private MaterialButton updateBtn;

    private TextInputLayout currentWrapper;
    private TextInputLayout newWrapper;
    private TextInputLayout confirmWrapper;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateCurrentPassword() && validateNewPassword() && validateConfirmPassword()){

                    String currentPass = currentPassword.getText().toString();
                    String newPass = newPassword.getText().toString();
                    String confirmPass = confirmPassword.getText().toString();

                    if(newPass.equals(confirmPass)){
                        updatePassword(currentPass, confirmPass);
                    }else{
                        dialog.dismiss();
                        confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
                        confirmWrapper.setErrorEnabled(true);
                        confirmWrapper.setError("Passwords do not match");
                    }
                }

            }
        });

        observeResponse();
    }

    public void observeResponse(){
        responseMessage = userViewModel.getResponseMessage();
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
            Toast.makeText(this,"PASSWORD SUCCESSFULLY CHANGED", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){

            LiveData<NetworkResponse> networkResponse = userViewModel.getNetworkResponse();
            networkResponse.observe(this, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePassword.this);
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


    public void updatePassword(String oldPassword, String newPassword){

        LiveData<User> liveUser = userViewModel.changePassword(oldPassword, newPassword);
        liveUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {

            }
        });
    }

    public void init(){
        currentPassword = findViewById(R.id.current_password);
        newPassword = findViewById(R.id.new_password);
        confirmPassword = findViewById(R.id.confirm_password);

        updateBtn = findViewById(R.id.update_password_btn);

        currentWrapper = findViewById(R.id.current_pass_wrapper);
        newWrapper = findViewById(R.id.new_pass_wrapper);
        confirmWrapper = findViewById(R.id.confirm_pass_wrapper);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        dialog = Utils.customerProgressBar(this);
    }

    public boolean validateCurrentPassword(){
        String inputText = currentPassword.getText().toString();

        dialog.show();

        if(inputText.isEmpty()){
            currentWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            currentWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else{
            currentWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            currentWrapper.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateNewPassword(){
        String inputText = newPassword.getText().toString();

        if(inputText.isEmpty()){
            newWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            newWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            newWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            newWrapper.setErrorEnabled(true);
            newWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            newWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            newWrapper.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateConfirmPassword(){
        String inputText = confirmPassword.getText().toString();

        if(inputText.isEmpty()){
            confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            confirmWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            confirmWrapper.setErrorEnabled(true);
            confirmWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            confirmWrapper.setErrorEnabled(false);
            return true;
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!InternetConnection.checkConnection(this)){
            startActivity(new Intent(this, InternetError.class));
        }
    }
}
