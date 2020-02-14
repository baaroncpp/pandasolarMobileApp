package com.panda.solar.presentation.view.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import net.sourceforge.zbar.Symbol;

public class DirectSale extends AppCompatActivity {

    public final static String PRODUCT_RESULT = "saleProductResult";
    private final static String SCAN_RESULT = "barcodeScanResult";
    private final static String CUSTOMER_RESULT = "customerResult";

    private static final int ZBAR_SCANNER_REQUEST = 0;
    private final static int SALE_PRO_CODE = 1;
    private static final int ZBAR_QR_SCANNER_REQUEST = 2;
    private final static int SALE_CUST_CODE = 3;

    private MaterialButton scannerButtonView;
    private MaterialButton productButtonView;
    private MaterialButton customerButtonView;
    private MaterialButton locationButtonView;
    private TextInputEditText serialTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_sale);

        init();

        customerButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleCustomerActivity.class);
                startActivityForResult(intent, SALE_CUST_CODE);
            }
        });

        productButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleProductActivity.class);
                startActivityForResult(intent, SALE_PRO_CODE);
            }
        });

        scannerButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCameraAvailable()) {
                    Intent intent = new Intent(DirectSale.this, BarCodeScanner.class);
                    startActivityForResult(intent, ZBAR_SCANNER_REQUEST);
                } else {
                    Toast.makeText(DirectSale.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void init(){
        scannerButtonView = findViewById(R.id.scanner_button);
        productButtonView = findViewById(R.id.add_product_button);
        customerButtonView = findViewById(R.id.add_customer_button);
        locationButtonView = findViewById(R.id.set_location_button);
        serialTextView = findViewById(R.id.serialnumber_text_field);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    String serialNumberResult = data.getStringExtra(SCAN_RESULT);
                    serialTextView.setText(serialNumberResult);
                    Toast.makeText(this, "Scan Result = " + serialNumberResult, Toast.LENGTH_SHORT).show();
                } else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case ZBAR_QR_SCANNER_REQUEST:

                break;

            case SALE_PRO_CODE:

                if(resultCode == RESULT_OK){
                    Product productResult = data.getParcelableExtra(PRODUCT_RESULT);
                    productButtonView.setText(productResult.getName());
                    serialTextView.setText(productResult.getSerialNumber());
                    Toast.makeText(DirectSale.this, productResult.getName(), Toast.LENGTH_SHORT).show();
                }
                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(DirectSale.this, "No product selected", Toast.LENGTH_SHORT).show();
                }
                break;

            case SALE_CUST_CODE:

                if(resultCode == RESULT_OK){
                    Customer customerResult = data.getParcelableExtra(CUSTOMER_RESULT);
                    customerButtonView.setText(customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname());
                    Toast.makeText(DirectSale.this, customerResult.getUser().getFirstname(), Toast.LENGTH_SHORT).show();
                }
                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(DirectSale.this, "No customer selected", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }

    public boolean isCameraAvailable() {
        PackageManager pm = getPackageManager();
        return pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

}
