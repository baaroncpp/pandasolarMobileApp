package com.panda.solar.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.panda.solar.utils.NotificationsUtil;
import com.panda.solar.utils.Utils;

public class BackGroundTasks {

    private static final String ACTION_GET_USER_DETAILS = "get_user_details";
    public static final String ACTION_DISMISS_SALE_NOTIFICATION = "dismiss_sale_notification";
    public static final String ACTION_APPROVE_SALE_NOTIFICATION = "approve_sale_notification";
    public static final String ACTION_DISMISS_PAYMENT_NOTIFICATION = "dismiss_payment_notification";

    public static void executeTask(Context context, String action){

        if(ACTION_DISMISS_SALE_NOTIFICATION.equals(action)){
            NotificationsUtil.clearAllNotifications(context);
        }else if(ACTION_APPROVE_SALE_NOTIFICATION.equals(action)){
            NotificationsUtil.clearAllNotifications(context);
        }else if(ACTION_GET_USER_DETAILS.equals(action)){
            setUserDetails(context);
        }else if(ACTION_DISMISS_PAYMENT_NOTIFICATION.equals(action)){
            NotificationsUtil.clearAllNotifications(context);
        }

    }

    public static void setUserDetails(Context context){
        Utils.setUserDetails(context);
    }
}
