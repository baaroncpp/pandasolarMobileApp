package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
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
import com.panda.solar.data.repository.retroRepository.LoginRepository;
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

    private LoginRepository loginRepository;
    private ProgressDialog progressDialog;
    private UserViewModel userViewModel;
    private LiveData<Token> liveToken;
    private LiveData<String> responseMessage;
    private LiveData<NetworkResponse> networkResponseLiveData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

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
    }

    private void loginUser(){
        phoneNumber = loginPhoneNumberEditTxt.getText().toString().trim();
        password = loginPasswordEditTxt.getText().toString().trim();

        if(validInputs()){

            Login log = new Login(phoneNumber, password);

            liveToken = userViewModel.authenticateUser(log);
            responseMessage = userViewModel.getResponseMessage();

            progressDialog.show();
            liveToken.observe(this, new Observer<Token>() {
                @Override
                public void onChanged(@Nullable Token token) {
                    observeResponse(token);
                }
            });
        }
    }

    public void observeResponse(final Token token){

        responseMessage = userViewModel.getResponseMessage();
        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s, token);
            }
        });
    }

    public void handleResponse(String msg, Token token){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            getUserDetails();
            saveJWT(token);
            startActivity(new Intent(this,HomeActivity.class));
            progressDialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){

            networkResponseLiveData = userViewModel.getNetworkResponse();
            networkResponseLiveData.observe(this, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
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

            progressDialog.dismiss();
            Toast.makeText(this,"BAD CREDENTIALS TRY AGAIN", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
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

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        LiveData<User> liveUser = userViewModel.getUser();
        liveUser.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                saveUserDetails(user);
            }
        });
    }

   /* @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.hashCode() == loginPhoneNumberEditTxt.getText().hashCode()){
            loginPhoneNumberEditTxt.setError(null);
        }
        else if(s.hashCode() == loginPasswordEditTxt.getText().hashCode()){
            loginPasswordEditTxt.setError(null);
        }
    }


    @Override
    public void onCallback(NetworkResponse networkResponse) {

    }
*/
    public void saveJWT(Token token){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.JWT_TOKEN, token.getToken());
        editor.apply();

    }

    public void saveUserDetails(User user){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.USER_ID, user.getId());
        editor.putString(Constants.USER_TYPE, user.getUsertype());
        editor.apply();
    }

    public void clearJWT(){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.JWT_TOKEN, "");
        editor.apply();
    }
}
