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
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CustomerListRecyclerViewAdapter extends RecyclerView.Adapter<CustomerListRecyclerViewAdapter.CustomerViewHolder> /*implements Filterable*/ {

    /*private List<Customer> customers = new ArrayList<>();
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
        if(position == customers.size() - 1){
            viewHolder.itemView.setPadding(0, 0, 0, 15);
        }
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

            image = (ImageView)itemView.findViewById(R.id.profile_customer_list_item);
            customerName = (TextView) itemView.findViewById(R.id.customer_name);
            customerAddress = (TextView)itemView.findViewById(R.id.customer_address);
            phoneNumber = (TextView)itemView.findViewById(R.id.customer_phone_number);
            this.onCustomerListener = onCustomerListener;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position = getAdapterPosition();

                    Customer customer = customers.get(position);

                    Intent intent = new Intent(context, CustomerDetails.class);
                    intent.putExtra(Constants.CUSTOMER_OBJECT,customer);
                    context.startActivity(intent);

                    if(onCustomerListener != null && position != RecyclerView.NO_POSITION){
                        onCustomerListener.onCustomerClick(customers.get(position));
                    }
                }
            });
        }

        public void bindView(int position){
            Customer customer = customers.get(position);

            customerName.setText(customer.getUser().getLastname()+", "+customer.getUser().getFirstname());
            customerAddress.setText(customer.getAddress());
            phoneNumber.setText("+"+ Utils.insertCharacterForEveryNDistance(3,customer.getUser().getPrimaryphone(), ' '));

            Picasso.with(context).load(customer.getProfilephotopath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(image);

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
    }*/

    /*
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
*/
    private List<Customer> customers;
    private List<Customer> customerCopy = new ArrayList<>();
    private Context context;
    private OnCustomerClickListener customerClickListener;

    public interface OnCustomerClickListener{
        void onCustomerClick(int position);
    }

    public CustomerListRecyclerViewAdapter(List<Customer> customers, Context context){
        this.customers = customers;
        this.context = context;
    }

    public void setOnCustomerClickListener(OnCustomerClickListener customerClickListener){
        this.customerClickListener = customerClickListener;
    }

    @NonNull
    @Override
    public CustomerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.customer_list_item, viewGroup,false);
        CustomerViewHolder customerViewHolder = new CustomerViewHolder(view, customerClickListener);
        return customerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerViewHolder customerViewHolder, int position) {

        Customer currentCustomer = customers.get(position);

        customerViewHolder.customerName.setText(currentCustomer.getUser().getLastname()+", "+currentCustomer.getUser().getFirstname());
        customerViewHolder.customerAddress.setText(currentCustomer.getAddress());

        if(currentCustomer.getUser().isIsapproved()){
            customerViewHolder.activeCust.setImageResource(R.drawable.ic_approve_24dp);
        }else{
            customerViewHolder.activeCust.setImageResource(R.drawable.ic_pend_24dp);
        }

        customerViewHolder.phoneNumber.setText("+"+ Utils.insertCharacterForEveryNDistance(3,currentCustomer.getUser().getPrimaryphone(), ' '));

        Picasso.with(context).load(currentCustomer.getProfilephotopath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(customerViewHolder.image);

        if(position == customers.size() - 1){
            customerViewHolder.itemView.setPadding(0, 0, 0, 15);
        }
    }

    @Override
    public int getItemCount() {
        return customers.size();
    }

    public static class CustomerViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        TextView customerName;
        TextView customerAddress;
        TextView phoneNumber;
        ImageView activeCust;

        public CustomerViewHolder(@NonNull View itemView, final OnCustomerClickListener customerClickListener) {
            super(itemView);
            image = itemView.findViewById(R.id.profile_customer_list_item);
            customerName = itemView.findViewById(R.id.customer_name);
            customerAddress = itemView.findViewById(R.id.customer_address);
            phoneNumber = itemView.findViewById(R.id.customer_phone_number);
            activeCust = itemView.findViewById(R.id.active_customer);

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