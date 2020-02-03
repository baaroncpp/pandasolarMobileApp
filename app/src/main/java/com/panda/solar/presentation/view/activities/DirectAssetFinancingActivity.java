package com.panda.solar.presentation.view.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.fragments.AssetFinancingFragment;
import com.panda.solar.presentation.view.fragments.ReceiptFragment;
import com.panda.solar.presentation.view.fragments.SaleCustomerDetailFragment;
import com.panda.solar.presentation.view.fragments.ScanEquipmentFragment;
import com.panda.solar.utils.Constants;

public class DirectAssetFinancingActivity extends AppCompatActivity  {

    private String SALE_TYPE;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_asset_financing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        SALE_TYPE = getIntent().getStringExtra(Constants.SALE_TYPE);

        viewPager = (ViewPager)findViewById(R.id.sale_view_pager);
        tabLayout = (TabLayout)findViewById(R.id.sale_tab_layout);

        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setSelectedTabIndicator(null);
        //viewPager.addOnPageChangeListener(this);
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter{

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = null;
            switch (SALE_TYPE){
                case Constants.ASSET_FINANCING:
                    switch (i){
                        case 0:
                            fragment = new SaleCustomerDetailFragment();
                            break;
                        case 1:
                            fragment = new AssetFinancingFragment();
                            break;
                        case 2:
                            fragment = new ReceiptFragment();
                            break;
                    }
                    break;

                case Constants.DIRECT_SALE:
                    switch (i){
                        case 0:
                            fragment = new SaleCustomerDetailFragment();
                            break;
                        case 1:
                            fragment = new ReceiptFragment();
                            break;
                        case 2:
                            fragment = new ScanEquipmentFragment();
                            break;
                    }
                    break;
            }

            return fragment;
        }

        @Override
        public int getCount() {
            switch (SALE_TYPE){
                case Constants.ASSET_FINANCING:
                    return 3;
                case Constants.DIRECT_SALE:
                    return 3;
                default:
                    return 3;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
