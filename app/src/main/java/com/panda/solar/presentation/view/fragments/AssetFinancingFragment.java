package com.panda.solar.presentation.view.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.panda.solar.activities.BuildConfig;
import com.panda.solar.presentation.view.activities.DirectAssetFinancingActivity;
import com.panda.solar.activities.R;
import com.panda.solar.utils.GlideImageHandler;
import com.panda.solar.utils.ImageCompression;
import com.panda.solar.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;


public class AssetFinancingFragment extends Fragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    private static AssetFinancingFragment assetFinancingFragment;

    private EditText initialDepositEditText;
    private Button assetFinancingNextButton;
    private Button cameraButton;
    private Button galleryButton;
    private AppCompatSpinner spinner;
    private ImageView IDImageView;

    private int ACTION_IMAGE_CAPTURE_REQUEST_CODE = 2;
    private int ACTION_GALLERY_REQUEST_CODE = 3;

    private int IMAGE_VIEW_WIDTH;
    private int IMAGE_VIEW_HEIGHT;

    private String PROFILE_ID_IMAGE_NAME;
    private String DIRECTORY_PATH = Environment.getExternalStorageDirectory().getAbsolutePath()+"/PandaSolar/";
    private String IMAGE_PATH;

    private Bitmap IDBitmap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PROFILE_ID_IMAGE_NAME = "panda_id_test.jpg";
        IMAGE_PATH = DIRECTORY_PATH + PROFILE_ID_IMAGE_NAME;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asset_financing, container, false);
        initView(view);
        return view;
    }

    private void initView(View view){
        initialDepositEditText = (EditText)view.findViewById(R.id.initial_deposit_edittext);
        assetFinancingNextButton = (Button)view.findViewById(R.id.asset_financing_next_button);
        spinner = (AppCompatSpinner)view.findViewById(R.id.payment_plan_spinner);
        galleryButton = (Button)view.findViewById(R.id.gallery_button);
        cameraButton = (Button)view.findViewById(R.id.camera_button);
        IDImageView = (ImageView)view.findViewById(R.id.id_image_view);

        initialDepositEditText.setKeyListener(null);
        initialDepositEditText.setFocusableInTouchMode(false);
        initialDepositEditText.setFocusable(false);

        initialDepositEditText.setText("UGX 20,000");

        cameraButton.setOnClickListener(this);
        galleryButton.setOnClickListener(this);
        assetFinancingNextButton.setOnClickListener(this);
        setUpSpinner();

        view.getViewTreeObserver().addOnGlobalLayoutListener(this);

    }

    private void setUpSpinner(){
        List<String> paymentPlanList = new ArrayList<>();
        paymentPlanList.add("Daily");
        paymentPlanList.add("Weekly");
        paymentPlanList.add("Monthly");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, paymentPlanList);
        spinner.setAdapter(adapter);
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() != null && ((DirectAssetFinancingActivity)getActivity()).getSupportActionBar() != null)
            ((DirectAssetFinancingActivity) getActivity()).getSupportActionBar().setTitle(R.string.asset_financing);
    }

    public static AssetFinancingFragment getInstance(){
       if(assetFinancingFragment == null){
           assetFinancingFragment = new AssetFinancingFragment();
       }
       return assetFinancingFragment;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.asset_financing_next_button:
                if(getActivity() != null){
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.sale_container, new ReceiptFragment(), ReceiptFragment.class.getSimpleName())
                            .addToBackStack(null)
                            .commit();
                }
                break;

            case R.id.camera_button:
                openCamera();
                break;

            case R.id.gallery_button:
                openGallery();
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        IMAGE_VIEW_HEIGHT = IDImageView.getHeight();
        IMAGE_VIEW_WIDTH = IDImageView.getWidth();
    }

    private void openCamera(){
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        File file = Utils.getDestinationFile(PROFILE_ID_IMAGE_NAME);
        Uri imageUri = null;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT){
            file =  Utils.getDestinationFile(PROFILE_ID_IMAGE_NAME);
            imageUri = Uri.fromFile(file);
        } else {
            if(getActivity() != null){
                imageUri = FileProvider.getUriForFile(getActivity(), BuildConfig.APPLICATION_ID + ".provider",file);
            }

        }

        pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        if(getActivity() != null)
         if(pictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(pictureIntent, ACTION_IMAGE_CAPTURE_REQUEST_CODE);
        }
    }

    private void openGallery(){
        Intent galleryIntent = new Intent();
        galleryIntent.setType("image/*");
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(galleryIntent, "Select Picture"), ACTION_GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == ACTION_IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            new ImageCompression(getActivity(), new ImageCompression.ImageCompressionListener() {
                @Override
                public void filePath(String filePath) {
                    GlideImageHandler glideImageHandler = new GlideImageHandler(getActivity(), Uri.fromFile(new File(filePath)), IDImageView);
                    glideImageHandler.load(IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT, cameraCallback);
                }
            }).execute(IMAGE_PATH, PROFILE_ID_IMAGE_NAME);
        }

        else if(requestCode == ACTION_GALLERY_REQUEST_CODE && resultCode == RESULT_OK){

            if (data != null)
            {
                Uri uri = data.getData();
                final String path = Utils.getImagePath(getActivity(), uri);

                new ImageCompression(getActivity(), new ImageCompression.ImageCompressionListener() {
                    @Override
                    public void filePath(String filePath) {
                        GlideImageHandler glideImageHandler = new GlideImageHandler(getActivity(), Uri.fromFile(new File(path)), IDImageView);
                        glideImageHandler.load(IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT, galleryCallback);
                    }
                }).execute(path, PROFILE_ID_IMAGE_NAME);

            }
        }

    }

    private GlideImageHandler.GlideLoaderCallback cameraCallback = new GlideImageHandler.GlideLoaderCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }
    };

    private GlideImageHandler.GlideLoaderCallback galleryCallback = new GlideImageHandler.GlideLoaderCallback() {
        @Override
        public void onSuccess() {

        }

        @Override
        public void onFailure() {

        }
    };


}
