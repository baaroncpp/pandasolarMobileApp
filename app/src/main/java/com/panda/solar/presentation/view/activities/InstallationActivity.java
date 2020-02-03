package com.panda.solar.presentation.view.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.fragments.HousePictureFragment;
import com.panda.solar.presentation.view.fragments.InstallationLocationFragment;
import com.panda.solar.presentation.view.fragments.InstallationSearchCustomerFragment;
import com.panda.solar.presentation.view.fragments.ScanEquipmentFragment;

public class InstallationActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private final int PERMISSION_REQUEST_CODE = 5;
    private final int REQUEST_CHECK_SETTINGS = 2;
    private int ACTION_IMAGE_CAPTURE_REQUEST_CODE = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }

        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.installation_view_pager);

        viewPager.setAdapter(new MFragmentStatePagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicator(null);
        tabLayout.addOnTabSelectedListener(this);


    }

    public ViewPager getViewPager(){
        return this.viewPager;
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {

        View view = tab.getCustomView();
        //RelativeLayout relativeLayout = (RelativeLayout) view.findViewById(R.id.layout_background);
        if(view != null){
            view.setBackgroundResource(R.drawable.tablayout_background_selected);
        }else{
            Log.d("Location", "View is null");
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    private class MFragmentStatePagerAdapter extends FragmentPagerAdapter {
        public MFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (i){
                case 0:
                    fragment = new InstallationSearchCustomerFragment();
                    break;

                case 1:
                    fragment = new ScanEquipmentFragment();
                    break;

                case 2:
                    fragment = new InstallationLocationFragment();
                    break;

                case 3:
                    fragment = new HousePictureFragment();
                    break;

            }
            return fragment;
        }

        @Override
        public int getCount() {
            return 4;
        }
    };

    @Override
    public void onResume(){
        super.onResume();
        if(!checkIfHasPermission()){
            requestForPermission();
        }
    }

    private boolean checkIfHasPermission() {
        return PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA) &&
                PackageManager.PERMISSION_GRANTED == ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.CAMERA);
    }

    private void requestForPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == PERMISSION_REQUEST_CODE){
            if(grantResults.length > 1){

                if(grantResults.length>0){
                    if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){

                    }else{
                        this.finish();
                        //requestForPermission();
                    }
                }else{
                    this.finish();
                }
            }else{
                this.finish();
            }


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CHECK_SETTINGS){
            if(resultCode == RESULT_OK){

                InstallationLocationFragment installationLocationFragment = (InstallationLocationFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.installation_view_pager + ":" + viewPager.getCurrentItem());
                if(installationLocationFragment != null && installationLocationFragment.isVisible()){
                    installationLocationFragment.startLocationQuery();
                }
            }

        }
        else if(requestCode == ACTION_IMAGE_CAPTURE_REQUEST_CODE){

            HousePictureFragment housePictureFragment = (HousePictureFragment) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.installation_view_pager + ":" + viewPager.getCurrentItem());
            if(housePictureFragment != null && housePictureFragment.isVisible()){
                housePictureFragment.onActivityResult(requestCode, resultCode, data);
            }
        }

    }

    @Override
    public void onBackPressed() {
        int position = viewPager.getCurrentItem();
        if(position == 3){
            viewPager.setCurrentItem(2);
        }
        else if(position == 2){
            viewPager.setCurrentItem(1);
        }
        else if(position == 1){
            viewPager.setCurrentItem(0);
        }
        else if(position == 0){
            super.onBackPressed();
        }

    }
}
