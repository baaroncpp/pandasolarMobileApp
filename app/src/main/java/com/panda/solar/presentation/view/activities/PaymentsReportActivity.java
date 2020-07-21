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
import com.panda.solar.Model.entities.KeyValueModel;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.PaymentStatisticModel;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.PaymentsAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.PaymentViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
    private TextView dailyPaymentView;
    private List<KeyValueModel> dailyPayments;
    private List<KeyValueModel> monthlyPayments;
    private Long dailyPayment;
    private String[] months = new String[12];
    private String[] days = new String[7];

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

        morePaymentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PaymentsReportActivity.this, PaymentsList.class);
                startActivity(intent);
            }
        });

        //get paymentStatistics
        LiveData<PaymentStatisticModel> paymentStatisticModelLiveData = paymentViewModel.getPaymentStatistics();
        paymentStatisticModelLiveData.observe(this, new Observer<PaymentStatisticModel>() {
            @Override
            public void onChanged(@Nullable PaymentStatisticModel paymentStatisticModel) {
                dailyPayment = paymentStatisticModel.getDailyPayments();
                dailyPayments.addAll(paymentStatisticModel.getWeeklyPayments());
                monthlyPayments.addAll(paymentStatisticModel.getMonthlyPayments());
                dailyPaymentView.setText(paymentStatisticModel.getDailyPayments().toString());
                Collections.reverse(dailyPayments);
                Collections.reverse(monthlyPayments);
                paymentChart();
            }
        });

        paymentChart();

        paymentRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                paymentChart();
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
        dailyPaymentView = findViewById(R.id.daily_paymments_view);
        dailyPayments = new ArrayList<>();
        monthlyPayments = new ArrayList<>();
        dailyPayment = (long)0;
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

        //final String[] months = new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul","Aug","Sept","Oct","Nov","Dec"};
        //final String[] days = new String[]{"Mon", "Tue", "Wed", "Thur", "Fri", "Sat", "Sun"};



        BarDataSet barDataSet = new BarDataSet(getData(), "Payments"+ getGraphDate());
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
        paymentBarChart.invalidate();
    }

    private ArrayList getData(){

        int count = 0;
        ArrayList<BarEntry> entries = new ArrayList<>();

        if(weekBtn.isChecked()){

            for(KeyValueModel obj : dailyPayments){
                entries.add(new BarEntry(count, obj.getValue()));
                days[count] = obj.getName();
                count = count + 1;
            }
        }else if(monthBtn.isChecked()){

            for(KeyValueModel obj : monthlyPayments){
                entries.add(new BarEntry(count, obj.getValue()));
                months[count] = obj.getName();
                count = count + 1;
            }
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

    public String getGraphDate(){

        if(weekBtn.isChecked()){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_MONTH, -6);
            return " from "+ Utils.readableDate(cal.getTime())+" to Date";
        }else{
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -11);
            return " from "+ Utils.readableDate(cal.getTime())+" to Date";
        }

    }
}
