package com.panda.solar.presentation.adapters;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.Model.entities.UploadLinks;
import com.panda.solar.activities.R;

import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.UploadLinkViewModel;

import java.util.List;

public class LeaseOfferAdapter extends RecyclerView.Adapter<LeaseOfferAdapter.LeaseOfferViewHolder> {

    private List<LeaseOffer> leaseOfferList;
    private LeaseOfferOnClickListener leaseOfferOnClickListener;
    private Context context;

    public LeaseOfferAdapter(List<LeaseOffer> leaseOffers){
        this.leaseOfferList = leaseOffers;
    }

    public interface LeaseOfferOnClickListener{
        void onLeaseOfferClick(int position);
    }

    public void setLeaseOfferOnClickListener(LeaseOfferOnClickListener leaseOfferOnClickListener){
        this.leaseOfferOnClickListener = leaseOfferOnClickListener;
    }

    @NonNull
    @Override
    public LeaseOfferViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.activity_lease_offer_item, viewGroup,false);
        LeaseOfferViewHolder leaseOfferViewHolder = new LeaseOfferViewHolder(view, leaseOfferOnClickListener);
        return leaseOfferViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LeaseOfferViewHolder leaseOfferViewHolder, int i) {
        LeaseOffer currentLeaseOffer = leaseOfferList.get(i);

        leaseOfferViewHolder.leaseOfferItemName.setText(currentLeaseOffer.getName());
        leaseOfferViewHolder.leaseItemProduct.setText(currentLeaseOffer.getProduct().getName());
        leaseOfferViewHolder.leaseItemCost.setText(Utils.moneyFormatter(currentLeaseOffer.getProduct().getUnitcostselling()));
        leaseOfferViewHolder.leaseOfferItemCode.setText(String.valueOf(currentLeaseOffer.getCode()));
        leaseOfferViewHolder.leaseItemDeposit.setText(Utils.moneyFormatter(currentLeaseOffer.getIntialdeposit()));
        leaseOfferViewHolder.leaseItemPayments.setText(Utils.moneyFormatter(currentLeaseOffer.getRecurrentpaymentamount()));
        leaseOfferViewHolder.leaseDescription.setText(currentLeaseOffer.getDescription());
    }

    @Override
    public int getItemCount() {
        return leaseOfferList.size();
    }

    public static class LeaseOfferViewHolder extends RecyclerView.ViewHolder{

        TextView leaseOfferItemName;
        TextView leaseOfferItemCode;
        TextView leaseItemProduct;
        TextView leaseItemCost;
        TextView leaseItemDeposit;
        TextView leaseItemPayments;
        TextView leaseDescription;

        public LeaseOfferViewHolder(@NonNull View itemView, LeaseOfferOnClickListener leaseOfferOnClickListener) {
            super(itemView);

            leaseOfferItemName = itemView.findViewById(R.id.lease_item_name);
            leaseOfferItemCode = itemView.findViewById(R.id.lease_item_code);
            leaseItemProduct = itemView.findViewById(R.id.lease_product_name);
            leaseItemCost = itemView.findViewById(R.id.lease_item_unitcost);
            leaseItemDeposit = itemView.findViewById(R.id.lease_initial_deposit);
            leaseItemPayments = itemView.findViewById(R.id.lease_recur_payment);
            leaseDescription = itemView.findViewById(R.id.lease_description);
        }
    }

    public UploadLinks getUploadLinks(String linkType, String id){

        final UploadLinks[] dataResult = {new UploadLinks()};
        UploadLinkViewModel uploadLinkViewModel = ViewModelProviders.of((FragmentActivity) context).get(UploadLinkViewModel.class);

        LiveData<UploadLinks> uploadLinksLiveData = uploadLinkViewModel.getUploadLinks(linkType, id);

        uploadLinksLiveData.observe((LifecycleOwner) context, new Observer<UploadLinks>() {
            @Override
            public void onChanged(@Nullable UploadLinks uploadLinks) {
                dataResult[0] = uploadLinks;
            }
        });

        return dataResult[0];
    }

    public void observeResponse(){
        UploadLinkViewModel uploadLinkViewModel = ViewModelProviders.of((FragmentActivity) context).get(UploadLinkViewModel.class);
        LiveData<String> responseMessage = uploadLinkViewModel.getResponseMessage();

        responseMessage.observe((LifecycleOwner) context, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){

        }else if(msg.equals(Constants.ERROR_RESPONSE)){

        }else if(msg.equals(Constants.FAILURE_RESPONSE)){

        }
    }
}
