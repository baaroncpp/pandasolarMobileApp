package com.panda.solar.presentation.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Utils;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {

    private List<SaleModel> sales;
    private SaleOnClickListener saleOnClickListener;
    private Context context;

    public interface SaleOnClickListener{
        void onSaleItemClick(int position);
    }

    public SalesAdapter(List<SaleModel> sales){
        this.sales = sales;
    }

    public void setSaleOnClickListener(SaleOnClickListener saleOnClickListener){
        this.saleOnClickListener = saleOnClickListener;
    }

    public static class SalesViewHolder extends RecyclerView.ViewHolder{

        CircleImageView agentPic;
        TextView productName;
        TextView agentName;
        TextView saleDetail;
        TextView saleDate;
        TextView saleStatus;

        public SalesViewHolder(@NonNull View itemView, final SaleOnClickListener saleOnClickListener) {
            super(itemView);
            agentPic = itemView.findViewById(R.id.sale_item_agent_pic);
            productName = itemView.findViewById(R.id.sale_prod_name);
            agentName = itemView.findViewById(R.id.sale_agent_name);
            saleDetail = itemView.findViewById(R.id.sale_detail);
            saleDate = itemView.findViewById(R.id.sale_date);
            saleStatus = itemView.findViewById(R.id.sale_status);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(saleOnClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            saleOnClickListener.onSaleItemClick(position);
                        }
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public SalesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_sale_item, viewGroup,false);
        SalesViewHolder salesViewHolder = new SalesViewHolder(view, saleOnClickListener);
        return salesViewHolder;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull SalesViewHolder salesViewHolder, int position) {

        SaleModel currentSale = sales.get(position);

        //salesViewHolder.agentPic.setImageURI(null);
        salesViewHolder.productName.setText(currentSale.getProduct().getName());
        salesViewHolder.agentName.setText(currentSale.getAgent().getUser().getFirstname()+" "+currentSale.getAgent().getUser().getLastname());
        salesViewHolder.saleDetail.setText(currentSale.getSaletype()+" Sale");
        salesViewHolder.saleDate.setText(Utils.readableShortDate(currentSale.getCreatedon()));
        salesViewHolder.saleStatus.setText(Utils.saleStatus(currentSale.getSalestatus()));

        if(currentSale.getSalestatus() == 2){
            //salesViewHolder.saleStatus.setTextColor(android.R.color.holo_orange_light);
        }

    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

}
