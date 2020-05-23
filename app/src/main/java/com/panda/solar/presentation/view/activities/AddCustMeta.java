package com.panda.solar.presentation.view.activities;

import android.app.ProgressDialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.panda.solar.Model.constants.CustomerUploadType;
import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.activities.R;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.FileUtil;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.UploadLinkViewModel;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

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

    private UploadLinkViewModel uploadLinkViewModel;
    private LiveData<FileResponse> fileResponseLiveData;
    private LiveData<String> responseMessageLiveData;
    private LiveData<NetworkResponse> networkResponseLiveData;
    private String customerId;

    private ProgressDialog dialog;


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

        uploadFilesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadFile();
            }
        });

    }

    public void uploadFile(){

        String customerUploadType = "";

        if(!uploadUris.isEmpty()){

            for(Map.Entry<String, Uri> object : uploadUris.entrySet()){

                String upload = object.getKey();
                Uri uri = object.getValue();

                File file = null;

                try {
                    file  = FileUtil.from(this, uri);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if(upload.equals(Constants.CUSTOMER_PROFILE_PATH)){
                    customerUploadType = CustomerUploadType.PROFILE.name();
                }else if(upload.equals(Constants.CUSTOMER_COI_PATH)){
                    customerUploadType = CustomerUploadType.CONSENT_FORM.name();
                }else if(upload.equals(Constants.CUSTOMER_ID_PATH)){
                    customerUploadType = CustomerUploadType.ID_COPY.name();
                }

                RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(uri)), file);
                MultipartBody.Part fileBody = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                RequestBody uploadType = RequestBody.create(MultipartBody.FORM, customerUploadType);

                fileResponseLiveData = uploadLinkViewModel.uploadFile(customerId, fileBody, uploadType);
                fileResponseLiveData.observe(this, new Observer<FileResponse>() {
                    @Override
                    public void onChanged(@Nullable FileResponse fileResponse) {

                    }
                });

            }

        }else{
            Toast.makeText(this,"NO SELECTED FILES !!!", Toast.LENGTH_SHORT).show();
        }

    }

    public void observeResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            //dialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            //dialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            //dialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
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
        uploadLinkViewModel = ViewModelProviders.of(this).get(UploadLinkViewModel.class);
        customerId = getIntent().getStringExtra(Constants.CUSTOMER_ID);

        dialog = Utils.customerProgressBar(this);
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

    private class FileUploadTask extends AsyncTask<Map<String, Uri>, Integer, Void>{

        @Override
        protected Void doInBackground(Map<String, Uri>... maps) {
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }
    }
}
