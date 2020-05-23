package com.panda.solar.presentation.view.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.panda.solar.Model.entities.SaleProduct;
import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.fragments.bottomNavigationFragements.AdminFragment;
import com.panda.solar.presentation.view.fragments.bottomNavigationFragements.HomeFragment;
import com.panda.solar.presentation.view.fragments.bottomNavigationFragements.ProfileFragment;
import com.panda.solar.presentation.view.fragments.bottomNavigationFragements.SettingsFragment;
import com.panda.solar.utils.AppContext;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.UserViewModel;

import org.parceler.Parcels;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, CompoundButton.OnCheckedChangeListener {

    private CardView saleCard;
    private CardView stockManagementCard;
    private CardView installationCard;
    private CardView repairCard;
    private CardView customerCard;

    private AppCompatCheckBox directSaleCheckBox;
    private AppCompatCheckBox assetFinancingCheckbox;
    private Intent saleTypeIntent;

    private FloatingActionButton fab;
    private UserViewModel userViewModel;
    private LiveData<String> responseMessage;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /*dialog = Utils.customerProgressBar(this);
        dialog.show();

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        LiveData<User> user = userViewModel.getUser();
        user.observe(this, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                saveUserDetails(user);
            }
        });*/

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saleTypeProductDialog();
            }
        });

        //observeResponse();

        BottomNavigationView bottomNav = (BottomNavigationView)findViewById(R.id.bottom_navigation_bar);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment()).commit();

    }

    /*public void observeResponse(){
        responseMessage = userViewModel.getResponseMessage();
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
            finish();
            dialog.dismiss();
            Toast.makeText(this,"SORRY TRY AGAIN", Toast.LENGTH_LONG).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            finish();
            dialog.dismiss();
            Toast.makeText(this,"CONNECTION ERROR, TRY AGAIN", Toast.LENGTH_LONG).show();
        }
    }*/

    private void saleTypeProductDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View v = LayoutInflater.from(this).inflate(R.layout.sale_type_checkbox_layout, null);
        directSaleCheckBox = (AppCompatCheckBox)v.findViewById(R.id.direct_sale_checkbox);
        assetFinancingCheckbox = (AppCompatCheckBox)v.findViewById(R.id.asset_financing_checkbox);

        directSaleCheckBox.setOnCheckedChangeListener(this);
        assetFinancingCheckbox.setOnCheckedChangeListener(this);

        builder.setView(v);

        builder.setPositiveButton(R.string.next, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(assetFinancingCheckbox.isChecked()){
                    saleTypeIntent = new Intent(HomeActivity.this, LeaseSaleActivity.class);
                }else if(directSaleCheckBox.isChecked()){
                    saleTypeIntent = new Intent(HomeActivity.this, DirectSale.class);
                }

                if(assetFinancingCheckbox.isChecked() || directSaleCheckBox.isChecked()){
                    startActivity(saleTypeIntent);
                }
                else{
                    Toast.makeText(HomeActivity.this, "Please select sale type.", Toast.LENGTH_SHORT).show();
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
    public void onPointerCaptureChanged(boolean hasCapture) {

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

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener(){
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

            Fragment selectedFragment = null;

            switch(menuItem.getItemId()){

                case R.id.nav_home:
                    selectedFragment = new HomeFragment();
                    break;

                case R.id.nav_admin:
                    selectedFragment = new AdminFragment();
                    break;

                case R.id.nav_settings:
                    selectedFragment = new SettingsFragment();
                    break;

                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment).commit();

            return true;
        }

    };

    private void initViews(){
        saleCard = (CardView)findViewById(R.id.sale);
        stockManagementCard = (CardView)findViewById(R.id.stock_management_card);
        installationCard = (CardView)findViewById(R.id.installation_card);
        repairCard = (CardView)findViewById(R.id.repair_card);
        customerCard = (CardView)findViewById(R.id.customer_card);

        stockManagementCard.setOnClickListener(this);
        saleCard.setOnClickListener(this);
        installationCard.setOnClickListener(this);
        repairCard.setOnClickListener(this);
        customerCard.setOnClickListener(this);
    }

    @Override
    public void onBackPressed() {
        RelativeLayout drawer = (RelativeLayout) findViewById(R.id.drawer_layout);
       // if (drawer.isisDrawerOpen(GravityCompat.START)) {
          //  drawer.closeDrawer(GravityCompat.START);
      //  } else {
       //     super.onBackPressed();
       // }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

       // DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.sale:
                startActivity(new Intent(this, SaleActivity.class));
                break;

            case R.id.stock_management_card:
                startActivity(new Intent(this, StockManagementActivity.class));
                break;

            case R.id.installation_card:
                startActivity(new Intent(this, InstallationActivity.class));
                break;

            case R.id.repair_card:
                startActivity(new Intent(this, RepairActivity.class));
                break;

            case R.id.customer_card:
                startActivity(new Intent(this, CustomerActivity.class));
                break;

        }
    }

    /*public void saveUserDetails(User user){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.USER_ID, user.getId());
        editor.putString(Constants.USER_TYPE, user.getUsertype());
        editor.apply();
    }
*/
}
