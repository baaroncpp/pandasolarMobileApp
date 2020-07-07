package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panda.solar.activities.R;

import java.util.List;

public class ProductEquipementAdapter extends RecyclerView.Adapter<ProductEquipementAdapter.EquipViewHolder> {

    private List<String> equipList;

    public ProductEquipementAdapter(List<String> equipList){
        this.equipList = equipList;
    }

    @NonNull
    @Override
    public EquipViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_equipement_item, viewGroup,false);
        EquipViewHolder paymentViewHolder = new EquipViewHolder(view);
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EquipViewHolder equipViewHolder, int i) {
        String currentEquip = equipList.get(i);
        equipViewHolder.equipName.setText(currentEquip);
    }

    @Override
    public int getItemCount() {
        return equipList.size();
    }

    public static class EquipViewHolder extends RecyclerView.ViewHolder{

        TextView equipName;

        public EquipViewHolder(@NonNull View itemView){
            super(itemView);
            equipName = itemView.findViewById(R.id.product_equip_name);
        }
    }

}
