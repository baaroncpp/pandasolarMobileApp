package com.panda.solar.presentation.view.activities;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.adapters.PaymentsAdapter;
import com.panda.solar.utils.Constants;
import com.panda.solar.viewModel.PaymentViewModel;

import java.util.ArrayList;
import java.util.List;

public class PaymentsReportActivity extends AppCompatActivity {

    private LineChart paymentLineChart;
    private MaterialButton morePaymentsBtn;
    private RecyclerView reportPaymentsRecycler;
    private PaymentsAdapter paymentsAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView errorView;
    private PaymentViewModel paymentViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments_report);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();
        paymentChart();

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
        paymentLineChart = findViewById(R.id.payments_line_chart);
        morePaymentsBtn = findViewById(R.id.more_payments_btn);
        reportPaymentsRecycler = findViewById(R.id.recent_payments_recycler);
        errorView = findViewById(R.id.report_payment_error_view);
        paymentViewModel = ViewModelProviders.of(this).get(PaymentViewModel.class);
    }

    public void paymentChart(){

        paymentLineChart.setDragEnabled(true);
        paymentLineChart.setScaleEnabled(false);

        ArrayList<Entry> yValues = new ArrayList<>();

        yValues.add(new Entry(1, 20));
        yValues.add(new Entry(2, 10));
        yValues.add(new Entry(3, 50));
        yValues.add(new Entry(4, 40));

        LineDataSet set1 = new LineDataSet(yValues, "test");
        set1.setFillAlpha(110);
        set1.setColor(getResources().getColor(R.color.colorPrimary));
        set1.setLineWidth(3f);
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setCubicIntensity(0.1f);
        set1.setDrawFilled(true);
        set1.setFillDrawable(getResources().getDrawable(R.drawable.grad));

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        paymentLineChart.setData(data);
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
