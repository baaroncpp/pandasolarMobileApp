package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.panda.solar.Model.entities.Lease;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.SaleStatisticsModel;
import com.panda.solar.Model.entities.TotalLeasePayments;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.PaymentsAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.ReportViewModel;

import java.util.ArrayList;
import java.util.List;

public class SaleStatistics extends AppCompatActivity {

    private PieChart pieChart;
    private RecyclerView recyclerView;
    private TextView customerName;
    private TextView soldDate;
    private TextView expiryDate;
    private TextView deviceSerial;
    private ReportViewModel reportViewModel;
    private PaymentsAdapter paymentsAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sale_statistics);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        String leaseid = getIntent().getStringExtra(Constants.LEASE_ID);
        init();

        //buildPieChart();

        LiveData<SaleStatisticsModel> saleStatisticsModelLiveData = reportViewModel.getSaleStatistic(leaseid);
        saleStatisticsModelLiveData.observe(this, new Observer<SaleStatisticsModel>() {
            @Override
            public void onChanged(@Nullable SaleStatisticsModel saleStatisticsModel) {
                customerName.setText(saleStatisticsModel.getCustomer().getUser().getFirstname()+" "+saleStatisticsModel.getCustomer().getUser().getLastname());
                deviceSerial.setText(saleStatisticsModel.getSale().getScannedserial());
                soldDate.setText(Utils.readableDate(saleStatisticsModel.getSale().getCreatedon()));
                expiryDate.setText(Utils.readableDate(saleStatisticsModel.getLease().getExpectedfinishdate()));

                buildPieChart(saleStatisticsModel.getTotalLeasePayments(), saleStatisticsModel.getLease());
                buildRecycler(saleStatisticsModel.getPayments());

                //buildPieChart();
            }
        });
    }

    public void init(){
        pieChart = findViewById(R.id.sale_stat_chart);
        recyclerView = findViewById(R.id.sale_stat_recycler);
        customerName = findViewById(R.id.sale_stat_customer);
        soldDate = findViewById(R.id.sale_stat_sold_on);
        expiryDate = findViewById(R.id.sale_stat_expected_completion);
        deviceSerial = findViewById(R.id.sale_stat_device_serial);
        reportViewModel = ViewModelProviders.of(this).get(ReportViewModel.class);
    }

    public void buildRecycler(List<LeasePayment> payments){
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        paymentsAdapter = new PaymentsAdapter(payments, Constants.SALE_PAYMENTS, this);
        recyclerView.setAdapter(paymentsAdapter);
    }

    public void buildPieChart(TotalLeasePayments tlp, Lease lease){

        int[] colors = new int[]{getResources().getColor(R.color.orange), getResources().getColor(R.color.light_yellow)};

        PieDataSet pieDataSet = new PieDataSet(dataValues(tlp, lease), "");
        pieDataSet.setColors(colors);

        PieData pieData = new PieData(pieDataSet);
        pieChart.setData(pieData);
        pieChart.setDrawEntryLabels(false);
        pieChart.setMaxAngle(300);
        pieChart.getDescription().setEnabled(false);
        pieChart.invalidate();
        //pieChart.setUsePercentValues(true);

        /*pieChart.setTransparentCircleRadius(40);
        pieChart.setTransparentCircleAlpha(20);
        pieChart.setTransparentCircleColor(Color.RED);*/

        Legend legend = pieChart.getLegend();
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.CENTER);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        legend.setDrawInside(false);
    }

    private ArrayList<PieEntry> dataValues(TotalLeasePayments tlp, Lease lease){

        float owedPercentage = 0;
        float paidPercentage = 0;

        //float paidAmount = lease.getTotalleasevalue() - (tlp.getTotalamountowed() + tlp.getResidueamount());
        float paidAmount = (lease.getTotalleasevalue() - tlp.getTotalamountowed())  + tlp.getResidueamount();

        float owedAmount = tlp.getTotalamountowed();

        float unitCostAmount = lease.getTotalleasevalue();

        if(paidAmount >= unitCostAmount){
            owedPercentage = 0;
            paidPercentage = 100;
        }else if(paidAmount < unitCostAmount){
            paidPercentage = (paidAmount*100)/unitCostAmount;
            owedPercentage = 100 - paidPercentage;
        }else if(paidAmount == 0){
            owedPercentage = 100;
            paidPercentage = 0;
        }

        ArrayList<PieEntry> dataVals = new ArrayList<>();

        dataVals.add(new PieEntry(paidAmount, "Paid"));
        dataVals.add(new PieEntry(owedAmount, "Owed"));
        return dataVals;
    }
}
