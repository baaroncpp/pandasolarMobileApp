package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.Sale;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.SaleViewModel;

import de.hdodenhof.circleimageview.CircleImageView;

public class SaleDetail extends AppCompatActivity {

    private CircleImageView customerProfile;
    private TextView saleType;
    private TextView saleStatus;
    private TextView customerName;
    private TextView customerLocation;
    private TextView productName;
    private TextView productPrice;
    private TextView productQuantity;
    private TextView agentCommision;
    private TextView saleDate;
    private TextView saleDescription;
    private MaterialButton approveBtn;
    private SaleModel saleModel;
    private SaleViewModel saleViewModel;
    private Sale saleResult;
    private ProgressDialog progressDialog;
    private LiveData<String> responseMessageLive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_detail);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        init();

        saleModel = getIntent().getExtras().getParcelable(Constants.SALE_OBJ);
        String createdon = getIntent().getStringExtra("saleDate");

        //if(Utils.getSharedPreference(Constants.USER_ID).equalsIgnoreCase("ADMIN")){
            if(saleModel.getSalestatus() == 2){
                approveBtn.setVisibility(View.GONE);
            }else{
                approveBtn.setVisibility(View.VISIBLE);
            }
        //}else{approveBtn.setVisibility(View.GONE);}

        customerName.setText(saleModel.getCustomer().getUser().getFirstname()+" "+saleModel.getCustomer().getUser().getLastname());
        productName.setText(saleModel.getProduct().getName());
        productPrice.setText(Utils.moneyFormatter(saleModel.getAmount()));
        productQuantity.setText(String.valueOf(saleModel.getQuantity())+" PCS");
        agentCommision.setText(Utils.moneyFormatter(saleModel.getAgentcommission()));
        saleDate.setText(createdon);
        saleDescription.setText(saleModel.getDescription());
        saleType.setText(saleModel.getSaletype()+" Sale");
        saleStatus.setText(Utils.saleStatus(saleModel.getSalestatus()));
        customerLocation.setText(saleModel.getCustomer().getAddress());

        approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                Sale sale = approveSale(saleModel.getId());
                if(sale != null){
                    saleStatus.setText(Utils.saleStatus(sale.getSalestatus()));
                    //Toast.makeText(SaleDetail.this,"APPROVED SUCCESSFULLY", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public Sale approveSale(String saleId){
        saleResult = new Sale();
        LiveData<Sale> sale = saleViewModel.approveSale(saleId, "approved", "Mobile approved");
        sale.observe(this, new Observer<Sale>() {
            @Override
            public void onChanged(@Nullable Sale sale) {
                saleResult = sale;
            }
        });
        observeResponse();
        return saleResult;
    }

    public void observeResponse(){
        responseMessageLive = saleViewModel.getResponseMessage();
        responseMessageLive.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                handelResponse(s);
            }
        });
    }

    public void handelResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            progressDialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            progressDialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG, APPROVAL FAILED!!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            progressDialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE, NOT APPROVED", Toast.LENGTH_SHORT).show();
        }
    }

    public void init(){
        customerProfile = findViewById(R.id.saledetail_customer_pic);
        customerName = findViewById(R.id.saledetail_customer_name);
        productName = findViewById(R.id.saledetail_product_name);
        productPrice = findViewById(R.id.saledetail_product_price);
        productQuantity = findViewById(R.id.saledetail_quantity);
        agentCommision = findViewById(R.id.sale_agent_commission);
        saleDate = findViewById(R.id.saledetail_date);
        saleDescription = findViewById(R.id.saledetail_description);
        approveBtn = findViewById(R.id.saledetail_approve_btn);
        saleType = findViewById(R.id.saledetail_type);
        saleStatus = findViewById(R.id.saledetail_status);
        customerLocation = findViewById(R.id.saledetail_customer_location);

        saleViewModel = ViewModelProviders.of(this).get(SaleViewModel.class);
        progressDialog = Utils.customerProgressBar(this);

    }
}
