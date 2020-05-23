package com.panda.solar.presentation.view.activities;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;

import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.utils.AppContext;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;

import com.panda.solar.viewModel.UserViewModel;

public class LoginActivity extends AppCompatActivity/* implements View.OnClickListener, TextWatcher, NetworkCallback*/ {

    private Button loginButton;
    private EditText loginPhoneNumberEditTxt;
    private EditText loginPasswordEditTxt;
    private TextView forgotPasswordTextView;

    private String phoneNumber;
    private String password;

    private ProgressDialog progressDialog;
    private UserViewModel userViewModel;
    private LiveData<Token> liveToken;
    private LiveData<String> responseMessage;
    private LiveData<NetworkResponse> networkResponseLiveData;
    private Token token;
    private boolean isTokenStored;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }


        int ALL_PERMISSIONS = 101;

        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE ,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

        ActivityCompat.requestPermissions(this, permissions, ALL_PERMISSIONS);

        init();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

    public void init(){

        progressDialog = Utils.customerProgressBar(this);

        loginButton = (Button)findViewById(R.id.login_button);
        loginPhoneNumberEditTxt = (EditText)findViewById(R.id.login_phone_number);
        loginPasswordEditTxt = (EditText)findViewById(R.id.login_password);
        forgotPasswordTextView = (TextView)findViewById(R.id.forgot_password_text);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);


        token = new Token();
    }

    public void checkAppPermissions(){

        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            Utils.appPermissions(this, Constants.READ_STORAGE_PERMISSION_CODE);
        }

        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)){
            Utils.appPermissions(this, Constants.WRITE_STORAGE_PERMISSION_CODE);
        }

        if(!(ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)){
            Utils.appPermissions(this, Constants.CAMERA_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == Constants.WRITE_STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Write Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Read Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == Constants.CAMERA_PERMISSION_CODE){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this,"Camera Permission Granted", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void loginUser(){
        phoneNumber = loginPhoneNumberEditTxt.getText().toString().trim();
        password = loginPasswordEditTxt.getText().toString().trim();

        if(validInputs()){

            Login log = new Login(phoneNumber, password);

            liveToken = userViewModel.authenticateUser(log);
            progressDialog.show();
            liveToken.observe(this, new Observer<Token>() {
                @Override
                public void onChanged(@Nullable Token t) {
                    token = t;
                    isTokenStored = saveJWT(t);
                    observeResponse();
                }
            });
            //observeResponse();
        }
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
        if(msg.equals(Constants.SUCCESS_RESPONSE) && token != null){

            if(!Utils.isSharedPreferenceSet(this) && isTokenStored){
                getUserDetails();
            }else{
                progressDialog.dismiss();
                Toast.makeText(this,"FAILED, TRY AGAIN", Toast.LENGTH_LONG).show();
            }


        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            progressDialog.dismiss();
            Toast.makeText(this,"BAD CREDENTIALS TRY AGAIN", Toast.LENGTH_LONG).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_LONG).show();
        }
    }

    private boolean validInputs(){
        if(phoneNumber.isEmpty()){
            loginPhoneNumberEditTxt.setError("Enter phone number");
            return false;
        }else if(password.isEmpty()){
            loginPasswordEditTxt.setError("Enter password");
            return false;
        }
        return true;
    }

    public void getUserDetails(){

        UserViewModel uViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        LiveData<User> liveUser = uViewModel.getUser();
        liveUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                Utils.saveUserDetails(user);
            }
        });

        LiveData<String> response2 = uViewModel.getResponseMessage();
        response2.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {

                if(s.equals(Constants.SUCCESS_RESPONSE)){
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    progressDialog.dismiss();
                }else if(s.equals(Constants.ERROR_RESPONSE)){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"BAD CREDENTIALS TRY AGAIN", Toast.LENGTH_LONG).show();
                }else if(s.equals(Constants.FAILURE_RESPONSE)){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"CONNECTION FAILURE", Toast.LENGTH_LONG).show();
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this,"TRY AGAIN LATER", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public boolean saveJWT(Token token){

        if(token != null){
            //SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(Constants.JWT_TOKEN, token.getToken());
            editor.commit();

            return true;
        }else {
            return false;
        }
    }


}
