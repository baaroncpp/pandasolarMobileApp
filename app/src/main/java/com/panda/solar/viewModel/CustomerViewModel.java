package com.panda.solar.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.CustomerDAO;

import java.util.List;

public class CustomerViewModel extends ViewModel {

    private CustomerDAO customerDAO = PandaDAOFactory.getCustomerDAO();
    private MutableLiveData<List<Customer>> customers;
    private static  String jwtToken = "";

    public MutableLiveData<List<Customer>> getCustomers(){

        if(customers == null){
            customers = new MutableLiveData<>();
            customers.postValue(customerDAO.getCustomers(jwtToken));
            return customers;
        }else{
            return customers;
        }
    }

}