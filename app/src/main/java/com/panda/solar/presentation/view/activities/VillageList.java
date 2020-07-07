package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.Village;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.VillageAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.CustomerViewModel;

import java.util.ArrayList;
import java.util.List;

public class VillageList extends AppCompatActivity {

    private RecyclerView recyclerView;
    //private TextInputEditText searchEditView;
    private VillageAdapter villageAdapter;
    private CustomerViewModel customerViewModel;
    private RecyclerView.LayoutManager layoutManager;
    private ProgressDialog dialog;
    private TextView errorView;
    private List<Village> villageList;
    private ArrayList<Village> villagesFiltered;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_village_list);

        init();

        dialog.show();
        LiveData<List<Village>> livaDataVillages = customerViewModel.getVillages();
        livaDataVillages.observe(this, new Observer<List<Village>>() {
            @Override
            public void onChanged(@Nullable List<Village> villages) {
                villageList = new ArrayList<>();
                villageList.addAll(villages);
                buildRecyclerView(villages);
            }
        });
        observeResponse();

        /*searchEditView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.village_search, menu);

        SearchView searchView = (SearchView)menu.findItem(R.id.search_village_item).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return true;
    }

    private void filter(String text){
        ArrayList<Village> filteredList = new ArrayList<>();

        if(!villagesFiltered.isEmpty()){
            villagesFiltered.clear();
        }

        for(Village item : villageList){
            if(item.getName().toLowerCase().contains(text.toLowerCase())){
                villagesFiltered.add(item);
                filteredList.add(item);
            }
        }
        villageAdapter.filterList(filteredList);
    }

    public void observeResponse(){
        LiveData<String> responseMessage = customerViewModel.getResponseMessage();
        responseMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handleResponse(s);
            }
        });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            dialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            dialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Items Fetched");

            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();

            recyclerView.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("NO CONNECTION");

            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

    public void buildRecyclerView(final List<Village> villages){

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        villageAdapter = new VillageAdapter(villages);
        recyclerView.setAdapter(villageAdapter);

        villageAdapter.setVillageClickListener( new VillageAdapter.OnVillageClickListener() {
            @Override
            public void onVillageClick(int position) {
                Intent resultIntent = new Intent();

                if(!villagesFiltered.isEmpty()){
                    resultIntent.putExtra(Constants.VILLAGE_RESULT, villagesFiltered.get(position));
                }else{
                    resultIntent.putExtra(Constants.VILLAGE_RESULT, villages.get(position));
                }
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });
    }

    public void init(){
        recyclerView = findViewById(R.id.village_recycler_view);
        //searchEditView = findViewById(R.id.search_village);
        dialog = Utils.customerProgressBar(this);
        customerViewModel = ViewModelProviders.of(this).get(CustomerViewModel.class);
        errorView = findViewById(R.id.villageList_error_view);
        villagesFiltered = new ArrayList<>();
    }

}
