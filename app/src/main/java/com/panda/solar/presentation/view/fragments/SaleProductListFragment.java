package com.panda.solar.presentation.view.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.panda.solar.presentation.view.activities.DirectAssetFinancingActivity;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.SaleActivity;
import com.panda.solar.presentation.adapters.SaleProductAdapter;
import com.panda.solar.Model.entities.SaleProduct;
import com.panda.solar.utils.Constants;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;


public class SaleProductListFragment extends Fragment implements SaleProductAdapter.SaleProductItemClickListener, CompoundButton.OnCheckedChangeListener {

    private RecyclerView recyclerView;
    private AppCompatCheckBox directSaleCheckBox;
    private AppCompatCheckBox assetFinancingCheckbox;

    public SaleProductListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sale_product_list, container, false);

        recyclerView = (RecyclerView)v.findViewById(R.id.product_list_recycler);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));

        mockSaleProductList();
        return v;
    }

    private void mockSaleProductList(){
        List<SaleProduct> saleProductList = new ArrayList<>();

        SaleProduct saleProductOne = new SaleProduct();
        saleProductOne.setName("Solar Panel");
        saleProductOne.setImageUrl("");

        saleProductList.add(saleProductOne);

        SaleProduct saleProductTwo = new SaleProduct();
        saleProductTwo.setName("Solar Battery");
        saleProductTwo.setImageUrl("");

        saleProductList.add(saleProductTwo);

        SaleProduct saleProductThree = new SaleProduct();
        saleProductThree.setName("Solar Frame");
        saleProductThree.setImageUrl("");

        saleProductList.add(saleProductThree);

        recyclerView.setAdapter(new SaleProductAdapter(saleProductList, getActivity(), this));
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
            ((SaleActivity) getActivity()).getSupportActionBar().setTitle(R.string.products);
    }

    @Override
    public void onItemClick(SaleProduct saleProduct) {
        saleTypeProductDialog(saleProduct);
    }

    private void saleTypeProductDialog(final SaleProduct saleProduct){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.sale_type_checkbox_layout, null);
        directSaleCheckBox = (AppCompatCheckBox)v.findViewById(R.id.direct_sale_checkbox);
        assetFinancingCheckbox = (AppCompatCheckBox)v.findViewById(R.id.asset_financing_checkbox);

        directSaleCheckBox.setOnCheckedChangeListener(this);
        assetFinancingCheckbox.setOnCheckedChangeListener(this);
        
        builder.setView(v);

        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intent = new Intent(getActivity(), DirectAssetFinancingActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(Constants.SALE_PRODCUT, Parcels.wrap(saleProduct));
                if(assetFinancingCheckbox.isChecked()){

                    bundle.putString(Constants.SALE_TYPE, Constants.ASSET_FINANCING);
                    intent.putExtra(Constants.SALE_TYPE, Constants.ASSET_FINANCING);
                }else if(directSaleCheckBox.isChecked()){
                    bundle.putString(Constants.SALE_TYPE, Constants.DIRECT_SALE);
                    intent.putExtra(Constants.SALE_TYPE, Constants.DIRECT_SALE);
                }

                if(assetFinancingCheckbox.isChecked() || directSaleCheckBox.isChecked()){
                    startActivity(intent);
                    /*SaleCustomerDetailFragment saleCustomerDetailFragment = SaleCustomerDetailFragment.getInstance();
                    saleCustomerDetailFragment.setArguments(bundle);

                    if(getActivity() != null){
                        getActivity().getSupportFragmentManager()
                                .beginTransaction().replace(R.id.sale_container, saleCustomerDetailFragment, SaleCustomerDetailFragment.class.getSimpleName())
                                .addToBackStack(null)
                                .commit();
                    }*/
                }
                else{
                    Toast.makeText(getActivity(), "Please select sale type.", Toast.LENGTH_SHORT).show();
                }




            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setTitle(R.string.choose_sale_type);
        builder.setCancelable(false);
        builder.show();
    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView.getId() == R.id.asset_financing_checkbox){
            if(isChecked){
                if(directSaleCheckBox.isChecked()){
                    directSaleCheckBox.setChecked(false);
                }
            }else{
                if(!directSaleCheckBox.isChecked()){
                    directSaleCheckBox.setChecked(true);
                }
            }
        }
        else{
            if(isChecked){
                if(assetFinancingCheckbox.isChecked()){
                    assetFinancingCheckbox.setChecked(false);
                }
            }else{
                if(!assetFinancingCheckbox.isChecked()){
                    assetFinancingCheckbox.setChecked(true);
                }
            }
        }
    }
}
