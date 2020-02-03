package com.panda.solar.presentation.view.fragments;

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
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.panda.solar.presentation.view.activities.InstallationActivity;
import com.panda.solar.activities.R;
import com.panda.solar.services.LocationUpdateService;
import com.panda.solar.utils.LocationUtils;
import com.panda.solar.utils.Utils;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.panda.solar.services.LocationUpdateService.FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS;
import static com.panda.solar.services.LocationUpdateService.UPDATE_INTERVAL_IN_MILLISECONDS;

public class InstallationLocationFragment extends Fragment implements View.OnClickListener {

    private Button searchLocationButton;
    private Dialog dialog;

    private LocationManager locationManager;

    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private final int REQUEST_CHECK_SETTINGS = 2;

    private LocationRequest mLocationRequest;

    private LocationUpdateService mService = null;
    private boolean mBound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        locationManager = (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
    }

    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Toast.makeText(getActivity(), "Connected to service", Toast.LENGTH_SHORT).show();
            LocationUpdateService.LocalBinder binder = (LocationUpdateService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;

            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver,
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_installation_location, container, false);
        searchLocationButton = (Button)v.findViewById(R.id.search_location_button);
        searchLocationButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onPause() {
        if(getActivity() != null){
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
        }

        super.onPause();
    }

    @Override
    public void onStop() {
        if (mBound) {
            // Unbind from the service. This signals to the service that this activity is no longer
            // in the foreground, and the service can respond by promoting itself to a foreground
            // service.
            getActivity().unbindService(mServiceConnection);
            mBound = false;
        }

        super.onStop();
    }



    @Override
    public void onClick(View v) {
        startLocationQuery();
    }

    public void startLocationQuery(){

        if (!checkPermissions()) {
            requestPermissions();
        } else {

            if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                if(!mBound){
                    getActivity().bindService(new Intent(getActivity(), LocationUpdateService.class), mServiceConnection,
                            Context.BIND_AUTO_CREATE);
                }else{

                    if(mService != null){
                        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(myReceiver,
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

    private boolean checkPermissions() {

        return  PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION);
    }

    private void requestPermissions() {
        requestPermissions(
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                REQUEST_PERMISSIONS_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("Location", "onactivityResult");
        if(requestCode == REQUEST_CHECK_SETTINGS){
            if(resultCode == RESULT_OK){
                getActivity().bindService(new Intent(getActivity(), LocationUpdateService.class), mServiceConnection,
                        Context.BIND_AUTO_CREATE);
            }else if(resultCode == RESULT_CANCELED) {
                Toast.makeText(getActivity(), R.string.turn_on_location_warning, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        Log.d("Location", "onrequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
            }else{

                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){
                        getActivity().bindService(new Intent(getActivity(), LocationUpdateService.class), mServiceConnection,
                                Context.BIND_AUTO_CREATE);
                    }else{
                        showLocationDialog();
                    }
                } else {
                    Toast.makeText(getActivity(), "Permission denied", Toast.LENGTH_SHORT).show();
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
                LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(myReceiver);
                stopDialog();
                Toast.makeText(getActivity(), LocationUtils.getLocationText(location),
                        Toast.LENGTH_SHORT).show();

                ((InstallationActivity)getActivity()).getViewPager().setCurrentItem(3);
            }
        }
    };

    private void showLocationDialog(){

        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(getActivity())
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

                        getActivity().bindService(new Intent(getActivity(), LocationUpdateService.class), mServiceConnection,
                                Context.BIND_AUTO_CREATE);

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                        try {
                            status.startResolutionForResult(
                                    getActivity(), REQUEST_CHECK_SETTINGS);
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
        dialog = new Utils().getDialog(getActivity());
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
