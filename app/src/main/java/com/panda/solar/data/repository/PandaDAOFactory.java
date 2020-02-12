package com.panda.solar.data.repository;

import com.panda.solar.data.repository.retroRepository.CustomerDAO;
import com.panda.solar.data.repository.retroRepository.CustomerRetroRepository;
import com.panda.solar.data.repository.retroRepository.ProductDAO;
import com.panda.solar.data.repository.retroRepository.ProductRepository;
import com.panda.solar.data.repository.retroRepository.SaleDAO;
import com.panda.solar.data.repository.retroRepository.SaleRepository;
import com.panda.solar.data.repository.retroRepository.UserDAO;
import com.panda.solar.data.repository.retroRepository.UserRetroRepository;

public abstract class PandaDAOFactory extends AbstractFactory {

    public static CustomerDAO getCustomerDAO(){
        return new CustomerRetroRepository();
    }

    public static UserDAO getUserDAO(){
        return UserRetroRepository.getInstance();
    }

    public static ProductDAO getProductDAO(){return ProductRepository.getInstance();}

    public static SaleDAO getSaleDAO(){return SaleRepository.getInstance();}

}
