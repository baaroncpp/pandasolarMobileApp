package com.panda.solar.presentation.view.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.panda.solar.Model.constants.IdType;
import com.panda.solar.Model.constants.UserType;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;
import com.panda.solar.viewModel.UserViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AddCustomer extends AppCompatActivity {

    private TextInputLayout firstNameWrapper;
    private TextInputEditText firstName;
    private TextInputLayout lastNameWrapper;
    private TextInputEditText lastName;
    private TextInputLayout otherNameWrapper;
    private TextInputEditText otherName;
    private AppCompatRadioButton maleBtn;
    private AppCompatRadioButton femaleBtn;
    private TextInputLayout dobWrapper;
    private TextInputEditText dob;
    private TextInputLayout emailWrapper;
    private TextInputEditText email;
    private TextInputLayout phoneWrapper;
    private TextInputEditText phone;
    private TextInputLayout villageWrapper;
    private Spinner village;
    private TextInputLayout addressWrapper;
    private TextInputEditText address;
    private TextInputLayout secPhoneWrapper;
    private TextInputEditText secPhone;
    private TextInputLayout secEmailWrapper;
    private TextInputEditText secEmail;
    private MaterialButton nextBtn;
    private DatePickerDialog datePickerDialog;
    private TextInputEditText idNumber;
    private TextInputLayout idNumberWrapper;
    private Spinner idType;
    private TextInputLayout idTypeWrapper;
    private CustomerModel user;

    private CustomerViewModel customerViewModel;
    private ProgressDialog dialog;
    private LiveData<String> responseMessage;
    private LiveData<CustomerMeta> liveCustomerMeta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        init();

        initSpinner();

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                Calendar c = Calendar.getInstance();
                int cYear = c.get(Calendar.YEAR);
                int cMonth = c.get(Calendar.MONTH);
                int cDay = c.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddCustomer.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dob.setText(Integer.toString(dayOfMonth)+"-"+Integer.toString(month)+"-"+Integer.toString(year));
                    }
                }, cYear, cMonth, cDay);
                datePickerDialog.show();
                return true;
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validateFirstName() && validateLastName() && validateOtherName() && validateGender()
                   && validateDOB() && validateEmail() && validatePrimaryPhone() && validateIdType() && validateIdNumber()
                   && validateAddress() && validateVillage() && validateSecPrimaryPhone() && validateSecEmail()){

                    Intent intent = new Intent(AddCustomer.this, ReviewCustomer.class);
                    intent.putExtra(Constants.CUSTOMER_MODEL_OBJECT, createCustomerUser());
                    startActivity(intent);
                }

            }
        });

        secPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int len = s.toString().length();

                if(before == 0 && (len == 4 || len == 8)){
                    phone.append(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                int len = s.toString().length();

                if(before == 0 && (len == 4 || len == 8)){
                    phone.append(" ");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    public CustomerModel createCustomerUser(){

        user = new CustomerModel();

        user.setPrimaryphone(phone.getText().toString().trim().replace(" ", ""));
        user.setUsertype(UserType.CUSTOMER);
        user.setLastname(lastName.getText().toString().trim());
        user.setFirstname(firstName.getText().toString().trim());
        user.setMiddlename(otherName.getText().toString().trim());
        user.setEmail(email.getText().toString().trim());
        user.setDateofbirth(new Date());//convert string to java date
        user.setUsername(phone.getText().toString().trim().replace(" ", ""));
        user.setPassword("pandaCustomer");
        user.setCompanyemail(email.getText().toString().trim());
        user.setSex(maleFemale());
        user.setTitle("MRS");
        user.setAddress(address.getText().toString());
        user.setConsentformpath("");
        user.setHomelat(45);
        user.setHomelong(45);
        user.setIdtype(getIdType(idType.getSelectedItem().toString()));
        user.setIdnumber(idNumber.getText().toString());
        user.setProfilephotopath("");
        user.setSecondaryemail(secEmail.getText().toString());
        user.setSecondaryphone(secPhone.getText().toString());

        return user;
    }

    public String getIdType(String text){
        if(text.equals(IdType.NATIONALID.toString())){
            return IdType.NATIONALID.name();
        }else if(text.equals(IdType.DRIVINGPERMIT.toString())){
            return IdType.DRIVINGPERMIT.name();
        }else if(text.equals(IdType.PASSPORT.toString())){
            return IdType.PASSPORT.name();
        }else{return null;}
    }

    public String maleFemale(){
        if(maleBtn.isChecked()){
            return "MALE";
        }else if(femaleBtn.isChecked()){
            return "FEMALE";
        }else{return "";}
    }

    public void init(){
        firstNameWrapper = findViewById(R.id.firstname_wrapper);
        firstName = findViewById(R.id.cust_firstname);
        lastNameWrapper = findViewById(R.id.lastname_wrapper);
        lastName = findViewById(R.id.cust_lastname);
        otherNameWrapper = findViewById(R.id.othername_wrapper);
        otherName = findViewById(R.id.cust_othername);
        maleBtn = findViewById(R.id.cust_male_radiobtn);
        femaleBtn = findViewById(R.id.cust_femalebtn);
        dobWrapper = findViewById(R.id.dob_wrapper);
        dob = findViewById(R.id.cust_dob);
        emailWrapper = findViewById(R.id.email_wrapper);
        email = findViewById(R.id.cust_email);
        phone = findViewById(R.id.cust_phone);
        phoneWrapper = findViewById(R.id.phone_wrapper);
        nextBtn = findViewById(R.id.cust_next_btn);
        village = findViewById(R.id.cust_village);
        villageWrapper = findViewById(R.id.village_wrapper);
        address = findViewById(R.id.cust_address);
        addressWrapper = findViewById(R.id.address_wrapper);
        secEmail = findViewById(R.id.cust_secondarymail);
        secEmailWrapper = findViewById(R.id.secondarymail_wrapper);
        secPhone = findViewById(R.id.cust_secondaryphone);
        secPhoneWrapper = findViewById(R.id.secondaryphone_wrapper);
        idNumber = findViewById(R.id.cust_idnumber);
        idNumberWrapper = findViewById(R.id.idnumber_wrapper);
        idType = findViewById(R.id.cust_id_type);
        idTypeWrapper = findViewById(R.id.idtype_wrapper);

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        dialog = Utils.customerProgressBar(this);
    }

    public void initSpinner(){
        List<String> villageNames = new ArrayList<>();
        villageNames.add("Choose Village");
        villageNames.add("Ntinda");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, villageNames);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        village.setAdapter(arrayAdapter);

        List<String> idTypes = new ArrayList<>();
        idTypes.add("Select ID Type");
        idTypes.add("NATIONALID");
        idTypes.add("PASSPORT");
        idTypes.add("DRIVINGPERMIT");
        ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, idTypes);
        arrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        idType.setAdapter(arrayAdapter2);
    }

    public boolean validateIdType(){
        String inputText = idType.getSelectedItem().toString();

        if(inputText.equals("Select ID Type")){
            Toast.makeText(this, "select ID type", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateVillage(){
        String inputText = village.getSelectedItem().toString();

        if(inputText.equals("Choose Village")){
            Toast.makeText(this, "select village", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    public boolean validateFirstName(){
        String inputText = firstName.getText().toString();

        if(inputText.isEmpty()){
            firstNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            firstNameWrapper.setErrorEnabled(true);
            firstNameWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            firstNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            firstNameWrapper.setErrorEnabled(true);
            firstNameWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            firstNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            firstNameWrapper.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateLastName(){
        String inputText = lastName.getText().toString();

        if(inputText.isEmpty()){
            lastNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            lastNameWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            lastNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            lastNameWrapper.setErrorEnabled(true);
            lastNameWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            lastNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            lastNameWrapper.setErrorEnabled(false);
            return true;
        }
    }

    public boolean validateAddress(){
        String inputText = address.getText().toString();

        if(inputText.isEmpty()){
            addressWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            addressWrapper.setErrorEnabled(true);
            addressWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            addressWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            addressWrapper.setErrorEnabled(true);
            addressWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            addressWrapper.setErrorEnabled(false);
            addressWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }
    }

    public boolean validateIdNumber(){
        String inputText = idNumber.getText().toString();

        if(inputText.isEmpty()){
            idNumberWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            idNumberWrapper.setErrorEnabled(true);
            idNumberWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            idNumberWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            idNumberWrapper.setErrorEnabled(true);
            idNumberWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            idNumberWrapper.setErrorEnabled(false);
            idNumberWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }
    }

    public boolean validateGender(){
        if(maleBtn.isChecked() || femaleBtn.isChecked()){
            return true;
        }else{
            Toast.makeText(this, "Add customer gender", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateOtherName(){
        String inputText = otherName.getText().toString();

        if(inputText.isEmpty()){
            otherNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            otherNameWrapper.setErrorEnabled(true);
            otherNameWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.length() < 4){
            otherNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            otherNameWrapper.setErrorEnabled(true);
            otherNameWrapper.setError(Constants.SHORT_INPUT);
            return false;
        }else{
            otherNameWrapper.setErrorEnabled(false);
            otherNameWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }
    }

    public boolean validateDOB(){
        String inputText = dob.getText().toString();

        if(inputText.isEmpty()){
            dobWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            dobWrapper.setErrorEnabled(true);
            dobWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else{
            dobWrapper.setErrorEnabled(false);
            dobWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }
    }

    public boolean validatePrimaryPhone(){
        String inputText = phone.getText().toString();

        if(inputText.isEmpty()){
            phoneWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            phoneWrapper.setErrorEnabled(true);
            phoneWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(!Utils.validatePhoneNumberInput(inputText)){
            phoneWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            phoneWrapper.setErrorEnabled(true);
            phoneWrapper.setError("Invalid phone number");
            return false;
        }else{phoneWrapper.setErrorEnabled(false);
            phoneWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }
    }

    public boolean validateSecPrimaryPhone(){
        String inputText = secPhone.getText().toString();

        if(inputText.isEmpty()){
            secPhoneWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            secPhoneWrapper.setErrorEnabled(true);
            secPhoneWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(!Utils.validatePhoneNumberInput(inputText)){
            secPhoneWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            secPhoneWrapper.setErrorEnabled(true);
            secPhoneWrapper.setError("Invalid phone number");
            return false;
        }else{
            secPhoneWrapper.setErrorEnabled(false);
            secPhoneWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }
    }

    public boolean validateEmail(){

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String inputText = email.getText().toString();

        if(inputText.isEmpty()){
            emailWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            emailWrapper.setErrorEnabled(true);
            emailWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.matches(emailPattern)){
            emailWrapper.setErrorEnabled(false);
            emailWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }else{
            emailWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            emailWrapper.setErrorEnabled(true);
            emailWrapper.setError("Invalid email");
            return false;
        }
    }

    public boolean validateSecEmail(){

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        String inputText = secEmail.getText().toString();

        if(inputText.isEmpty()){
            secEmailWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            secEmailWrapper.setErrorEnabled(true);
            secEmailWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(inputText.matches(emailPattern)){
            secEmailWrapper.setErrorEnabled(false);
            secEmailWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            return true;
        }else{
            secEmailWrapper.setErrorEnabled(true);
            secEmailWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            secEmailWrapper.setError("Invalid email");
            return false;
        }
    }

    /*public boolean validatePassword(){
        String pass = password.getText().toString();
        String comfPass = confirmPassword.getText().toString();

        if(pass.isEmpty()){
            passwordWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            passwordWrapper.setErrorEnabled(true);
            passwordWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(comfPass.isEmpty()){
            confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            confirmWrapper.setErrorEnabled(true);
            confirmWrapper.setError(Constants.FIELD_REQUIRED);
            return false;
        }else if(!pass.equals(comfPass)){
            confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.error));
            confirmWrapper.setErrorEnabled(true);
            confirmWrapper.setError("Passwords do nor match");
            return false;
        }else{
            confirmWrapper.setErrorEnabled(false);
            passwordWrapper.setErrorEnabled(false);
            confirmWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
           return true;
        }
    }*/

}
