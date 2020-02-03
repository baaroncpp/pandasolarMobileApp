package com.panda.solar.presentation.view.activities;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.panda.solar.activities.R;

public class NewEquipmentActivity extends AppCompatActivity implements View.OnClickListener {

    private CardView barcodeCard;
    private LinearLayout barcodeTextHolder;
    private ImageView iconTick;
    private TextView barcodeText;
    private String barCodeContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_equipment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryDark));
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.register_new_equipment);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        barcodeTextHolder = (LinearLayout)findViewById(R.id.bar_code_text_holder);
        iconTick = (ImageView)findViewById(R.id.ic_tick);
        barcodeText = (TextView)findViewById(R.id.bar_code_text);
        barcodeCard = (CardView)findViewById(R.id.barcode_card);

        barcodeTextHolder.setVisibility(View.GONE);

        barcodeCard.setOnClickListener(this);
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
    protected void onResume() {
        super.onResume();

    }

    private void startBarcodeScanner(){

        IntentIntegrator integrator = new IntentIntegrator(NewEquipmentActivity.this);
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
        }
    }

    private void activateBarCodeCard(String barCodeContent){
        iconTick.setColorFilter(ContextCompat.getColor(this, R.color.lawn_green), PorterDuff.Mode.SRC_IN);
        barcodeTextHolder.setVisibility(View.VISIBLE);
        barcodeText.setText(barCodeContent);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.barcode_card:
                startBarcodeScanner();
                break;

        }
    }
}
