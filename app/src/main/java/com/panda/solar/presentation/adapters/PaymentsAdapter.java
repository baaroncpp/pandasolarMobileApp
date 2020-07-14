package com.panda.solar.presentation.adapters;

import android.app.ProgressDialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.DeviceToken;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.presentation.view.activities.SaleStatistics;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentsAdapter extends RecyclerView.Adapter<PaymentsAdapter.PaymentViewHolder> {

    private List<LeasePayment> leasePaymentList;
    private OnPaymentClickListener onPaymentClickListener;
    private static String paymentType;
    private Context context;
    private ProgressDialog dialog;
    private SaleViewModel saleViewModel;

    public PaymentsAdapter(List<LeasePayment> leasePaymentList, String paymentType, Context context){
        this.leasePaymentList = leasePaymentList;
        this.paymentType = paymentType;
        this.context = context;
        this.dialog = Utils.customerProgressBar(context);
        this.saleViewModel = ViewModelProviders.of((FragmentActivity) context).get(SaleViewModel.class);
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
        }else if(paymentType.equals(Constants.SALE_PAYMENTS)){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.sale_statistic_payment_item, viewGroup,false);
            paymentViewHolder = new PaymentViewHolder(view, onPaymentClickListener);
        }
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentViewHolder paymentViewHolder, int position) {
        final LeasePayment currentPayment = leasePaymentList.get(position);

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
                    Intent intent = new Intent(context, SaleStatistics.class);
                    intent.putExtra(Constants.LEASE_ID, currentPayment.getLeaseid());
                    context.startActivity(intent);
                    //Toast.makeText(context,"test", Toast.LENGTH_SHORT).show();
                }
            });
        }else if(paymentType.equals(Constants.SALE_PAYMENTS)){
            paymentViewHolder.statPayeeNumber.setText("+"+Utils.insertCharacterForEveryNDistance(3, currentPayment.getPayeemobilenumber(),' '));
            paymentViewHolder.statPayDate.setText(Utils.readableDate(currentPayment.getCreatedon()));
            paymentViewHolder.statAmount.setText(Utils.moneyFormatter(currentPayment.getAmount()));
            paymentViewHolder.resendTokenBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    resendToken(currentPayment.getId());
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

        //sale payment
        MaterialButton resendTokenBtn;
        TextView statPayeeNumber;
        TextView statAmount;
        TextView statPayDate;

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
            }else if(paymentType.equals(Constants.SALE_PAYMENTS)){
                statPayeeNumber = itemView.findViewById(R.id.sale_stat_item_number);
                statAmount = itemView.findViewById(R.id.sale_stat_item_amount);
                statPayDate = itemView.findViewById(R.id.sale_stat_item_date);
                resendTokenBtn = itemView.findViewById(R.id.sale_stat_resend_item_token_btn);
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

    public void resendToken(String leasePaymentId){
        //SaleViewModel saleViewModel = ViewModelProviders.of((FragmentActivity) context).get(SaleViewModel.class);
        LiveData<DeviceToken> deviceTokenLiveData = saleViewModel.resendToken(leasePaymentId);
        deviceTokenLiveData.observe((LifecycleOwner) context, new Observer<DeviceToken>() {
            @Override
            public void onChanged(@Nullable DeviceToken deviceToken) {

            }
        });
        observeResponse();
    }

    public void observeResponse(){
        LiveData<String> responseMessage = saleViewModel.getResponseMessage();
        responseMessage.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(context,"PAYMENT TOKEN SUCCESSFULLY SENT", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){

           /* LiveData<NetworkResponse> networkResponse = saleViewModel.getNetworkResponse();
            networkResponse.observe((LifecycleOwner) context, new Observer<NetworkResponse>() {
                @Override
                public void onChanged(@Nullable NetworkResponse response) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Error");
                    builder.setMessage(response.getBody());
                    builder.setCancelable(false);
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });

                    AlertDialog alert = builder.create();
                    alert.show();
                }
            });*/

            dialog.dismiss();
            Toast.makeText(context,"SOMETHING WENT WRONG, PAYMENT TOKEN NOT SENT", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(context,"CONNECTION FAILURE, PAYMENT TOKEN NOT SENT", Toast.LENGTH_SHORT).show();
        }
    }

}
