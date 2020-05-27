package com.panda.solar.presentation.view.activities;

import android.app.NotificationManager;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.app.ProgressDialog;

import com.panda.solar.Model.constants.CustomerUploadType;
import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.FileResponse;
import com.panda.solar.activities.R;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.FileUtil;
import com.panda.solar.utils.Utils;
import com.panda.solar.viewModel.UploadLinkViewModel;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class AddCustMeta extends AppCompatActivity {

    private Map<String, Uri> uploadUris;

    private ImageView profileImageView;
    private MaterialButton addProfileBtn;
    private MaterialButton viewProfileBtn;
    private MaterialButton uploadProfileBtn;

    private ImageView idImageView;
    private MaterialButton viewIdBtn;
    private MaterialButton addIdBtn;
    private MaterialButton uploadIdBtn;
    private TextView idType;

    private ImageView coiImageView;
    private MaterialButton viewCoiBtn;
    private MaterialButton addCoiBtn;
    private MaterialButton uploadCoiBtn;

    private TextView coiFileName;
    private TextView idFileName;
    private TextView profileFileName;

    private UploadLinkViewModel uploadLinkViewModel;
    private LiveData<FileResponse> fileResponseLiveData;
    private Customer customer;

    private ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cust_meta);

        ActionBar actionBar = this.getSupportActionBar();

        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        init();

        viewIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(customer.getIdcopypath());
            }
        });

        viewCoiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(customer.getConsentformpath());
            }
        });

        viewProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewImage(customer.getProfilephotopath());
            }
        });

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

        uploadIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = uploadUris.get(Constants.CUSTOMER_ID_PATH);

                if(uri != null){
                    FileUploadModel fileUploadModel = new FileUploadModel();
                    fileUploadModel.setUri(uri);
                    fileUploadModel.setIploadType(Constants.CUSTOMER_ID_PATH);

                    FileUploadTask fileUploadTask = new FileUploadTask();
                    fileUploadTask.execute(fileUploadModel);


                    //uploadFile(uri, Constants.CUSTOMER_ID_PATH);
                }else{
                    Toast.makeText(AddCustMeta.this, "Add an ID image", Toast.LENGTH_SHORT).show();
                }

            }
        });

        uploadCoiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = uploadUris.get(Constants.CUSTOMER_COI_PATH);

                if(uri != null){
                    FileUploadModel fileUploadModel = new FileUploadModel();
                    fileUploadModel.setUri(uri);
                    fileUploadModel.setIploadType(Constants.CUSTOMER_COI_PATH);

                    FileUploadTask fileUploadTask = new FileUploadTask();
                    fileUploadTask.execute(fileUploadModel);

                    //uploadFile(uri, Constants.CUSTOMER_COI_PATH);
                }else{
                    Toast.makeText(AddCustMeta.this, "Add an consent form image", Toast.LENGTH_SHORT).show();
                }

            }
        });

        uploadProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = uploadUris.get(Constants.CUSTOMER_PROFILE_PATH);

                if(uri != null){
                    FileUploadModel fileUploadModel = new FileUploadModel();
                    fileUploadModel.setUri(uri);
                    fileUploadModel.setIploadType(Constants.CUSTOMER_PROFILE_PATH);

                    FileUploadTask fileUploadTask = new FileUploadTask();
                    fileUploadTask.execute(fileUploadModel);

                    //uploadFile(uri, Constants.CUSTOMER_PROFILE_PATH);
                }else{
                    Toast.makeText(AddCustMeta.this, "Add an profile image", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

/*
    public void uploadFile(Uri uri, String upload){

        String customerUploadType = "";
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

        dialog.show();
        fileResponseLiveData = uploadLinkViewModel.uploadFile(customer.getUserid(), fileBody, uploadType);
        fileResponseLiveData.observe(this, new Observer<FileResponse>() {
            @Override
            public void onChanged(@Nullable FileResponse fileResponse) {
                observeResponse();
            }
        });
    }
*/

    public void observeResponse(){

            LiveData<String> responseMsg = uploadLinkViewModel.getResponseMessage();
            responseMsg.observe(this, new Observer<String>() {
                @Override
                public void onChanged(@Nullable String s) {
                    handleResponse(s);
                }
            });
    }

    public void handleResponse(String msg){
        if(msg.equals(Constants.SUCCESS_RESPONSE)){
            dialog.dismiss();
        }else if(msg.equals(Constants.ERROR_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(this,"SOMETHING WENT WRONG !!!", Toast.LENGTH_SHORT).show();
        }else if(msg.equals(Constants.FAILURE_RESPONSE)){
            dialog.dismiss();
            Toast.makeText(this,"CONNECTION FAILURE", Toast.LENGTH_SHORT).show();
        }
    }

    public void setProgress(){
        dialog = new ProgressDialog(this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.setMax(100);
    }

    public void init(){
        profileImageView = findViewById(R.id.customer_profile_view);
        addProfileBtn = findViewById(R.id.add_cust_profile_btn);
        viewProfileBtn = findViewById(R.id.cust_profileview_btn);
        uploadProfileBtn = findViewById(R.id.cust_profileupload_btn);

        uploadIdBtn = findViewById(R.id.cust_idupload_btn);
        viewIdBtn = findViewById(R.id.cust_idview_btn);
        addIdBtn = findViewById(R.id.add_id_btn);
        idImageView = findViewById(R.id.cust_idImage_view);
        idType = findViewById(R.id.cust_idtype_name);

        addCoiBtn = findViewById(R.id.add_coi_btn);
        viewCoiBtn = findViewById(R.id.cust_coiview_btn);
        uploadCoiBtn = findViewById(R.id.cust_coiupload_btn);
        coiImageView = findViewById(R.id.cust_coi_image);

        coiFileName = findViewById(R.id.coi_file_name);
        idFileName = findViewById(R.id.cust_idfile_name);
        profileFileName = findViewById(R.id.profilename_view);

        uploadUris = new HashMap<>();
        uploadLinkViewModel = ViewModelProviders.of(this).get(UploadLinkViewModel.class);
        customer = getIntent().getParcelableExtra(Constants.CUSTOMER_OBJECT);
        setProgress();
        //dialog = Utils.customerProgressBar(this);

        //idType.setText(customer.getIdtype().toString());
        Picasso.with(this).load(customer.getProfilephotopath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(profileImageView);
        Picasso.with(this).load(customer.getIdcopypath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(idImageView);
        Picasso.with(this).load(customer.getConsentformpath()).fit().centerCrop().placeholder(R.drawable.ic_default_profile).error(R.drawable.ic_default_profile).into(coiImageView);
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
                profileFileName.setText(FileUtil.getFileName(this, data.getData()));
                profileImageView.setImageURI(data.getData());
            }

            if(requestCode == Constants.PICK_COI_IMAGE_REQUEST){
                uploadUris.put(Constants.CUSTOMER_COI_PATH, data.getData());
                coiFileName.setText(FileUtil.getFileName(this, data.getData()));
                coiImageView.setImageURI(data.getData());
            }

            if(requestCode == Constants.PICK_ID_IMAGE_REQUEST){
                uploadUris.put(Constants.CUSTOMER_ID_PATH, data.getData());
                idFileName.setText(FileUtil.getFileName(this, data.getData()));
                idImageView.setImageURI(data.getData());
            }

        }
    }

    public void viewImage(String filePath){
        Intent intent = new Intent();

        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.addCategory(android.content.Intent.CATEGORY_DEFAULT);
        intent.setDataAndType(Uri.parse(filePath), "image/*");

        startActivity(intent);
    }

    private class FileUploadTask extends AsyncTask<FileUploadModel, Integer, Void>{


        @Override
        protected Void doInBackground(FileUploadModel... fileUploadModels) {

            uploadFile(fileUploadModels[0].uri, fileUploadModels[0].uploadType);
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setProgress(0);
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            observeResponse();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.setProgress(values[0]);
            dialog.setMessage("File is uploading ...");
        }

        public void uploadFile(Uri uri, String upload){

            String customerUploadType = "";
            File file = null;

            try {
                file  = FileUtil.from(AddCustMeta.this, uri);
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

            //dialog.show();
            fileResponseLiveData = uploadLinkViewModel.uploadFile(customer.getUserid(), fileBody, uploadType);
            fileResponseLiveData.observe(AddCustMeta.this, new Observer<FileResponse>() {
                @Override
                public void onChanged(@Nullable FileResponse fileResponse) {
              //      observeResponse(fileResponse);
                }
            });
        }
    }

    private class FileUploadModel{
        private Uri uri;
        private String uploadType;

        public FileUploadModel(){super();}

        public Uri getUri() {
            return uri;
        }

        public String getUploadType() {
            return uploadType;
        }

        public void setUri(Uri uri) {
            this.uri = uri;
        }

        public void setIploadType(String uploadType) {
            this.uploadType = uploadType;
        }
    }
}
