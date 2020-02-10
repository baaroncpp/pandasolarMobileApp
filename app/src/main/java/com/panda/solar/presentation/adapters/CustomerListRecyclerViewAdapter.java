package com.panda.solar.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.panda.solar.activities.R;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.presentation.view.activities.CustomerDetails;

import java.util.ArrayList;
import java.util.List;

public class CustomerListRecyclerViewAdapter extends RecyclerView.Adapter<CustomerListRecyclerViewAdapter.ViewHolder> implements Filterable {

    private List<Customer> customers = new ArrayList<>();
    private List<Customer> customerCopy = new ArrayList<>();
    private Context context;
    private OnCustomerListener onCustomerListener;

    public CustomerListRecyclerViewAdapter(Context context){
        super();
        this.context = context;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        this.customerCopy = customers;
        //notifyDataSetChanged();
    }

    public Customer getCustomerAt(int position){
        return customers.get(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {

        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.customer_list_item, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(view);


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        ((ViewHolder)viewHolder).bindView(position);
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnCustomerListener{

        ImageView image;
        TextView customerName;
        TextView customerAddress;
        TextView phoneNumber;
        OnCustomerListener onCustomerListener;

        public ViewHolder(View itemView){
            super(itemView);

            //image = (ImageView)itemView.findViewById(R.id.customer_image);
            customerName = (TextView) itemView.findViewById(R.id.customer_name);
            customerAddress = (TextView)itemView.findViewById(R.id.customer_address);
            phoneNumber = (TextView)itemView.findViewById(R.id.customer_phone_number);
            this.onCustomerListener = onCustomerListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Toast.makeText(v.getContext(), String.valueOf(getAdapterPosition()), Toast.LENGTH_SHORT).show();
                    int position = getAdapterPosition();

                    Customer customer = customers.get(position);

                    Intent intent = new Intent(context, CustomerDetails.class);
                    intent.putExtra("customerObject",customer);
                    context.startActivity(intent);

                    if(onCustomerListener != null && position != RecyclerView.NO_POSITION){
                        onCustomerListener.onCustomerClick(customers.get(position));
                    }
                }
            });
        }

        public void bindView(int position){
            Customer customer = customers.get(position);

            //viewHolder.image.setImageDrawable(context.getDrawable(R.drawable.ic_home_black_24dp));
            customerName.setText(customer.getUser().getLastname()+", "+customer.getUser().getFirstname());
            customerAddress.setText(customer.getAddress());
            phoneNumber.setText(customer.getUser().getPrimaryphone());

            //Glide.with(context).load(customer.getProfilephotopath()).into(image);

        }


        @Override
        public void onCustomerClick(Customer customer) {
            //Toast.makeText(, "Open the list of numbers", Toast.LENGTH_SHORT).show();
        }
    }

    public interface OnCustomerListener{
        void onCustomerClick(Customer customer);
    }

    public void setOnCustomerListener(OnCustomerListener onCustomerListener){
        this.onCustomerListener = onCustomerListener;
    }

    @Override
    public Filter getFilter(){
        return customerFilter;
    }

    public Filter customerFilter = new Filter(){

        List<Customer> filteredCustomers = new ArrayList<>();

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint == null || constraint.length() == 0){
                filteredCustomers.addAll(customerCopy);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                for(Customer obj : customers){
                    if((obj.getUser().getLastname().toLowerCase().contains(filterPattern)) || (obj.getUser().getFirstname().toLowerCase().contains(filterPattern))){
                        filteredCustomers.add(obj);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredCustomers;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            customers.clear();
            customers.addAll((List)results.values);
            notifyDataSetChanged();
        }
    };


}