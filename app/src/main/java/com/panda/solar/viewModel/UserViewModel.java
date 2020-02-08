package com.panda.solar.viewModel;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.panda.solar.Model.entities.User;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UserDAO;
import com.panda.solar.data.repository.retroRepository.UserRetroRepository;

public class UserViewModel extends ViewModel {

    private UserDAO userDAO = PandaDAOFactory.getUserDAO();
    private MutableLiveData<User> user = new MutableLiveData<>();
    private String jwtToken = "";

    public MutableLiveData<User> getUser(String username){

        if(user == null){
            user = new MutableLiveData<>();
            user.postValue(userDAO.getUserByUsername(jwtToken, username));
        }
        return null;
    }
}
