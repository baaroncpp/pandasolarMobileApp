package com.panda.solar.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.panda.solar.activities.R;
import com.panda.solar.Model.entities.SaleProduct;

import java.util.List;

/**
 * Created by macosx on 27/01/2019.
 */

public class SaleProductAdapter extends RecyclerView.Adapter<SaleProductAdapter.SaleProductAdapterViewHolder> {

    private List<SaleProduct> saleProductList;
    private Context context;
    private SaleProductItemClickListener saleProductItemClickListener;

    public SaleProductAdapter(List<SaleProduct> saleProductList, Context context, SaleProductItemClickListener saleProductItemClickListener){
        this.context = context;
        this.saleProductList = saleProductList;
        this.saleProductItemClickListener = saleProductItemClickListener;
    }

    @NonNull
    @Override
    public SaleProductAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.sale_product_list_item, viewGroup, false);
        return new SaleProductAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SaleProductAdapterViewHolder saleProductAdapterViewHolder, int position) {
        final SaleProduct saleProduct = saleProductList.get(position);

        saleProductAdapterViewHolder.productName.setText(saleProduct.getName());
        saleProductAdapterViewHolder.productImageView.setImageResource(R.drawable.ic_sales);

        saleProductAdapterViewHolder.saleProductItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saleProductItemClickListener.onItemClick(saleProduct);
            }
        });


    }

    @Override
    public int getItemCount() {
        return this.saleProductList.size();
    }

    public class SaleProductAdapterViewHolder extends RecyclerView.ViewHolder{

        private TextView productName;
        private ImageView productImageView;
        private LinearLayout saleProductItemContainer;

        public SaleProductAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            productName = (TextView)itemView.findViewById(R.id.product_name);
            productImageView = (ImageView)itemView.findViewById(R.id.product_image_view);
            saleProductItemContainer = (LinearLayout)itemView.findViewById(R.id.sale_product_item_container);
        }
    }

    public interface SaleProductItemClickListener{
        void onItemClick(SaleProduct saleProduct);
    }
}
