package com.panda.solar.presentation.view.fragments.bottomNavigationFragements;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.PaymentsReportActivity;

public class AdminFragment extends Fragment {

    private CardView paymentReportCard;
    private CardView saleReportCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragement, container, false);

        init(view);

        paymentReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentsReportActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void init(View view){
        paymentReportCard = view.findViewById(R.id.payments_report_card);
        paymentReportCard = view.findViewById(R.id.sale_report_card);

    }
}
