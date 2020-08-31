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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SalesAdapter extends RecyclerView.Adapter<SalesAdapter.SalesViewHolder> {

    private List<SaleModel> sales;
    private SaleOnClickListener saleOnClickListener;
    private Context context;

    public interface SaleOnClickListener{
        void onSaleItemClick(int position);
    }

    public SalesAdapter(List<SaleModel> sales, Context context){
        this.sales = sales;
        this.context = context;
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
        salesViewHolder.agentName.setText(currentSale.getCustomer().getUser().getFirstname()+" "+currentSale.getCustomer().getUser().getLastname());
        salesViewHolder.saleDetail.setText(currentSale.getSaletype()+" Sale");
        salesViewHolder.saleDate.setText(Utils.readableShortDate(currentSale.getCreatedon()));
        salesViewHolder.saleStatus.setText(Utils.saleStatus(currentSale.getSalestatus()));

        Picasso.with(context).load(currentSale.getAgent().getProfilepath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(salesViewHolder.agentPic);

        if(position == sales.size() - 1){
            salesViewHolder.itemView.setPadding(0, 0, 0, 15);
        }

    }

    @Override
    public int getItemCount() {
        return sales.size();
    }

    public void filterList(List<SaleModel> filteredList){
        sales = filteredList;
        notifyDataSetChanged();
    }

}
