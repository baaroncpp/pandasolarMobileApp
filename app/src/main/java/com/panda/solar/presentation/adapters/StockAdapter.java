package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panda.solar.Model.entities.StockProduct;
import com.panda.solar.activities.R;

import java.util.List;

public class StockAdapter extends RecyclerView.Adapter<StockAdapter.StockViewHolder> {

    private List<StockProduct> stockProducts;

    public StockAdapter(List<StockProduct> stockProducts){
        this.stockProducts = stockProducts;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.stock_list_item, viewGroup,false);
        StockViewHolder stockViewHolder = new StockViewHolder(view);
        return stockViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull StockViewHolder stockViewHolder, int position) {
        StockProduct stockProduct = stockProducts.get(position);

        stockViewHolder.leaseOfferName.setText(stockProduct.getLeaseOffer().getName());
        stockViewHolder.productName.setText(stockProduct.getLeaseOffer().getProduct().getName());
        stockViewHolder.stockValue.setText(stockProduct.getStockValue().toString());
    }

    @Override
    public int getItemCount() {
        return stockProducts.size();
    }

    public static class StockViewHolder extends RecyclerView.ViewHolder{

        TextView productName;
        TextView leaseOfferName;
        TextView stockValue;

        public StockViewHolder(@NonNull View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.stock_product_name);
            leaseOfferName = itemView.findViewById(R.id.pay_go_leaseName);
            stockValue = itemView.findViewById(R.id.pay_go_stock_value);
        }
    }


}
