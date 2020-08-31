package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.ProductAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.ProductViewModel;

import java.util.ArrayList;
import java.util.List;

public class SaleProductActivity extends AppCompatActivity {

    private RecyclerView saleProductRecycler;
    private ProductAdapter productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LiveData<List<Product>> productList;
    private ProductViewModel productViewModel;
    private LiveData<String> responseMessage;
    private ProgressDialog dialog;
    private TextView errorView;
    private List<Product> nonleasable_product = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_product);

        init();

        dialog.show();
        productList.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                buildRecyclerView(products);
            }
        });
        observeResponse();
    }

    public void init(){
        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productList = productViewModel.getProducts();
        dialog = Utils.customerProgressBar(this);
        errorView = findViewById(R.id.sale_product_error_view);
    }

    public void buildRecyclerView(final List<Product> products){

        //filter to non lease products
        for(Product object : products){
            if(!object.getIsleasable()){
                nonleasable_product.add(object);
            }
        }

        saleProductRecycler = findViewById(R.id.product_sale_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        saleProductRecycler.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(nonleasable_product, Constants.PRODUCT_LIST_SALE, this);
        saleProductRecycler.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onProductClick(int position) {

                Intent resultIntent = new Intent();
                resultIntent.putExtra(Constants.PRODUCT_RESULT, nonleasable_product.get(position));
                setResult(RESULT_OK, resultIntent);
                finish();

            }
        });

    }

    public void observeResponse(){
        responseMessage = productViewModel.getResponseMessage();
        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            dialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            dialog.dismiss();

            saleProductRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Items Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();

            saleProductRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO CONNECTION");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }
}
