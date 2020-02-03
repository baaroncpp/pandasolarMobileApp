package com.panda.solar.presentation.view.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.panda.solar.activities.R;

public class StockManagementActivity extends AppCompatActivity {

    private EditText totalCommissionEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_management);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.stock_management);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        totalCommissionEditText = (EditText)findViewById(R.id.total_commission);
        totalCommissionEditText.setKeyListener(null);
        totalCommissionEditText.setFocusableInTouchMode(false);
        totalCommissionEditText.setFocusable(false);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
