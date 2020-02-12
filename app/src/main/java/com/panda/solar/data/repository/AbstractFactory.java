package com.panda.solar.data.repository;

import com.panda.solar.data.repository.retroRepository.CustomerDAO;
import com.panda.solar.data.repository.retroRepository.ProductDAO;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
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
}
