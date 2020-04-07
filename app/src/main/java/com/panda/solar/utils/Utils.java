package com.panda.solar.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.app.ProgressDialog;

import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.CustomerList;

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
        return String.format("%,.2f", amount) + " UGX";
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
}
