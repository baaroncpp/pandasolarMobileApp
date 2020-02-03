package com.panda.solar.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.panda.solar.activities.R;
import com.panda.solar.Model.entities.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerListRecyclerViewAdapter extends RecyclerView.Adapter<CustomerListRecyclerViewAdapter.ViewHolder> {

    private List<Customer> customers = new ArrayList<>();
    private Context context;
    private OnCustomerListener onCustomerListener;

    /*public CustomerListRecyclerViewAdapter(List<Customer> customers, LoaderManager.LoaderCallbacks<List<Customer>> listLoaderCallbacks) {
        this.customers = customers;
    }*/

    public CustomerListRecyclerViewAdapter(Context context){
        super();
        this.context = context;
    }

    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
        notifyDataSetChanged();
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

    public class ViewHolder extends RecyclerView.ViewHolder{

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
                    int position = getAdapterPosition();

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

        /*public void onClick(View view){
            //Toast.makeText(view.getContext(), "Open the list of numbers", Toast.LENGTH_SHORT).show();
            onCustomerListener.onCustomerClick(getAdapterPosition());
        }*/
    }

    public interface OnCustomerListener{
        void onCustomerClick(Customer customer);
    }

    public void setOnCustomerListener(OnCustomerListener onCustomerListener){
        this.onCustomerListener = onCustomerListener;
    }
}