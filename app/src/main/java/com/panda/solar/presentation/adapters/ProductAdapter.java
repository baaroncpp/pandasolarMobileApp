package com.panda.solar.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private OnItemClickListener productClickListener;
    private static int productListConst;
    private Context context;

    public interface OnItemClickListener{
        void onProductClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        productClickListener = listener;
    }

    public ProductAdapter(List<Product> product, int productListConst, Context context){
        this.products = product;
        this.productListConst = productListConst;
        this.context = context;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView productPriceView;
        TextView productNameView;
        TextView productAvailableView;

        //dashboard products
        TextView dashProductTitle;
        TextView dashProductContent;
        TextView dashProductLeasable;
        TextView dashProductAvailable;
        ImageView dashProductImage;
        ProductEquipementAdapter productEquipementAdapter;
        RecyclerView equipRecycler;
        //RecyclerView.LayoutManager layoutManager;

        public ProductViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);

            if(productListConst == Constants.PRODUCT_LIST_SALE){

                productPriceView = itemView.findViewById(R.id.sale_product_item_price);
                productNameView = itemView.findViewById(R.id.sale_product_item_name);
                productAvailableView = itemView.findViewById(R.id.sale_product_item_availability);

            }else if(productListConst == Constants.PRODUCT_LIST_DASH){

                dashProductTitle = itemView.findViewById(R.id.product_title);
                dashProductContent = itemView.findViewById(R.id.product_content);
                dashProductAvailable = itemView.findViewById(R.id.product_available);
                dashProductLeasable = itemView.findViewById(R.id.product_leasable);
                dashProductImage = itemView.findViewById(R.id.productimage_item_dash);
                equipRecycler = itemView.findViewById(R.id.product_equipement_recycler);

            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            listener.onProductClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view;
        ProductViewHolder productViewHolder = null;

        if(productListConst == Constants.PRODUCT_LIST_SALE){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_product_list_item2, viewGroup,false);
            productViewHolder = new ProductViewHolder(view, productClickListener);
        }else if(productListConst == Constants.PRODUCT_LIST_DASH){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_product_item, viewGroup,false);
            productViewHolder = new ProductViewHolder(view, productClickListener);

        }
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        Product currentProduct = products.get(position);

        if(productListConst == Constants.PRODUCT_LIST_SALE){

            productViewHolder.productNameView.setText(currentProduct.getName());
            productViewHolder.productPriceView.setText(Float.toString(currentProduct.getUnitcostselling()));
            productViewHolder.productAvailableView.setText("Available");

        }else if(productListConst == Constants.PRODUCT_LIST_DASH){

            productViewHolder.dashProductTitle.setText(currentProduct.getName());
            productViewHolder.dashProductContent.setText(currentProduct.getDescription());
            productViewHolder.dashProductLeasable.setText(isLeasable(currentProduct.getIsleasable()));
            productViewHolder.dashProductAvailable.setText("Available");

            Picasso.with(context).load(currentProduct.getThumbnail()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(productViewHolder.dashProductImage);
            ProductEquipementAdapter productEquipementAdapter = new ProductEquipementAdapter(Arrays.asList(currentProduct.getEquipmentlist()));
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            productViewHolder.equipRecycler.setLayoutManager(layoutManager);
            productViewHolder.equipRecycler.setAdapter(productEquipementAdapter);

        }

        if(position == products.size() - 1){
            productViewHolder.itemView.setPadding(0, 0, 0, 15);
        }

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public String isLeasable(boolean val){
        if(val){
            return "Leasable";
        }else {
            return "Non Leasable";
        }
    }
}
