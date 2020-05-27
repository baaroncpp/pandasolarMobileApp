package com.panda.solar.services;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.panda.solar.Model.entities.AgentMeta;
import com.panda.solar.Model.entities.CustomerMeta;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.Product;
import com.panda.solar.Model.entities.SaleModel;
import com.panda.solar.activities.R;
import com.panda.solar.utils.NotificationsUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

public class PandaFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        if(title.equalsIgnoreCase("PAYMENT")){
            sendPaymentNotification(getLeasePayment(remoteMessage));
        }else if(title.equalsIgnoreCase("APPROVE SALE")){
            sendSaleNotification(getSaleModel(remoteMessage));
        }else if(title.equalsIgnoreCase("SALE APPROVED")){
            sendApprovalNotification(getSaleModel(remoteMessage));
        }
    }

    private void sendSaleNotification(SaleModel sale){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getResources().getString(R.string.pref_sale_notification_key), getResources().getBoolean(R.bool.pref_sale_notification_default))){
            NotificationsUtil.notifyOnNewSale(this, sale);
        }
    }

    private void sendPaymentNotification(LeasePayment payment){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getResources().getString(R.string.pref_payment_notification_key),getResources().getBoolean(R.bool.pref_payment_notification_default))){
            NotificationsUtil.notifyPayment(this, payment);
        }
    }

    private void sendApprovalNotification(SaleModel sale){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        if(sharedPreferences.getBoolean(getResources().getString(R.string.pref_sale_notification_key), getResources().getBoolean(R.bool.pref_sale_notification_default))){
            NotificationsUtil.notifyOnNewSale(this, sale);
        }
    }

    public SaleModel getSaleModel(RemoteMessage remoteMessage){

        JSONObject jsonSaleModel = new JSONObject(remoteMessage.getData());
        Gson gson = new Gson();
        SaleModel saleModel = new SaleModel();

        saleModel = gson.fromJson(jsonSaleModel.toString(), SaleModel.class);

        JSONObject jsonAgent = null;
        try {
            jsonAgent = new JSONObject(remoteMessage.getData().get("agent"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        AgentMeta agent = gson.fromJson(jsonAgent.toString(), AgentMeta.class);

        JSONObject jsonProduct = null;
        try {
            jsonProduct = new JSONObject(remoteMessage.getData().get("product"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Product product = gson.fromJson(jsonProduct.toString(), Product.class);

        JSONObject jsonCustomer = null;

        try {
            jsonCustomer = new JSONObject(remoteMessage.getData().get("customer"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CustomerMeta customer = gson.fromJson(jsonCustomer.toString(), CustomerMeta.class);

        saleModel.setAgent(agent);
        saleModel.setCustomer(customer);
        saleModel.setProduct(product);

        return saleModel;

    }

    public LeasePayment getLeasePayment(RemoteMessage remoteMessage){

        JSONObject jsonPayment = new JSONObject(remoteMessage.getData());
        Gson gson = new Gson();

        return gson.fromJson(jsonPayment.toString(), LeasePayment.class);
    }
}
