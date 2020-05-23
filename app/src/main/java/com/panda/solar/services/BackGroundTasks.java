package com.panda.solar.services;

import android.content.Context;

import com.panda.solar.utils.NotificationsUtil;

public class BackGroundTasks {

    private static final String ACTION_APPROVE_SALE = "approve_sale";
    public static final String ACTION_DISMISS_SALE_NOTIFICATION = "dismiss_sale_notification";
    public static final String ACTION_APPROVE_SALE_NOTIFICATION = "approve_sale_notification";

    public static void executeTask(Context context, String action){

        if(ACTION_DISMISS_SALE_NOTIFICATION.equals(action)){
            NotificationsUtil.clearAllNotifications(context);
        }else if(ACTION_APPROVE_SALE_NOTIFICATION.equals(action)){
            NotificationsUtil.clearAllNotifications(context);
        }

    }

    public static void approveSale(){

    }
}
