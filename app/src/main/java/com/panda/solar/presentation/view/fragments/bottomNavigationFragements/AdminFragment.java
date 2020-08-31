package com.panda.solar.presentation.view.fragments.bottomNavigationFragements;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.LoginActivity;
import com.panda.solar.presentation.view.activities.PaymentsReportActivity;
import com.panda.solar.presentation.view.activities.ProfileActivity;
import com.panda.solar.presentation.view.activities.SettingsActivity;
import com.panda.solar.utils.Utils;

import java.io.File;

public class AdminFragment extends Fragment {

    private CardView paymentReportCard;
    private CardView saleReportCard;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.admin_fragement, container, false);

/*
        context = getActivity();
        Toolbar toolbar = view.findViewById(R.id.home_toolbar);
        toolbar.inflateMenu(R.menu.appmenu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.appmenu_logout) {
                    Utils.logoutUtil(getActivity());
                    Utils.deleteCache(getActivity());

                    Intent intent = new Intent(getActivity(), LoginActivity.class);

                    // FLAG_ACTIVITY_CLEAR_TOP:- clears all activities stacked on top of the current activity
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    getActivity().finish();

                    trimCache(getActivity());
                }else if(item.getItemId() == R.id.appmenu_settings){
                    startActivity(new Intent(getActivity(), SettingsActivity.class));
                }else if(item.getItemId() == R.id.appmenu_profile){
                    startActivity(new Intent(getActivity(), ProfileActivity.class));
                }
                return false;
            }
        });
*/

        init(view);

        paymentReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PaymentsReportActivity.class);
                startActivity(intent);
            }
        });

        saleReportCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Coming Soon", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    public void init(View view){
        paymentReportCard = view.findViewById(R.id.payments_report_card);
        saleReportCard = view.findViewById(R.id.sale_report_card);

    }

    public static void trimCache(Context context) {
        try {
            File dir = context.getCacheDir();
            if (dir != null && dir.isDirectory()) {
                deleteDir(dir);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        // The directory is now empty so delete it
        return dir.delete();
    }
}
