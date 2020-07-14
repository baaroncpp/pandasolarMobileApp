package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.PaymentsAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.viewModel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentsReportActivity extends AppCompatActivity {

    private BarChart paymentBarChart;
    private MaterialButton morePaymentsBtn;
    private RecyclerView reportPaymentsRecycler;
    private PaymentsAdapter paymentsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView errorView;
    private PaymentViewModel paymentViewModel;
    private AppCompatRadioButton weekBtn;
    private AppCompatRadioButton monthBtn;
    private RadioGroup paymentRadioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_report);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
        weekBtn.setChecked(true);

        paymentChart();

        paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                paymentChart();
            }
        });

        morePaymentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentsReportActivity.this, PaymentsList.class);
                startActivity(intent);
            }
        });

        LiveData<List<LeasePayment>> listPaymentLiveData = paymentViewModel.getAllLeasePayment(0, 5, "DESC");

        listPaymentLiveData.observe(this, new Observer<List<LeasePayment>>() {
            @Override
            public void onChanged(@Nullable List<LeasePayment> leasePayments) {
                buildRecycler(leasePayments);
            }
        });
    }

    public void init(){
        paymentBarChart = findViewById(R.id.payments_bar_chart);
        morePaymentsBtn = findViewById(R.id.more_payments_btn);
        reportPaymentsRecycler = findViewById(R.id.recent_payments_recycler);
        errorView = findViewById(R.id.report_payment_error_view);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
        weekBtn = findViewById(R.id.payment_week_btn);
        monthBtn = findViewById(R.id.payment_month_btn);
        paymentRadioGroup = findViewById(R.id.payment_stat_radio_group);
    }

    public void paymentChart(){

        int[] colors = new int[]{getResources().getColor(R.color.orange), getResources().getColor(R.color.light_yellow1),
                      getResources().getColor(R.color.light_yellow), getResources().getColor(R.color.light_yellow2),
                      getResources().getColor(R.color.orange), getResources().getColor(R.color.light_yellow1),
                      getResources().getColor(R.color.light_yellow), getResources().getColor(R.color.light_yellow2),
                    getResources().getColor(R.color.orange), getResources().getColor(R.color.light_yellow1),
                    getResources().getColor(R.color.light_yellow), getResources().getColor(R.color.light_yellow2)};

        int[] colors1 = new int[]{getResources().getColor(R.color.orange), getResources().getColor(R.color.light_yellow1),
                getResources().getColor(R.color.light_yellow),getResources().getColor(R.color.light_yellow) ,getResources().getColor(R.color.light_yellow2),
                getResources().getColor(R.color.light_yellow1), getResources().getColor(R.color.orange)};

        final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sept","Oct","Nov","Dec"};
        final String[] days = new String[]{"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};

        BarDataSet barDataSet = new BarDataSet(getData(), "Payments");
        barDataSet.setColors(colors);
        IndexAxisValueFormatter formatter = null;

        if(weekBtn.isChecked()){
            barDataSet.setColors(colors1);
            formatter = new IndexAxisValueFormatter(days);
        }else if(monthBtn.isChecked()){
            barDataSet.setColors(colors);
            formatter = new IndexAxisValueFormatter(months);
        }

        barDataSet.setBarBorderWidth(0.0f);

        BarData barData = new BarData(barDataSet);

        XAxis xAxis = paymentBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(formatter);

        paymentBarChart.setData(barData);
        paymentBarChart.setDrawValueAboveBar(false);
        paymentBarChart.setFitBars(true);
        paymentBarChart.animateXY(1000, 1000);
        paymentBarChart.getDescription().setEnabled(false);
        paymentBarChart.invalidate();
        paymentBarChart.getAxisLeft().setDrawGridLines(false);
        paymentBarChart.getXAxis().setDrawGridLines(false);
    }

    private ArrayList getData(){

        ArrayList<BarEntry> entries = new ArrayList<>();

        if(weekBtn.isChecked()){
            entries.add(new BarEntry(0f, 30f));
            entries.add(new BarEntry(1f, 80f));
            entries.add(new BarEntry(2f, 60f));
            entries.add(new BarEntry(3f, 50f));
            entries.add(new BarEntry(4f, 70f));
            entries.add(new BarEntry(5f, 60f));
            entries.add(new BarEntry(6f, 40f));
        }else if(monthBtn.isChecked()){
            entries.add(new BarEntry(0f, 30f));
            entries.add(new BarEntry(1f, 80f));
            entries.add(new BarEntry(2f, 60f));
            entries.add(new BarEntry(3f, 50f));
            entries.add(new BarEntry(4f, 70f));
            entries.add(new BarEntry(5f, 60f));
            entries.add(new BarEntry(6f, 40f));
            entries.add(new BarEntry(7f, 90f));
            entries.add(new BarEntry(8f, 20f));
            entries.add(new BarEntry(9f, 30f));
            entries.add(new BarEntry(10f, 75f));
            entries.add(new BarEntry(11f, 30f));
        }

        return entries;
    }

    public void buildRecycler(List<LeasePayment> payments){

        if(!payments.isEmpty()){
            errorView.setVisibility(View.GONE);
            layoutManager = new LinearLayoutManager(this);
            reportPaymentsRecycler.setLayoutManager(layoutManager);
            paymentsAdapter = new PaymentsAdapter(payments, Constants.REPORT_PAYMENTS,this);
            reportPaymentsRecycler.setAdapter(paymentsAdapter);
        }else{
            reportPaymentsRecycler.setVisibility(View.GONE);
            errorView.setVisibility(View.VISIBLE);
            errorView.setText("No Payments");
        }

    }
}
