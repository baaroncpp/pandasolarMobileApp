package com.panda.solar.utils;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.app.ProgressDialog;

import com.panda.solar.Model.entities.User;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.HomeActivity;
import com.panda.solar.viewModel.UserViewModel;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by macosx on 15/01/2019.
 */

public class Utils {

    public Dialog getFullWindowDialog(Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(LayoutInflater.from(context).inflate(R.layout.progress_bar, null));

        Dialog dialog = builder.create();
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return dialog;

    }

    public Dialog getDialog(Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(LayoutInflater.from(context).inflate(R.layout.tint_dialog_bg_layout, null));

        Dialog dialog = builder.create();

        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        //window.clearFlags(-1);

        return dialog;
    }


    public void clearPreferences(){

    }

    public static File getDestinationFile(String fileName){
        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/PandaSolar");
        if (!myDir.exists()) {
            myDir.mkdirs();
        }
        File file = new File(myDir, fileName);
        return file;
    }

    public static String getImagePath(Context context, Uri uri){
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);
        if(cursor != null){
            cursor.moveToFirst();
            String document_id = cursor.getString(0);
            document_id = document_id.substring(document_id.lastIndexOf(":")+1);
            cursor.close();

            cursor = context.getContentResolver().query(
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                    null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
            if (cursor != null) {
                cursor.moveToFirst();

                if(cursor.getCount() > 0){
                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    cursor.close();
                    return path;
                }else{
                    return "";
                }
            }
            else{
                return "";
            }

        }
        return "";

    }

    public static String insertCharacterForEveryNDistance(int distance, String original, char c){
        StringBuilder sb = new StringBuilder();
        char[] charArrayOfOriginal = original.toCharArray();
        for(int ch = 0 ; ch < charArrayOfOriginal.length ; ch++){
            if(ch % distance == 0)
                sb.append(c).append(charArrayOfOriginal[ch]);
            else
                sb.append(charArrayOfOriginal[ch]);
        }
        return sb.toString();
    }

    public static String readableDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM yyyy");
        return formatter.format(date);
    }

    public static String readableShortDate(Date date){

        Date today = new Date();

        if(today == date){
            return "Today";
        }else{
            SimpleDateFormat formatter = new SimpleDateFormat("dd MM yyyy");
            return formatter.format(date);
        }
    }

    public static ProgressDialog customerProgressBar(Context context){

        ProgressDialog dialog;
        dialog = new ProgressDialog(context);
        dialog.setMessage("Please wait...");
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(R.style.AppCompatDialogStyle);

        return dialog;
    }

    public static SweetAlertDialog customSweetAlertDialog(Context context){
        final SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("Loading");
        dialog.setCancelable(false);

        return dialog;
    }

    public static String moneyFormatter(float amount){
        return String.format("%,.1f", amount) + " UGX";
    }

    public static boolean validatePhoneNumberInput(String phoneNumber){
        String result = phoneNumber.replaceAll(" ","");

        if(result.length() == 10){
            if(Character.getNumericValue(result.charAt(0)) == 0 && Character.getNumericValue(result.charAt(1)) == 7){
                return true;
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public static String saleStatus(short i){
        if(i == 1){
            return "Pending";
        }else if(i == 2){
            return "Approved";
        }else{
            return "Pending";
        }
    }

    public static String getSharedPreference(String val){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        return sharedPreferences.getString(val, null);
    }

    public static void logoutUtil(Context context){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor e = sharedPreferences.edit();
        e.remove(Constants.JWT_TOKEN);
        e.clear();
        e.commit();
    }

    public static boolean isSharedPreferenceSet(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return (sharedPref.getAll().size() >= 1);
    }

    public static void saveUserDetails(User user){
        SharedPreferences sharedPreferences = AppContext.getAppContext().getSharedPreferences(Constants.SHARED_PREF, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Constants.USER_ID, user.getId());
        editor.putString(Constants.USER_TYPE, user.getUsertype());
        editor.commit();
    }

    public static void setUserDetails(Context context){
        UserViewModel userViewModel = ViewModelProviders.of((FragmentActivity) context).get(UserViewModel.class);
        LiveData<User> liveUser = userViewModel.getUser();

        liveUser.observe((LifecycleOwner) context, new Observer<User>() {
            @Override
            public void onChanged(@Nullable User user) {
                saveUserDetails(user);
            }
        });
    }

    public static void appPermissions(Context context, int requestCode){

        switch (requestCode){
            case Constants.CAMERA_PERMISSION_CODE:
                requestCameraPermission(context);
                break;

            case Constants.WRITE_STORAGE_PERMISSION_CODE:
                requestWriteStoragePermission(context);
                break;

            case Constants.READ_STORAGE_PERMISSION_CODE:
                requestReadStoragePermission(context);
                break;

            case Constants.LOCATION_PERMISSION_CODE:
                requestLocationPermissions(context);
                break;

            default:
                break;
        }
    }

    public static void requestWriteStoragePermission(final Context context){

        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to write to device internal storage")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_STORAGE_PERMISSION_CODE);
        }
    }

    public static void requestReadStoragePermission(final Context context){

        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.READ_EXTERNAL_STORAGE)){
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to access device internal storage")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.READ_STORAGE_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, Constants.READ_STORAGE_PERMISSION_CODE);
        }
    }

    public static void requestCameraPermission(final Context context){

        if(ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)){
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to access the device internal storage")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION_CODE);
                        }
                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        }else{
            ActivityCompat.requestPermissions((Activity) context, new String[] {Manifest.permission.CAMERA}, Constants.CAMERA_PERMISSION_CODE);
        }
    }

    public static void requestLocationPermissions(Context context) {
        ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, Constants.LOCATION_PERMISSION_CODE);
    }

    public static boolean checkLocationPermissions(Context context) {
        if (ActivityCompat.checkSelfPermission((Activity)context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission((Activity)context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    //clearing cache
    public static void deleteCache(Context context) {
        try {
            File dir = context.getCacheDir();
            deleteDir(dir);
        } catch (Exception e) { e.printStackTrace();}
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
            return dir.delete();
        } else if(dir!= null && dir.isFile()) {
            return dir.delete();
        } else {
            return false;
        }
    }

}
