package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.CustomerDAO;

import java.util.List;

public class CustomerViewModel extends ViewModel {

    private CustomerDAO customerDAO = PandaDAOFactory.getCustomerDAO();

    public LiveData<List<Customer>> getCustomers(int page, int size, String sortby, String sortorder){
        return customerDAO.getCustomers(page, size, sortby,sortorder);
    }

}