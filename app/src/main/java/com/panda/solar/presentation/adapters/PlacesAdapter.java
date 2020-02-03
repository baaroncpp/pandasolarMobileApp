package com.panda.solar.presentation.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.panda.solar.activities.R;
import com.panda.solar.Model.entities.Place;

import java.util.List;

/**
 * Created by macosx on 27/01/2019.
 */

public class PlacesAdapter extends RecyclerView.Adapter<PlacesAdapter.PlaceAdapterRecyclerView>{

    private List<Place> placeList;
    private Context context;
    private OnPlaceItemClickListener onPlaceItemClickListener;

    public PlacesAdapter(List<Place> placeList, Context context, OnPlaceItemClickListener onPlaceItemClickListener){
        this.context = context;
        this.placeList = placeList;
        this.onPlaceItemClickListener = onPlaceItemClickListener;
    }

    @NonNull
    @Override
    public PlaceAdapterRecyclerView onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.place_list_item, viewGroup, false);
        return new PlaceAdapterRecyclerView(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceAdapterRecyclerView placeAdapterRecyclerView, int position) {
        final Place place = placeList.get(position);
        placeAdapterRecyclerView.placeName.setText(place.getName());
        placeAdapterRecyclerView.placeItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPlaceItemClickListener.onItemClicked(place);
            }
        });
    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }


    public class PlaceAdapterRecyclerView extends RecyclerView.ViewHolder{

        private TextView placeName;
        private LinearLayout placeItemContainer;

        public PlaceAdapterRecyclerView(@NonNull View itemView) {
            super(itemView);

            placeName = (TextView)itemView.findViewById(R.id.place_name);
            placeItemContainer = (LinearLayout)itemView.findViewById(R.id.place_item_container);
        }
    }

    public interface OnPlaceItemClickListener{
        public void onItemClicked(Place place);
    }
}

