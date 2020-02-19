package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.DirectSaleModel;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.viewModel.ProductViewModel;

import java.util.Date;

public class DirectSale extends AppCompatActivity {

    private MaterialButton scannerButtonView;
    private MaterialButton productButtonView;
    private MaterialButton customerButtonView;
    private MaterialButton locationButtonView;
    private TextInputEditText serialTextView;
    private MaterialButton directSaleSubmitBtn;
    private TextInputLayout serialWrapper;
    private TextInputEditText directSaledescription;
    private TextInputLayout directSaleDescriptionWrapper;
    private ProductViewModel productViewModel;
    /*private ImageButton quantityAdd;
    private ImageButton quantitySubtract;
    private TextView quantityText;*/

    private DirectSaleModel directSaleModel;
    private Product directSaleProduct;
    private Customer customerResult;

    private String customerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_sale);

        init();

        customerButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleCustomerActivity.class);
                startActivityForResult(intent, Constants.SALE_CUST_CODE);
            }
        });

        /*productButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleProductActivity.class);
                startActivityForResult(intent, SALE_PRO_CODE);
            }
        });*/

        scannerButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCameraAvailable()) {
                    Intent intent = new Intent(DirectSale.this, BarCodeScanner.class);
                    startActivityForResult(intent, Constants.ZBAR_SCANNER_REQUEST);
                } else {
                    Toast.makeText(DirectSale.this, "Rear Facing Camera Unavailable", Toast.LENGTH_SHORT).show();
                }
            }
        });

        directSaleSubmitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DirectSale.this, SaleReview.class);
                if(validateSerialEditView() && validateCustomerBtn() && validateDescriptionField() && validateLocationBtn()){

                    directSaleModel = new DirectSaleModel();
                    directSaleModel.setAgentid("y5r677tg9yh99juj");
                    directSaleModel.setCreatedon(new Date());
                    directSaleModel.setScannedserial(serialTextView.getText().toString());
                    directSaleModel.setCustomerid(customerResult.getUserid());
                    //directSaleModel.setQuantity(Integer.parseInt(quantityText.getText().toString()));
                    directSaleModel.setDescription(directSaledescription.getText().toString());
                    directSaleModel.setLat(43);
                    directSaleModel.setLong_(54);

                    customerName = customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname();

                    directSaleProduct = findProductBySerialNumber(directSaleModel.getScannedserial());

                    intent.putExtra(Constants.CUSTOMER_NAME, customerName);
                    intent.putExtra(Constants.DIRECT_SALE_OBJ, directSaleModel);
                    intent.putExtra(Constants.PROD_SALE_OBJ, directSaleProduct);
                    startActivity(intent);
                }
            }
        });

        /*quantityAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity < 10 && quantity != 10){
                    quantity = quantity + 1;
                    quantityText.setText(String.valueOf(quantity));
                }
            }
        });

        quantitySubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity > 0 && quantity != 1){
                    quantity = quantity - 1;
                    quantityText.setText(String.valueOf(quantity));
                }
            }
        });*/

    }

    public void init(){
        scannerButtonView = findViewById(R.id.scanner_button);
        customerButtonView = findViewById(R.id.add_customer_button);
        locationButtonView = findViewById(R.id.set_location_button);
        serialTextView = findViewById(R.id.serialnumber_text_field);
        directSaleSubmitBtn = findViewById(R.id.submit_direct_sale);
        serialWrapper = findViewById(R.id.serialnumber_text_wrapper);
        directSaledescription = findViewById(R.id.direct_sale_description);
        directSaleDescriptionWrapper = findViewById(R.id.description_wrapper);
    }

    public Product findProductBySerialNumber(String serialNumber){
        final Product result = new Product();
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        LiveData<Product> liveProduct = productViewModel.getProductBySerialNumber(serialNumber);
        liveProduct.observe(this, new Observer<Product>() {
            @Override
            public void onChanged(@Nullable Product product) {
                result.setUnitcostselling(product.getUnitcostselling());
                result.setName(product.getName());
                result.setSerialNumber(product.getSerialNumber());
            }
        });
        Product product = new Product();
        product.setName("Boom box Panda Solar");
        product.setUnitcostselling(400000);
        product.setSerialNumber("8991389741924");

        return product;
    }

    public boolean validateProductToken(String tokenSerialNumber){
        return true;
    }

    public boolean validateSubmit(){

        if(validateSerialEditView() && validateCustomerBtn() && validateDescriptionField() && validateLocationBtn()){
            directSaleModel = new DirectSaleModel();
            directSaleModel.setAgentid("");
            directSaleModel.setCreatedon(new Date());
            directSaleModel.setScannedserial(serialTextView.getText().toString());
            directSaleModel.setCustomerid(customerResult.getUserid());
            //directSaleModel.setQuantity(Integer.parseInt(quantityText.getText().toString()));
            directSaleModel.setDescription(directSaledescription.getText().toString());
            directSaleModel.setLat(43);
            directSaleModel.setLong_(54);

            customerName = customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname();

            directSaleProduct = new Product();
            directSaleProduct = findProductBySerialNumber(directSaleModel.getScannedserial());

            return true;
        }else{
            return false;
        }


    }


    public boolean validateSerialEditView(){
        String text = serialTextView.getText().toString();
        if(text.isEmpty()){
            serialWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            serialTextView.setTextColor(getResources().getColor(R.color.dark_grey));
            Toast.makeText(this, "Please Enter Product Serial", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            serialWrapper.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
            serialTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
            return true;
        }
    }


    /*public boolean validateProductBtn(){
        String text = productButtonView.getText().toString();

        if(!text.equalsIgnoreCase("Add Product")){
            productButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            productButtonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            productButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_add_product_yellow));
            productButtonView.setAllCaps(false);
            return true;
        }else{
            productButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            productButtonView.setTextColor(getResources().getColor(R.color.dark_grey));
            productButtonView.setIcon(getResources().getDrawable(R.drawable.ic_add_product));
            productButtonView.setAllCaps(true);
            return false;
        }
    }*/

    public boolean validateCustomerBtn(){
        String text = customerButtonView.getText().toString();

        if(!text.equalsIgnoreCase("Add Customer")){
            customerButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            customerButtonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            customerButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_add_person_add_yellow));
            customerButtonView.setAllCaps(false);
            return true;
        }else{
            customerButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            customerButtonView.setTextColor(getResources().getColor(R.color.dark_grey));
            customerButtonView.setIcon(getResources().getDrawable(R.drawable.ic_person_add_black_24dp));
            customerButtonView.setAllCaps(true);
            Toast.makeText(this, "Please Add a Customer", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    public boolean validateLocationBtn(){
        String text = locationButtonView.getText().toString();

        if(!text.equalsIgnoreCase("Set Location")){
            locationButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimary)));
            locationButtonView.setTextColor(getResources().getColor(R.color.colorPrimary));
            locationButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_location_yellow));
            locationButtonView.setAllCaps(false);
            return true;
        }else{
            locationButtonView.setStrokeColor(ColorStateList.valueOf(getResources().getColor(R.color.dark_grey)));
            locationButtonView.setTextColor(getResources().getColor(R.color.dark_grey));
            locationButtonView.setIcon(getResources().getDrawable(R.drawable.ic_panda_location_black));
            locationButtonView.setAllCaps(false);
            Toast.makeText(this, "Please Set The Customer Location", Toast.LENGTH_SHORT).show();
            return true;
        }
    }


    public boolean validateDescriptionField(){
        String text = directSaledescription.getText().toString();
        if(text.isEmpty()){
            directSaleDescriptionWrapper.setBoxStrokeColor(getResources().getColor(R.color.dark_grey));
            directSaledescription.setTextColor(getResources().getColor(R.color.dark_grey));
            Toast.makeText(this, "Please Enter Sale Description", Toast.LENGTH_SHORT).show();
            return false;
        }else{
            directSaleDescriptionWrapper.setBoxStrokeColor(getResources().getColor(R.color.colorPrimary));
            directSaledescription.setTextColor(getResources().getColor(R.color.colorPrimary));
            return true;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case Constants.ZBAR_SCANNER_REQUEST:
                if (resultCode == RESULT_OK)
                {
                    String serialNumberResult = data.getStringExtra(Constants.SCAN_RESULT);

                    if(validateProductToken(serialNumberResult)){
                        serialTextView.setText(serialNumberResult);
                        //productButtonView.setText(findProductBySerialNumber(serialNumberResult).getName());
                        //validateProductBtn();
                        validateSerialEditView();
                    }else{
                        Toast.makeText(this, "Product not registered by PANDASOLAR", Toast.LENGTH_SHORT).show();
                    }
                } else if(resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Camera unavailable", Toast.LENGTH_SHORT).show();
                }

                break;

            case Constants.ZBAR_QR_SCANNER_REQUEST:

                break;

            case Constants.SALE_PRO_CODE:

                if(resultCode == RESULT_OK){
                    Product productResult = data.getParcelableExtra(Constants.PRODUCT_RESULT);
                    productButtonView.setText(productResult.getName());
                    serialTextView.setText(productResult.getSerialNumber());

                    validateSerialEditView();
                    //validateProductBtn();

                    Toast.makeText(DirectSale.this, productResult.getName(), Toast.LENGTH_SHORT).show();
                }
                if(resultCode == RESULT_CANCELED){
                    Toast.makeText(DirectSale.this, "No product selected", Toast.LENGTH_SHORT).show();
                }
                break;

            case Constants.SALE_CUST_CODE:

                if(resultCode == RESULT_OK){
                    customerResult = data.getParcelableExtra(Constants.CUSTOMER_RESULT);
                    customerButtonView.setText(customerResult.getUser().getFirstname()+" "+customerResult.getUser().getLastname());
                    validateCustomerBtn();
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
