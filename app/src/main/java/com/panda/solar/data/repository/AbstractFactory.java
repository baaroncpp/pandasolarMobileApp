package com.panda.solar.data.repository;

import com.panda.solar.data.repository.retroRepository.CustomerDAO;
import com.panda.solar.data.repository.retroRepository.LeaseOfferDAO;
import com.panda.solar.data.repository.retroRepository.PayGoProductDAO;
import com.panda.solar.data.repository.retroRepository.PaymentDAO;
import com.panda.solar.data.repository.retroRepository.ProductDAO;
import com.panda.solar.data.repository.retroRepository.ReportsDAO;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
import com.panda.solar.data.repository.retroRepository.UploadLinkDAO;
import com.panda.solar.data.repository.retroRepository.UserDAO;

public abstract class AbstractFactory {

    public static CustomerDAO getCustomerDAO(){
        return null;
    }

    public static UserDAO getUserDAO(){
        return null;
    }

    public static ProductDAO getProductDAO(){return null;}

    public static SaleDAO getSaleDAO(){return null;}

    public static LeaseOfferDAO getLeaseOfferDAO(){return null;}

    public static PayGoProductDAO getPayGoProductDAO(){return null;}

    public static PaymentDAO getPaymentDAO(){return null;}

    public static UploadLinkDAO getUploadLinkDAO(){return null;}

    public static ReportsDAO getReportDAO(){return null;}

}
