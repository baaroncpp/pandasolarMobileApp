package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.ProductAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.viewModel.ProductViewModel;
import java.util.List;

public class ProductListDashBoard extends AppCompatActivity {

    private ProductAdapter productAdapter;
    private RecyclerView productRecycler;
    private RecyclerView.LayoutManager layoutManager;
    private LiveData<List<Product>> productList;
    private ProductViewModel productViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list_dash_board);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productList = productViewModel.getProducts();

        productList.observe(this, new Observer<List<Product>>() {
            @Override
            public void onChanged(@Nullable List<Product> products) {
                buildRecyclerView(products);
            }
        });
    }

    public  void buildRecyclerView(List<Product> products){
        productRecycler = findViewById(R.id.product_recyclerview_dash);
        layoutManager = new LinearLayoutManager(this);
        productRecycler.setLayoutManager(layoutManager);

        productAdapter = new ProductAdapter(products, Constants.PRODUCT_LIST_DASH);
        productRecycler.setAdapter(productAdapter);
    }
}