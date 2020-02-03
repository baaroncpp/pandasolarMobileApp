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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;

import com.panda.solar.activities.BuildConfig;
import com.panda.solar.activities.R;
import com.panda.solar.utils.GlideImageHandler;
import com.panda.solar.utils.ImageCompression;
import com.panda.solar.utils.Utils;

import java.io.File;

import static android.app.Activity.RESULT_OK;

public class HousePictureFragment extends Fragment implements View.OnClickListener, ViewTreeObserver.OnGlobalLayoutListener {

    private Button pictureButton;
    private ImageView houseImageView;
    public static final int ACTION_IMAGE_CAPTURE_REQUEST_CODE = 3;

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
        View view = inflater.inflate(R.layout.fragment_house_picture, container, false);

        pictureButton = (Button)view.findViewById(R.id.camera_button);
        houseImageView = (ImageView)view.findViewById(R.id.house_image_view);

        pictureButton.setOnClickListener(this);

        view.getViewTreeObserver().addOnGlobalLayoutListener(this);

        return view;
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
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.camera_button:
                openCamera();
                break;
        }
    }

    @Override
    public void onGlobalLayout() {
        IMAGE_VIEW_HEIGHT = houseImageView.getHeight();
        IMAGE_VIEW_WIDTH = houseImageView.getWidth();
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
                this.startActivityForResult(pictureIntent, ACTION_IMAGE_CAPTURE_REQUEST_CODE);
            }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == ACTION_IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            new ImageCompression(getActivity(), new ImageCompression.ImageCompressionListener() {
                @Override
                public void filePath(String filePath) {
                    GlideImageHandler glideImageHandler = new GlideImageHandler(getActivity(), Uri.fromFile(new File(filePath)), houseImageView);
                    glideImageHandler.load(IMAGE_VIEW_WIDTH, IMAGE_VIEW_HEIGHT, cameraCallback);
                }
            }).execute(IMAGE_PATH, PROFILE_ID_IMAGE_NAME);
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

}
