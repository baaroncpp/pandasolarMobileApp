package com.panda.solar.presentation.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.SaleActivity;
import com.panda.solar.presentation.adapters.PlacesAdapter;
import com.panda.solar.Model.entities.Place;
import com.panda.solar.utils.Constants;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class PlacesFragment extends Fragment implements PlacesAdapter.OnPlaceItemClickListener {

    private RecyclerView recyclerView;
    private String PLACE_NAME;
    private String titleText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(getArguments() != null){
            PLACE_NAME = getArguments().getString(Constants.PLACE_NAME);
        }
         View view =  inflater.inflate(R.layout.fragment_places, container, false);

         recyclerView = (RecyclerView)view.findViewById(R.id.places_recycler_view);

         LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
         recyclerView.setLayoutManager(layoutManager);
         recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

         if(Constants.DISTRICT_PLACE.equals(PLACE_NAME)){
             mockDistricts();
             titleText = "Districts";
         }else if(Constants.COUNTY_PLACE.equals(PLACE_NAME)){
             mockCounties();
             titleText = "Counties";
         }else if(Constants.SUB_COUNTY_PLACE.equals(PLACE_NAME)){
             showNoPlaceFoundAddPlaceDialog();
             titleText = "Sub-Counties";
         }

         return view;
    }

    private void mockDistricts(){
        List<Place> placeList = new ArrayList<>();

        Place place = new Place();
        place.setName("Lira");

        placeList.add(place);
        recyclerView.setAdapter(new PlacesAdapter(placeList, getActivity(), this));
    }

    private void mockCounties(){
        List<Place> placeList = new ArrayList<>();

        Place place = new Place();
        place.setName("Lira Municipal");

        placeList.add(place);
        recyclerView.setAdapter(new PlacesAdapter(placeList, getActivity(), this));
    }

    private void subCounties(){
        List<Place> placeList = new ArrayList<>();

        Place place = new Place();
        place.setName("Adyel Division");

        placeList.add(place);
        recyclerView.setAdapter(new PlacesAdapter(placeList, getActivity(), this));
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null && ((SaleActivity)getActivity()).getSupportActionBar() != null)
            ((SaleActivity) getActivity()).getSupportActionBar().setTitle(titleText);
    }

    @Override
    public void onItemClicked(Place place) {

        if(getActivity() != null){

            Bundle bundle = new Bundle();
            bundle.putParcelable(Constants.PLACE_BUNDLE, Parcels.wrap(place));


            bundle.putString(Constants.PLACE_NAME, PLACE_NAME);
            SaleCustomerDetailFragment.getInstance().setArguments(bundle);
            getActivity().getSupportFragmentManager().popBackStackImmediate();
        }

    }

    private void showNoPlaceFoundAddPlaceDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.place_not_found_register_message);
        builder.setPositiveButton(R.string.register, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PLACE_NAME, PLACE_NAME);
                    AddNewPlaceFragment addNewPlaceFragment = new AddNewPlaceFragment();
                    addNewPlaceFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sale_container, addNewPlaceFragment, AddNewPlaceFragment.class.getSimpleName()).addToBackStack(null).commit();
                }
            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(getActivity() != null){
                    getActivity().getSupportFragmentManager().popBackStackImmediate();
                }
            }
        });
        builder.setCancelable(false);
        builder.show();
    }
}
