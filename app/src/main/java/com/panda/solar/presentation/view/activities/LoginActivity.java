package com.panda.solar.presentation.view.activities;

import android.app.Dialog;
import android.content.Intent;
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
import com.panda.solar.utils.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher, NetworkCallback {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        loginButton = (Button)findViewById(R.id.login_button);
        loginPhoneNumberEditTxt = (EditText)findViewById(R.id.login_phone_number);
        loginPasswordEditTxt = (EditText)findViewById(R.id.login_password);
        forgotPasswordTextView = (TextView)findViewById(R.id.forgot_password_text);

        loginPasswordEditTxt.addTextChangedListener(this);
        loginPasswordEditTxt.addTextChangedListener(this);

        loginButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);



    }

    @Override
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


    }

    private void loginUser(){

        phoneNumber = loginPhoneNumberEditTxt.getText().toString().trim();
        password = loginPasswordEditTxt.getText().toString().trim();

        Login log = new Login(phoneNumber, password);

        if(validInputs()){
            JsonObject object = new JsonObject();
            object.addProperty("username", loginPhoneNumberEditTxt.getText().toString().trim());
            object.addProperty("password", loginPasswordEditTxt.getText().toString().trim());

            dialog = new Utils().getDialog(this);
            showProgressDialog(dialog);

            loginRepository = new LoginRepository();
            token = loginRepository.userLogin(log);
            bad_request = LoginRepository.getBad_request();
            connection_fail = LoginRepository.getConnection_fail();

            dismissProgressDialog(dialog);

            if(token != null && bad_request.isEmpty() && connection_fail.isEmpty()){
                //pass token to DB
                startActivity(new Intent(this,HomeActivity.class));
            }else if(!bad_request.isEmpty()){
                Toast.makeText(this,bad_request, Toast.LENGTH_LONG).show();
            }else if(!connection_fail.isEmpty()){
                Toast.makeText(this,connection_fail, Toast.LENGTH_LONG).show();
            }


            //new RetrofitHelper().login(object, this);
        }

        loginRepository = new LoginRepository();
        token = loginRepository.userLogin(log);
        bad_request = LoginRepository.getBad_request();
        connection_fail = LoginRepository.getConnection_fail();

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

    @Override
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

    public void showProgressDialog(Dialog dialog){

        if(dialog != null && !dialog.isShowing()){
            dialog.show();
        }
    }

    public void dismissProgressDialog(Dialog dialog){

        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }
}
