package com.panda.solar.presentation.view.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

public class BarCodeScanner extends AppCompatActivity implements ZBarScannerView.ResultHandler{

    private ZBarScannerView scannerView;
    private final static String SCAN_RESULT = "barcodeScanResult";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scannerView = new ZBarScannerView(this);
        setContentView(scannerView);
    }

    @Override
    public void onResume() {
        super.onResume();
        scannerView.setResultHandler(this);
        scannerView.startCamera();
    }

    @Override
    public void onPause() {
        super.onPause();
        scannerView.stopCamera();
    }

    @Override
    public void handleResult(me.dm7.barcodescanner.zbar.Result result) {

        final String scanResult = result.getContents();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scanned Result");
        builder.setMessage(scanResult);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra(SCAN_RESULT, scanResult);
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {}
}
