package com.panda.solar.presentation.view.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkCallback;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.RetrofitHelper;
import com.panda.solar.data.repository.retroRepository.LoginRepository;
import com.panda.solar.utils.AppContext;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.TokenViewModel;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class LoginActivity extends AppCompatActivity/* implements View.OnClickListener, TextWatcher, NetworkCallback*/ {

    private Button loginButton;
    private EditText loginPhoneNumberEditTxt;
    private EditText loginPasswordEditTxt;
    private TextView forgotPasswordTextView;

    private String phoneNumber;
    private String password;

    private Dialog dialog;

    private LoginRepository loginRepository;
    private Token token;
    private String bad_request;
    private String connection_fail;
    private TokenViewModel tokenViewModel;
    private SweetAlertDialog sweetDialog;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        sweetDialog = Utils.customSweetAlertDialog(this);
        progressDialog = Utils.customerProgressBar(this);

        loginButton = (Button)findViewById(R.id.login_button);
        loginPhoneNumberEditTxt = (EditText)findViewById(R.id.login_phone_number);
        loginPasswordEditTxt = (EditText)findViewById(R.id.login_password);
        forgotPasswordTextView = (TextView)findViewById(R.id.forgot_password_text);

        /*loginPasswordEditTxt.addTextChangedListener(this);
        loginPasswordEditTxt.addTextChangedListener(this);

        loginButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);*/

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

    }

   /* @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.login_button:
                loginUser();
                //startActivity(new Intent(this,HomeActivity.class));
                break;
            case R.id.forgot_password_text:
                startActivity(new Intent(this, ForgotPasswordActivity.class));
                break;
        }


    }*/

    private void loginUser(){

        phoneNumber = loginPhoneNumberEditTxt.getText().toString().trim();
        password = loginPasswordEditTxt.getText().toString().trim();

        if(validInputs()){

            progressDialog.show();

            Login log = new Login(phoneNumber, password);

            loginRepository = new LoginRepository();
            token = loginRepository.userLogin(log);
            bad_request = LoginRepository.getBad_request();
            connection_fail = LoginRepository.getConnection_fail();

            if(token != null && !((token.getToken()).equals("Bad credentials!")) && bad_request.isEmpty() && connection_fail.isEmpty()){
                progressDialog.dismiss();
                startActivity(new Intent(this,HomeActivity.class));
                saveJWT(token);
            }else if(token != null && (token.getToken()).equals("Bad credentials!")){
                progressDialog.dismiss();
                Toast.makeText(this, "Bad credentials!, Try again", Toast.LENGTH_LONG).show();
            }

            if(!(LoginRepository.getBad_request()).isEmpty()){
                progressDialog.dismiss();
                Toast.makeText(this,bad_request, Toast.LENGTH_LONG).show();
            }else if(!(LoginRepository.getConnection_fail()).isEmpty()){
                progressDialog.dismiss();
                Toast.makeText(this,connection_fail, Toast.LENGTH_LONG).show();
            }
            progressDialog.dismiss();
            //new RetrofitHelper().login(object, this);
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

        tokenViewModel = ViewModelProviders.of(LoginActivity.this).get(TokenViewModel.class);
        tokenViewModel.saveToken(token);
    }
}
