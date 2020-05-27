package com.panda.solar.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.activities.R;
import com.panda.solar.presentation.view.activities.PaymentsList;
import com.panda.solar.presentation.view.activities.SalesList;
import com.panda.solar.services.AppNotificationsService;
import com.panda.solar.services.BackGroundTasks;

public class NotificationsUtil {

    private static final int NOTIFICATION_PENDING_INTENT_ID = 111;
    private static final int SALE_NOTIFICATION_ID = 112;
    private static final int ACTION_IGNORE_SALE_PENDING_INTENT_ID = 113;
    private static final int ACTION_APPROVE_SALE_PENDING_INTENT_ID = 114;

    private static final int PAYMENT_NOTIFICATION_ID = 115;
    private static final int ACTION_VIEW_PAYMENT_PENDING_INTENT_ID = 116;
    private static final int ACTION_IGNORE_PAYMENT_PENDING_INTENT_ID = 116;

    private static final String SALE_NOTIFICATION_CHANNEL_ID = "sale_notification_channel";
    private static final String PAYMENT_NOTIFICATION_CHANNEL_ID = "payment_notification_channel";

    private static PendingIntent contentSaleIntent(Context context, SaleModel sale){

        //TO DO change from saleList activity to saleDetails activity
        Intent saleActitvityIntent = new Intent(context, SalesList.class);
        saleActitvityIntent.putExtra(Constants.SALE_OBJ, sale);

        //wrap the intentactivity to be opened on notification click with a pending intent
        return PendingIntent.getActivity(context, NOTIFICATION_PENDING_INTENT_ID, saleActitvityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static PendingIntent contentPaymentIntent(Context context){

        Intent paymentActitvityIntent = new Intent(context, PaymentsList.class);

        //wrap the intentactivity to be opened on notification click with a pending intent
        return PendingIntent.getActivity(context, NOTIFICATION_PENDING_INTENT_ID, paymentActitvityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    private static Bitmap largeIcon(Context context){

        Resources res = context.getResources();

        Bitmap largeIcon = BitmapFactory.decodeResource(res, R.drawable.panda_icon_circle);

        return largeIcon;
    }

    //make sure u clear notication after activity loads
    public static void clearAllNotifications(Context context){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }

    public static void notifyOnNewSale(Context context, SaleModel sale){

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(SALE_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, SALE_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.dark_grey))
                .setSmallIcon(R.drawable.panda_icon_circle)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(sale.getSaletype()+" Sale")
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.panda_sale)
                ))
                .setContentText(sale.getAgent().getUser().getFirstname()+" "+sale.getAgent().getUser().getLastname())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentSaleIntent(context, sale))
                .addAction(ignoreSaleAction(context))
                //.addAction(approveSaleAction(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(SALE_NOTIFICATION_ID, notificationBuilder.build());
    }

    public static void notifyPayment(Context context, LeasePayment leasePayment){
        NotificationManager notificationManager = (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel mChannel = new NotificationChannel(PAYMENT_NOTIFICATION_CHANNEL_ID,
                    context.getString(R.string.main_notification_channel_name),
                    NotificationManager.IMPORTANCE_HIGH);

            notificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, PAYMENT_NOTIFICATION_CHANNEL_ID)
                .setColor(ContextCompat.getColor(context, R.color.dark_grey))
                .setSmallIcon(R.drawable.panda_icon_circle)
                .setLargeIcon(largeIcon(context))
                .setContentTitle(leasePayment.getPayeename())
                .setStyle(new NotificationCompat.BigTextStyle().bigText(
                        context.getString(R.string.panda_payment)
                ))
                .setContentText(leasePayment.getPayeemobilenumber()+"\n AMOUNT "+leasePayment.getAmount())
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setContentIntent(contentPaymentIntent(context))
                .addAction(ignorePaymentAction(context))
                .setAutoCancel(true);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN && Build.VERSION.SDK_INT < Build.VERSION_CODES.O){
            notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        }

        notificationManager.notify(PAYMENT_NOTIFICATION_ID, notificationBuilder.build());
    }

    private static NotificationCompat.Action ignorePaymentAction(Context context){

        Intent ignorePaymentIntent = new Intent(context, AppNotificationsService.class);
        ignorePaymentIntent.setAction(BackGroundTasks.ACTION_DISMISS_SALE_NOTIFICATION);

        PendingIntent ignorePaymentPendingIntent = PendingIntent.getService(context,
                ACTION_IGNORE_PAYMENT_PENDING_INTENT_ID,
                ignorePaymentIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignorePaymentAction = new NotificationCompat.Action(R.drawable.ic_not_interested_black_24dp,
                "Dismiss",
                ignorePaymentPendingIntent);

        return ignorePaymentAction;
    }

    private static NotificationCompat.Action ignoreSaleAction(Context context){

        Intent ignoreSaleIntent = new Intent(context, AppNotificationsService.class);
        ignoreSaleIntent.setAction(BackGroundTasks.ACTION_DISMISS_SALE_NOTIFICATION);

        PendingIntent ignoreSalePendingIntent = PendingIntent.getService(context,
                ACTION_IGNORE_SALE_PENDING_INTENT_ID,
                ignoreSaleIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action ignoreSaleAction = new NotificationCompat.Action(R.drawable.ic_not_interested_black_24dp,
                "Not Interested",
                ignoreSalePendingIntent);

        return ignoreSaleAction;
    }

    private static NotificationCompat.Action approveSaleAction(Context context){

        Intent approveSaleIntent = new Intent(context, AppNotificationsService.class);
        approveSaleIntent.setAction(BackGroundTasks.ACTION_APPROVE_SALE_NOTIFICATION);

        PendingIntent approveSalePendingIntent = PendingIntent.getService(context,
                ACTION_APPROVE_SALE_PENDING_INTENT_ID,
                approveSaleIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Action approveSaleAction = new NotificationCompat.Action(R.drawable.ic_check_black_24dp,
                "Approve",
                approveSalePendingIntent);

        return approveSaleAction;
    }
}
