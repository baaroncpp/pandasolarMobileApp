package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.ProductAdapter;
import com.panda.solar.viewModel.ProductViewModel;

import java.util.List;

public class SaleProductActivity extends AppCompatActivity {

    private RecyclerView saleProductRecycler;
    private ProductAdapter productAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private LiveData<List<Product>> productList;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_product);

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productList = productViewModel.getProducts();

        productList.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                buildRecyclerView(products);
            }
        });
        
    }

    public void buildRecyclerView(List<Product> products){

        saleProductRecycler = findViewById(R.id.product_sale_recyclerview);
        layoutManager = new LinearLayoutManager(this);
        saleProductRecycler.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(products);
        saleProductRecycler.setAdapter(productAdapter);

        productAdapter.setOnItemClickListener(new ProductAdapter.OnItemClickListener() {
            @Override
            public void onProductClick(int position) {
                Toast.makeText(SaleProductActivity.this,"aaa",Toast.LENGTH_SHORT).show();
            }
        });

    }
}