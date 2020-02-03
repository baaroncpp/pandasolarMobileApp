package com.panda.solar.presentation.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.panda.solar.presentation.view.activities.DirectAssetFinancingActivity;
import com.panda.solar.activities.R;
import com.panda.solar.Model.entities.Place;
import com.panda.solar.Model.entities.SaleProduct;
import com.panda.solar.utils.Constants;

import org.parceler.Parcels;


public class SaleCustomerDetailFragment extends Fragment implements View.OnClickListener {

    private SaleProduct saleProduct;
    private LinearLayout districtSpinnerHolder;
    private LinearLayout countySpinnerHolder;
    private LinearLayout subCountySpinnerHolder;
    private Button customerDetailNextButton;

    private TextView districtPlaceName;
    private TextView countyPalceName;

    private static SaleCustomerDetailFragment saleCustomerDetailFragment;
    private Place districtPlace;
    private Place countyPlace;
    private Place subCountyPlace;
    private Place villagePlace;
    private String SALE_TYPE;


    public SaleCustomerDetailFragment() {
        // Required empty public constructor
    }

    public static SaleCustomerDetailFragment getInstance(){
        if(saleCustomerDetailFragment == null){
            saleCustomerDetailFragment = new SaleCustomerDetailFragment();
        }
        return saleCustomerDetailFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null){
            Bundle bundle = getArguments();
            saleProduct = Parcels.unwrap(bundle.getParcelable(Constants.SALE_PRODCUT));
            SALE_TYPE = bundle.getString(Constants.SALE_TYPE);
        }

        districtPlace = null;
        countyPlace = null;
        subCountyPlace = null;
        villagePlace = null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(getArguments() != null){
            if(getArguments().getString(Constants.PLACE_NAME) != null){
                String placeName = getArguments().getString(Constants.PLACE_NAME);
                if(Constants.DISTRICT_PLACE.equals(placeName)){
                    districtPlace = Parcels.unwrap(getArguments().getParcelable(Constants.PLACE_BUNDLE));
                }else if(Constants.COUNTY_PLACE.equals(placeName)){
                    countyPlace = Parcels.unwrap(getArguments().getParcelable(Constants.PLACE_BUNDLE));
                }
            }
        }

        View view = inflater.inflate(R.layout.fragment_sale_customer_detail, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        districtSpinnerHolder = (LinearLayout) view.findViewById(R.id.district_spinner_holder);
        countySpinnerHolder = (LinearLayout) view.findViewById(R.id.county_spinner_holder);
        subCountySpinnerHolder = (LinearLayout)view.findViewById(R.id.sub_county_spinner_holder);
        districtPlaceName = (TextView)view.findViewById(R.id.district_place_name);
        countyPalceName = (TextView)view.findViewById(R.id.county_place_name);
        customerDetailNextButton = (Button)view.findViewById(R.id.customer_detail_next_button);

        if(districtPlace != null){
            districtPlaceName.setText(districtPlace.getName());
        }

        if(countyPlace != null){
            countyPalceName.setText(countyPlace.getName());
        }

        districtSpinnerHolder.setOnClickListener(this);
        countySpinnerHolder.setOnClickListener(this);
        subCountySpinnerHolder.setOnClickListener(this);
        customerDetailNextButton.setOnClickListener(this);
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
        if(getActivity() != null && ((DirectAssetFinancingActivity)getActivity()).getSupportActionBar() != null)
            ((DirectAssetFinancingActivity) getActivity()).getSupportActionBar().setTitle(R.string.customer_details);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.district_spinner_holder:
                if(getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PLACE_NAME, Constants.DISTRICT_PLACE);
                    PlacesFragment placesFragment = new PlacesFragment();
                    placesFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sale_container, placesFragment, PlacesFragment.class.getSimpleName())
                            .addToBackStack(null).commit();
                }
                break;

            case R.id.county_spinner_holder:

                if(getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PLACE_NAME, Constants.COUNTY_PLACE);
                    PlacesFragment placesFragment = new PlacesFragment();
                    placesFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sale_container, placesFragment, PlacesFragment.class.getSimpleName())
                            .addToBackStack(null).commit();
                }

                break;

            case R.id.sub_county_spinner_holder:


                if(getActivity() != null){
                    Bundle bundle = new Bundle();
                    bundle.putString(Constants.PLACE_NAME, Constants.SUB_COUNTY_PLACE);
                    PlacesFragment placesFragment = new PlacesFragment();
                    placesFragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.sale_container, placesFragment, PlacesFragment.class.getSimpleName())
                            .addToBackStack(SaleCustomerDetailFragment.class.getSimpleName()).commit();
                }

                break;

            case R.id.customer_detail_next_button:
                if(getActivity() != null){
                    if(Constants.DIRECT_SALE.equals(SALE_TYPE)){
                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.sale_container, new ScanEquipmentFragment(), ScanEquipmentFragment.class.getSimpleName())
                                .addToBackStack(null).commit();
                    }
                    else if(Constants.ASSET_FINANCING.equals(SALE_TYPE)){

                        getActivity().getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.sale_container, new AssetFinancingFragment(), AssetFinancingFragment.class.getSimpleName())
                                .addToBackStack(null).commit();
                    }

                }

                break;
        }
    }
}
