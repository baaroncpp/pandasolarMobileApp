package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.panda.solar.Model.entities.User;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UserDAO;

import java.util.List;

public class UserViewModel extends ViewModel {

    private UserDAO userDAO = PandaDAOFactory.getUserDAO();

    public LiveData<User> getUser(){
        Log.e("userViewModel","accessed");
        return userDAO.getUser();
    }

    public LiveData<List<User>> getAllUser(String userType, int page, int size, String sortby, String sortorder){
        return userDAO.getUsers(userType, page, size, sortby, sortorder);
    }
}
