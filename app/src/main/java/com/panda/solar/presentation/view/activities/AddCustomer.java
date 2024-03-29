package com.panda.solar.presentation.view.activities;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
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

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.panda.solar.Model.constants.IdType;
import com.panda.solar.Model.constants.UserType;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.CustomerModel;
import com.panda.solar.Model.entities.User;
import com.panda.solar.Model.entities.Village;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.InternetConnection;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;

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
    //private TextInputLayout villageWrapper;
    //private Spinner village;
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
    private MaterialButton addVillageBtn;

    private double latitude;
    private double longitude;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private CustomerViewModel customerViewModel;
    private ProgressDialog dialog;
    private LiveData<String> responseMessage;
    private LiveData<CustomerMeta> liveCustomerMeta;
    private List<Village> villages;
    private Village villageResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_customer);

        init();
        //setVillages();
        initSpinner();
        setCalendar();

        addVillageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddCustomer.this, VillageList.class);
                startActivityForResult(intent, Constants.VILLAGE_OBJECT_CODE);
            }
        });

        dob.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                datePickerDialog.show();
                return true;
            }
        });

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*if(validateFirstName() && validateLastName() && validateOtherName() && validateGender()
                   && validateDOB() && validateEmail() && validatePrimaryPhone() && validateIdType() && validateIdNumber()
                   && validateAddress() && validateVillage() && validateSecPrimaryPhone() && validateSecEmail()){*/

                if(validateFirstName() && validateLastName() && validateGender()
                   && validateDOB() && validatePrimaryPhone() && validateIdType() && validateIdNumber()
                   && validateAddress() && validateVillage() && validateSecPrimaryPhone() ){

                    String inputText = email.getText().toString();
                    String inputText2 = secEmail.getText().toString();
                    String inputText3 = otherName.getText().toString();

                    if(!inputText.isEmpty()){
                        validateEmail();
                    }

                    if(!inputText2.isEmpty()){
                        validateSecEmail();
                    }

                    if(!inputText3.isEmpty()){
                        validateOtherName();
                    }

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

    public void setCalendar(){
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
    }

    public void setVillages(){
        LiveData<List<Village>> liveDataVillages = customerViewModel.getVillages();
        liveDataVillages.observe(this, new Observer<List<Village>>() {
            @Override
            public void onChanged(@Nullable List<Village> villagez) {
                villages.addAll(villagez);
            }
        });
        observeResponse();
    }

    public void observeResponse(){
        responseMessage = customerViewModel.getResponseMessage();
        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){

        if(msg.equals(Constants.ERROR_RESPONSE)){
            finish();
            Toast.makeText(this,"TRY AGAIN", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            finish();
            Toast.makeText(this,"CONNECTION, TRY AGAIN", Toast.LENGTH_SHORT).show();
        }
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
        user.setHomelat((float)latitude);
        user.setHomelong((float)longitude);
        user.setIdtype(getIdType(idType.getSelectedItem().toString()));
        user.setIdnumber(idNumber.getText().toString());
        user.setProfilephotopath("");
        user.setSecondaryemail(secEmail.getText().toString());
        user.setSecondaryphone(secPhone.getText().toString());
        user.setVillageid((short)villageResult.getId());

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
        //village = findViewById(R.id.cust_village);
        //villageWrapper = findViewById(R.id.village_wrapper);
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
        addVillageBtn = findViewById(R.id.cust_add_village_btn);

        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        dialog = Utils.customerProgressBar(this);
        villages = new ArrayList<>();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        getLastLocation();
    }

    public void initSpinner(){

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
        String inputText = addVillageBtn.getText().toString();//village.getSelectedItem().toString();

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

    private void getLastLocation(){
        if (Utils.checkLocationPermissions(this)) {
            if (isLocationEnabled()) {
                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(
                        new OnCompleteListener<Location>() {
                            @Override
                            public void onComplete(@NonNull Task<Location> task) {
                                Location location = task.getResult();
                                if (location == null) {
                                    requestNewLocationData();
                                } else {
                                    latitude = location.getLatitude();
                                    longitude = location.getLongitude();
                                }
                            }
                        }
                );
            } else {
                Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            Utils.requestLocationPermissions(this);
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location lastLocation = locationResult.getLastLocation();

            latitude = lastLocation.getLatitude();
            longitude = lastLocation.getLongitude();
        }
    };

    private void requestNewLocationData(){

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(0);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.requestLocationUpdates(
                mLocationRequest, locationCallback,
                Looper.myLooper()
        );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == Constants.VILLAGE_OBJECT_CODE){
            if(resultCode == RESULT_OK){
                villageResult = data.getParcelableExtra(Constants.VILLAGE_RESULT);
                addVillageBtn.setText(villageResult.getName());
            }else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "No Village selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == Constants.LOCATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
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
