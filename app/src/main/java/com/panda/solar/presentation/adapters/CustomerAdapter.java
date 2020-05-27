package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerViewHolder> {

    private List<Customer> customers;
    private OnCustomerClickListener customerClickListener;

    public interface OnCustomerClickListener{
        void onCustomerClick(int position);
    }

    public CustomerAdapter(List<Customer> customers){
        this.customers = customers;
    }

    public void setOnCustomerClickListener(OnCustomerClickListener customerClickListener){
        this.customerClickListener = customerClickListener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_product_list_item2, viewGroup,false);
        CustomerViewHolder customerViewHolder = new CustomerViewHolder(view, customerClickListener);
        return customerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder customerViewHolder, int position) {

        Customer currentCustomer = customers.get(position);

        customerViewHolder.customerNameView.setText(currentCustomer.getUser().getFirstname()+" "+currentCustomer.getUser().getLastname());
        customerViewHolder.customerLocationView.setText(currentCustomer.getAddress());
        customerViewHolder.customerRegDateView.setText(Utils.readableDate(currentCustomer.getCreatedon()));

        if(position == customers.size() - 1){
            customerViewHolder.itemView.setPadding(0, 0, 0, 15);
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder{

        TextView customerNameView;
        TextView customerLocationView;
        TextView customerRegDateView;

        public CustomerViewHolder(@NonNull View itemView, final OnCustomerClickListener customerClickListener) {
            super(itemView);
            customerNameView = itemView.findViewById(R.id.sale_product_item_price);
            customerLocationView = itemView.findViewById(R.id.sale_product_item_name);
            customerRegDateView = itemView.findViewById(R.id.sale_product_item_availability);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(customerClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            customerClickListener.onCustomerClick(position);
                        }
                    }
                }
            });
        }
    }

    public void filterList(ArrayList<Customer> filteredList){
        customers = filteredList;
        notifyDataSetChanged();
    }
}