package com.panda.solar.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.panda.solar.Model.entities.User;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UserDAO;

public class UserViewModel extends ViewModel {

    private UserDAO userDAO = PandaDAOFactory.getUserDAO();
    private MutableLiveData<User> user;// = new MutableLiveData<>();

    public MutableLiveData<User> getUser(String username){
        Log.e("userViewModel","accessed");

        if(user == null){
            user = new MutableLiveData<>();
            user.setValue(userDAO.getUserByUsername(username));
            Log.e("userViewModel","accessed 2");
        }
        return user;
    }
}
