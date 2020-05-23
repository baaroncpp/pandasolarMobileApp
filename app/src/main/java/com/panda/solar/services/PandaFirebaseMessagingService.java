package com.panda.solar.services.loaders;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.panda.solar.Model.entities.LeasePayment;
import com.panda.solar.Model.entities.SaleModel;

public class PandaFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();

        //TO DO
        SaleModel saleModel = new SaleModel();
        LeasePayment leasePayment = new LeasePayment();

        if(title.equalsIgnoreCase("PAYMENT")){
            sendPaymentNotification(leasePayment);
        }else if(title.equalsIgnoreCase("SALE")){
            sendSaleNotification(saleModel);
        }
    }

    private void sendSaleNotification(SaleModel sale){

    }

    private void sendPaymentNotification(LeasePayment payment){

    }
}
