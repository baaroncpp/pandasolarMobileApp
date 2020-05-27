package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder> {

    private List<LeasePayment> leasePaymentList;
    private OnPaymentClickListener onPaymentClickListener;

    public PaymentsAdapter(List<LeasePayment> leasePaymentList){
        this.leasePaymentList = leasePaymentList;
    }

    public void setOnPaymentClick(OnPaymentClickListener onPaymentClickListener){
        this.onPaymentClickListener = onPaymentClickListener;
    }

    public interface OnPaymentClickListener{
        void onPaymentClick(int position);
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_payment_list_item, viewGroup,false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(view, onPaymentClickListener);
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder paymentViewHolder, int position) {
        LeasePayment currentPayment = leasePaymentList.get(position);

        paymentViewHolder.paymentPayeeNumber.setText("+"+Utils.insertCharacterForEveryNDistance(3, currentPayment.getPayeemobilenumber(),' '));
        paymentViewHolder.paymentPayee.setText(currentPayment.getPayeename());
        paymentViewHolder.paymentChannel.setText(currentPayment.getPaymentchannel());
        paymentViewHolder.paymentAmount.setText(Utils.moneyFormatter(currentPayment.getAmount()));
        paymentViewHolder.paymentDate.setText(Utils.readableDate(currentPayment.getCreatedon()));

        if(position == leasePaymentList.size() - 1){
            paymentViewHolder.itemView.setPadding(0, 0, 0, 15);
        }
    }

    @Override
    public int getItemCount() {
        return leasePaymentList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder{

        TextView paymentDate;
        TextView paymentChannel;
        TextView paymentAmount;
        TextView paymentPayee;
        TextView paymentPayeeNumber;
        View paymentView;

        public PaymentViewHolder(@NonNull View itemView, final OnPaymentClickListener onPaymentClickListener){
            super(itemView);
            paymentDate = itemView.findViewById(R.id.payment_item_date);
            paymentAmount = itemView.findViewById(R.id.payment_item_amount);
            paymentChannel = itemView.findViewById(R.id.payment_item_channel);
            paymentPayee = itemView.findViewById(R.id.payment_item_payee);
            paymentPayeeNumber = itemView.findViewById(R.id.payment_item_payee_number);
            paymentView = itemView.findViewById(R.id.payment_item_hor_view);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onPaymentClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onPaymentClickListener.onPaymentClick(position);
                        }
                    }
                }
            });
        }
    }

    public void filterList(List<LeasePayment> filteredList){
        leasePaymentList = filteredList;
        notifyDataSetChanged();
    }
}
