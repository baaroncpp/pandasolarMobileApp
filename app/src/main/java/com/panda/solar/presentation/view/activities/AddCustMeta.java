package com.panda.solar.presentation.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddCustMeta extends AppCompatActivity {

    private Map<String, Uri> uploadUris;
    private CircleImageView profileImageView;
    private MaterialButton addProfileBtn;
    private MaterialButton uploadFilesBtn;
    private FloatingActionButton addCoiBtn;
    private FloatingActionButton addIdBtn;
    private TextView coiFileName;
    private TextView idFileName;
    private ProgressBar coiProgressBar;
    private ProgressBar idProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cust_meta);

        init();

        addProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(Constants.PICK_PROFILE_IMAGE_REQUEST);
            }
        });

        addIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(Constants.PICK_ID_IMAGE_REQUEST);
            }
        });

        addCoiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser(Constants.PICK_COI_IMAGE_REQUEST);
            }
        });

    }

    public void init(){
        profileImageView = findViewById(R.id.customer_profile_view);
        addProfileBtn = findViewById(R.id.add_cust_profile_btn);
        uploadFilesBtn = findViewById(R.id.upload_cust_files_btn);
        addCoiBtn = findViewById(R.id.add_coi_btn);
        addIdBtn = findViewById(R.id.add_id_btn);
        coiFileName = findViewById(R.id.coi_file_name);
        idFileName = findViewById(R.id.id_file_name);
        coiProgressBar = findViewById(R.id.coi_progressBar);
        idProgressBar = findViewById(R.id.progressBar_id);

        uploadUris = new HashMap<>();
    }

    private void openFileChooser(int val){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, val);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && data != null && data.getData() != null){

            if(requestCode == Constants.PICK_PROFILE_IMAGE_REQUEST){
                uploadUris.put(Constants.CUSTOMER_PROFILE_PATH, data.getData());
                profileImageView.setImageURI(data.getData());
            }

            if(requestCode == Constants.PICK_COI_IMAGE_REQUEST){
                uploadUris.put(Constants.CUSTOMER_COI_PATH, data.getData());
                coiFileName.setText(data.getData().getLastPathSegment());
            }

            if(requestCode == Constants.PICK_ID_IMAGE_REQUEST){
                uploadUris.put(Constants.CUSTOMER_ID_PATH, data.getData());
                idFileName.setText(data.getData().getLastPathSegment());
            }

        }
    }
}
