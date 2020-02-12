package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.activities.R;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> products;
    private OnItemClickListener productClickListener;

    public interface OnItemClickListener{
        void onProductClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener){
        productClickListener = listener;
    }

    public ProductAdapter(List<Product> product){
        products = product;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView productPriceView;
        TextView productNameView;
        TextView productAvailableView;

        public ProductViewHolder(View itemView, final OnItemClickListener listener){
            super(itemView);
            productPriceView = itemView.findViewById(R.id.sale_product_item_price);
            productNameView = itemView.findViewById(R.id.sale_product_item_name);
            productAvailableView = itemView.findViewById(R.id.sale_product_item_availability);

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

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_product_list_item2, viewGroup,false);
        ProductViewHolder productViewHolder = new ProductViewHolder(view, productClickListener);
        return productViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder productViewHolder, int position) {
        Product currentProduct = products.get(position);

        productViewHolder.productNameView.setText(currentProduct.getName());
        productViewHolder.productPriceView.setText(Float.toString(currentProduct.getUnitcostselling()));
        productViewHolder.productAvailableView.setText("Available");
    }

    @Override
    public int getItemCount() {
        return products.size();
    }
}
