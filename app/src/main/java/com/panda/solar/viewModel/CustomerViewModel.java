package com.panda.solar.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.Customer;
import com.panda.solar.Model.entities.User;
import com.panda.solar.data.repository.retroRepository.CustomerRetroRepository;

import java.util.ArrayList;
import java.util.List;

public class CustomerViewModel extends ViewModel {

    private CustomerRetroRepository customerRetroRepository;
    private MutableLiveData<List<Customer>> customers;



    public CustomerViewModel(){
        super();
        this.customerRetroRepository = new CustomerRetroRepository();//CustomerRetroRepository.getInstance();
    }

    public void init() {
        this.customerRetroRepository = new CustomerRetroRepository();
    }

    public MutableLiveData<List<Customer>> getCustomers(){

        if(customers == null){
            customers = new MutableLiveData<>();
            customers.setValue(customerRetroRepository.getCustomers());
            //customers.setValue(cust());
            return customers;
        }else{
            return customers;
        }
    }

    public List<Customer> cust(){
        List<Customer> c = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            User user = new User();
            Customer cu = new Customer();

            user.setEmail("baaronlubega1@gmail.com");
            user.setFirstname("Lady");
            user.setLastname("Leah");

            cu.setAddress("Ndinda");
            cu.setSecondaryphone("256 773 039 553");
            cu.setUser(user);

            c.add(cu);
        }
        return c;
    }
}