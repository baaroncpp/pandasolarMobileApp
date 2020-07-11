package com.panda.solar.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.button.MaterialButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder> {

    private List<LeasePayment> leasePaymentList;
    private OnPaymentClickListener onPaymentClickListener;
    private static String paymentType;
    private Context context;

    public PaymentsAdapter(List<LeasePayment> leasePaymentList, String paymentType, Context context){
        this.leasePaymentList = leasePaymentList;
        this.paymentType = paymentType;
        this.context = context;
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
        View view;
        PaymentViewHolder paymentViewHolder = null;

        if(paymentType.equals(Constants.DASH_PAYMENTS)){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_payment_list_item, viewGroup,false);
            paymentViewHolder = new PaymentViewHolder(view, onPaymentClickListener);
        }else if(paymentType.equals(Constants.REPORT_PAYMENTS)){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.report_payment_item, viewGroup,false);
            paymentViewHolder = new PaymentViewHolder(view, onPaymentClickListener);
        }
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder paymentViewHolder, int position) {
        LeasePayment currentPayment = leasePaymentList.get(position);

        if(paymentType.equals(Constants.DASH_PAYMENTS)){
            paymentViewHolder.paymentPayeeNumber.setText("+"+Utils.insertCharacterForEveryNDistance(3, currentPayment.getPayeemobilenumber(),' '));
            paymentViewHolder.paymentPayee.setText(currentPayment.getPayeename());
            paymentViewHolder.paymentChannel.setText(currentPayment.getPaymentchannel());
            paymentViewHolder.paymentAmount.setText(Utils.moneyFormatter(currentPayment.getAmount()));
            paymentViewHolder.paymentDate.setText(Utils.readableDate(currentPayment.getCreatedon()));

            if(position == leasePaymentList.size() - 1){
                paymentViewHolder.itemView.setPadding(0, 0, 0, 15);
            }
        }else if(paymentType.equals(Constants.REPORT_PAYMENTS)){
            paymentViewHolder.reportPaymentCustName.setText(currentPayment.getPayeename());
            paymentViewHolder.reportPaymentAmount.setText(Utils.moneyFormatter(currentPayment.getAmount()));
            paymentViewHolder.saleDetailBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context,"test", Toast.LENGTH_SHORT).show();
                }
            });
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

        //report payment
        MaterialButton saleDetailBtn;
        TextView reportPaymentAmount;
        TextView reportPaymentCustName;

        public PaymentViewHolder(@NonNull View itemView, final OnPaymentClickListener onPaymentClickListener){
            super(itemView);

            if(paymentType.equals(Constants.DASH_PAYMENTS)){
                paymentDate = itemView.findViewById(R.id.payment_item_date);
                paymentAmount = itemView.findViewById(R.id.payment_item_amount);
                paymentChannel = itemView.findViewById(R.id.payment_item_channel);
                paymentPayee = itemView.findViewById(R.id.payment_item_payee);
                paymentPayeeNumber = itemView.findViewById(R.id.payment_item_payee_number);
                paymentView = itemView.findViewById(R.id.payment_item_hor_view);
            }else if(paymentType.equals(Constants.REPORT_PAYMENTS)){
                reportPaymentAmount = itemView.findViewById(R.id.report_payment_item_amount);
                reportPaymentCustName = itemView.findViewById(R.id.report_payment_item_customer_name);
                saleDetailBtn = itemView.findViewById(R.id.report_payment_item_btn);
            }

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
