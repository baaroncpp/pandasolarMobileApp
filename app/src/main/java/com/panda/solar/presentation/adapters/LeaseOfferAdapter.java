package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.panda.solar.Model.entities.LeaseOffer;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.SaleLeaseOffer;

import java.util.List;

public class LeaseOfferAdapter extends RecyclerView.Adapter<LeaseOfferAdapter.LeaseOfferViewHolder> {

    private List<LeaseOffer> leaseOfferList;
    private LeaseOfferOnClickListener leaseOfferOnClickListener;

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
        leaseOfferViewHolder.leaseItemCost.setText(String.valueOf(currentLeaseOffer.getProduct().getUnitcostselling()));
        leaseOfferViewHolder.leaseOfferItemCode.setText(String.valueOf(currentLeaseOffer.getCode()));
        leaseOfferViewHolder.leaseItemDeposit.setText(String.valueOf(currentLeaseOffer.getIntialdeposit()));
        leaseOfferViewHolder.leaseItemPayments.setText(String.valueOf(currentLeaseOffer.getRecurrentpaymentamount()));
        // leaseOfferViewHolder.leaseDescription.setText(currentLeaseOffer.getDescription());
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
}
