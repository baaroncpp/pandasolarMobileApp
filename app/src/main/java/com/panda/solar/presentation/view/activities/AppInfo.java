package com.panda.solar.presentation.view.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.panda.solar.activities.R;
import com.panda.solar.utils.InternetConnection;

public class AppInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if(!InternetConnection.checkConnection(this)){
            startActivity(new Intent(this, InternetError.class));
        }
    }
}
