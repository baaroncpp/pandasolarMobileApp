package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.panda.solar.Model.entities.User;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UserDAO;

public class UserViewModel extends ViewModel {

    private UserDAO userDAO = PandaDAOFactory.getUserDAO();

    public LiveData<User> getUser(String username){
        Log.e("userViewModel","accessed");
        return userDAO.getUserByUsername(username);
    }
}
