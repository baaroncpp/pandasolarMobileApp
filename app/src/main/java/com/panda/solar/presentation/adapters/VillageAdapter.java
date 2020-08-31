package com.panda.solar.presentation.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.panda.solar.Model.entities.Village;
import com.panda.solar.activities.R;

import java.util.ArrayList;
import java.util.List;

public class VillageAdapter extends RecyclerView.Adapter<VillageAdapter.VillageViewHolder> {

    List<Village> villages;
    OnVillageClickListener villageClickListener;

    public VillageAdapter(List<Village> villages) {
        this.villages = villages;
    }

    public interface OnVillageClickListener{
        void onVillageClick(int position);
    }

    public void setVillageClickListener(OnVillageClickListener villageClickListener){
        this.villageClickListener = villageClickListener;
    }

    @NonNull
    @Override
    public VillageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.village_list_item, viewGroup,false);
        VillageViewHolder villageViewHolder = new VillageViewHolder(view, villageClickListener);
        return villageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VillageViewHolder villageViewHolder, int i) {
        Village currentVillage = villages.get(i);
        villageViewHolder.villageName.setText(currentVillage.getName());
    }

    @Override
    public int getItemCount() {
        return villages.size();
    }

    public static class VillageViewHolder extends RecyclerView.ViewHolder{

        TextView villageName;

        public VillageViewHolder(@NonNull View itemView, final OnVillageClickListener villageClickListener) {
            super(itemView);
            villageName = itemView.findViewById(R.id.village_name);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(villageClickListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            villageClickListener.onVillageClick(position);
                        }
                    }
                }
            });
        }
    }

    public void filterList(ArrayList<Village> filteredList){
        villages = filteredList;
        notifyDataSetChanged();
    }
}
