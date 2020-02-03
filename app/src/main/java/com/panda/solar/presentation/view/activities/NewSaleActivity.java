package com.panda.solar.presentation.view.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.panda.solar.activities.R;
import com.panda.solar.services.LocationUpdateService;
import com.panda.solar.utils.LocationUtils;
import com.panda.solar.utils.Utils;

import static com.panda.solar.services.LocationUpdateService.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
import static com.panda.solar.services.LocationUpdateService.UPDATE_INTERVAL_IN_MILLISECONDS;

public class NewSaleActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView barcodeCard;
    private CardView coordinatesCard;
    private CardView queryCustomerCard;

    private ImageView iconTick;
    private ImageView coordinatesTick;

    private TextView barcodeText;
    private TextView coordinatesText;

    private LinearLayout barcodeTextHolder;
    private LinearLayout coordinatesTextHolder;

    private Dialog dialog;

    private String barCodeContent;

    private LocationManager locationManager;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private final int REQUEST_CHECK_SETTINGS = 2;

    private LocationRequest mLocationRequest;

    private LocationUpdateService mService = null;
    private boolean mBound = false;

    // Monitors the state of the connection to the service.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(NewSaleActivity.this, "Connected to service", Toast.LENGTH_SHORT).show();
            LocationUpdateService.LocalBinder binder = (LocationUpdateService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            LocalBroadcastManager.getInstance(NewSaleActivity.this).registerReceiver(myReceiver,
                    new IntentFilter(LocationUpdateService.ACTION_BROADCAST));

            mService.requestLocationUpdates();
            showDialog();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
            mBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_sale);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.register);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);

        iconTick = (ImageView)findViewById(R.id.ic_tick);
        coordinatesTick = (ImageView)findViewById(R.id.ic_coordinates_tick);

        barcodeText = (TextView)findViewById(R.id.bar_code_text);
        coordinatesText = (TextView)findViewById(R.id.coordinates_text);

        barcodeTextHolder = (LinearLayout)findViewById(R.id.bar_code_text_holder);
        coordinatesTextHolder = (LinearLayout)findViewById(R.id.coordinates_text_holder);

        barcodeCard = (CardView)findViewById(R.id.barcode_card);
        coordinatesCard = (CardView)findViewById(R.id.coordinates_card);
        queryCustomerCard = (CardView)findViewById(R.id.query_customer_card);

        barcodeCard.setOnClickListener(this);
        coordinatesCard.setOnClickListener(this);
        queryCustomerCard.setOnClickListener(this);

        barcodeTextHolder.setVisibility(View.GONE);
        coordinatesTextHolder.setVisibility(View.GONE);


        //getDialog();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(myReceiver);
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            unbindService(mServiceConnection);
            mBound = false;
        }

        super.onStop();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.barcode_card:
                startBarcodeScanner();
                break;

            case R.id.coordinates_card:
                startLocationQuery();
                break;

            case R.id.query_customer_card:
                startQueryCustomerActivity();
                break;
        }
    }

    private void startQueryCustomerActivity(){

        Intent intent = new Intent(this, RepairActivity.class);
        startActivity(intent);
    }

    private void startLocationQuery(){

        if (!checkPermissions()) {
            requestPermissions();
        } else {

            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                if(!mBound){
                    bindService(new Intent(NewSaleActivity.this, LocationUpdateService.class), mServiceConnection,
                            Context.BIND_AUTO_CREATE);
                }else{

                    if(mService != null){
                        LocalBroadcastManager.getInstance(this).registerReceiver(myReceiver,
                                new IntentFilter(LocationUpdateService.ACTION_BROADCAST));
                        mService.requestLocationUpdates();
                        showDialog();
                    }

                }

            }else{
                showLocationDialog();
            }

        }
    }

    private void startBarcodeScanner(){

        IntentIntegrator integrator = new IntentIntegrator(NewSaleActivity.this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
        integrator.setPrompt("Place code in the rectangle to scan");
        integrator.setCameraId(0);
        integrator.setBarcodeImageEnabled(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(intentResult != null) {
            if(intentResult.getContents() == null) {

                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();

            } else {

                barCodeContent = intentResult.getContents();
                activateBarCodeCard(barCodeContent);
                Toast.makeText(this, "Scanned: " + intentResult.getContents(), Toast.LENGTH_LONG).show();
            }
        }else{
            if(requestCode == REQUEST_CHECK_SETTINGS){
                if(resultCode == RESULT_OK){
                    bindService(new Intent(NewSaleActivity.this, LocationUpdateService.class), mServiceConnection,
                            Context.BIND_AUTO_CREATE);
                }else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(NewSaleActivity.this, R.string.turn_on_location_warning, Toast.LENGTH_LONG).show();
                }
            }
        }
    }


    private boolean checkPermissions() {

        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {


        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {

                Toast.makeText(this, "Empty", Toast.LENGTH_SHORT).show();

            }else{

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        bindService(new Intent(NewSaleActivity.this, LocationUpdateService.class), mServiceConnection,
                                Context.BIND_AUTO_CREATE);
                    }else{
                        showLocationDialog();
                    }
                } else {
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
                    // Permission denied.

                }
            }
        }
    }

    private BroadcastReceiver myReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Location location = intent.getParcelableExtra(LocationUpdateService.EXTRA_LOCATION);
            if (location != null) {
                LocalBroadcastManager.getInstance(NewSaleActivity.this).unregisterReceiver(myReceiver);
                stopDialog();
                activateCoordinatesCard(LocationUtils.getLocationText(location));
                Toast.makeText(NewSaleActivity.this, LocationUtils.getLocationText(location),
                        Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void activateBarCodeCard(String barCodeContent){
        iconTick.setColorFilter(ContextCompat.getColor(this, R.color.lawn_green), PorterDuff.Mode.SRC_IN);
        barcodeTextHolder.setVisibility(View.VISIBLE);
        barcodeText.setText(barCodeContent);

    }

    private void activateCoordinatesCard(String coordinates){
        coordinatesTick.setColorFilter(ContextCompat.getColor(this, R.color.lawn_green), PorterDuff.Mode.SRC_IN);
        coordinatesTextHolder.setVisibility(View.VISIBLE);
        coordinatesText.setText(coordinates);

    }




    private void showLocationDialog(){

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
        createLocationRequest();
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                final LocationSettingsStates state = result.getLocationSettingsStates();

                switch (status.getStatusCode()) {

                    case LocationSettingsStatusCodes.SUCCESS:

                        bindService(new Intent(NewSaleActivity.this, LocationUpdateService.class), mServiceConnection,
                                Context.BIND_AUTO_CREATE);

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            status.startResolutionForResult(
                                    NewSaleActivity.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {

                        }

                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:

                        break;
                }
            }
        });
    }



    private void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setFastestInterval(FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    private void showDialog(){
        dialog = new Utils().getDialog(this);
        if(dialog != null){
            dialog.show();
        }
    }

    private void stopDialog(){
        if(dialog != null && dialog.isShowing()){
            dialog.dismiss();
        }
    }



}
