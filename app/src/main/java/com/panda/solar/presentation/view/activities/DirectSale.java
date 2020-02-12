package com.panda.solar.presentation.view.activities;

import android.content.Intent;
import android.support.design.button.MaterialButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.panda.solar.activities.R;

public class DirectSale extends AppCompatActivity {

    private MaterialButton scannerButtonView;
    private MaterialButton productButtonView;
    private MaterialButton customerButtonView;
    private MaterialButton locationButtonView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_sale);

        init();

        productButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleProductActivity.class);
                startActivity(intent);
            }
        });

    }

    public void init(){
        scannerButtonView = findViewById(R.id.scanner_button);
        productButtonView = findViewById(R.id.add_product_button);
        customerButtonView = findViewById(R.id.add_customer_button);
        locationButtonView = findViewById(R.id.set_location_button);
    }


}
