package com.panda.solar.presentation.view.activities;

import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.panda.solar.activities.R;

public class LeaseSaleActivity extends AppCompatActivity {

    private MaterialButton scanSerialNumberViewBtn;
    private TextInputEditText serialNumberView;
    private MaterialButton leaseOfferBtn;
    private MaterialButton setLocationBtn;
    private TextInputEditText leaseSaleDescriptionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lease_sale);

        init();
    }

    public void init(){
        scanSerialNumberViewBtn = findViewById(R.id.scanner_button);
        serialNumberView = findViewById(R.id.lease_sale_serialnumber);
        leaseOfferBtn = findViewById(R.id.add_lease_offer_button);
        setLocationBtn = findViewById(R.id.set_location_button);
        leaseSaleDescriptionView = findViewById(R.id.description_text_input);
    }
}
