package com.panda.solar.viewModel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.Model.entities.User;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.repository.PandaDAOFactory;
import com.panda.solar.data.repository.retroRepository.UserDAO;
import com.panda.solar.utils.Constants;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

public class UserViewModel extends ViewModel {

    private UserDAO userDAO = PandaDAOFactory.getUserDAO();
    private MutableLiveData<String> responseMessage = new MutableLiveData<>();
    private MutableLiveData<NetworkResponse> networkResponse = new MutableLiveData<>();

    public LiveData<User> getUser(){
        Log.e("userViewModel","accessed");
        return userDAO.getUser(new ResponseCallBack() {
            @Override
            public void onSuccess() {
                responseMessage.postValue(Constants.SUCCESS_RESPONSE);
            }

            @Override
            public void onFailure() {
                responseMessage.postValue(Constants.FAILURE_RESPONSE);
            }

            @Override
            public void onError(NetworkResponse response) {
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        });
    }

    public LiveData<Token> authenticateUser(Login login){
        return userDAO.authenticateUser(new ResponseCallBack() {
            @Override
            public void onSuccess() {
                responseMessage.postValue(Constants.SUCCESS_RESPONSE);
            }

            @Override
            public void onFailure() {
                responseMessage.postValue(Constants.FAILURE_RESPONSE);
            }

            @Override
            public void onError(NetworkResponse response) {
                networkResponse.postValue(response);
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, login);
    }

    public LiveData<List<User>> getAllUser(String userType, int page, int size, String sortby, String sortorder){
        return userDAO.getUsers(userType, page, size, sortby, sortorder);
    }

    public LiveData<User> addUser(User user){
        return userDAO.addUser(new ResponseCallBack() {
            @Override
            public void onSuccess() {
                responseMessage.postValue(Constants.SUCCESS_RESPONSE);
            }

            @Override
            public void onFailure() {
                responseMessage.postValue(Constants.FAILURE_RESPONSE);
            }

            @Override
            public void onError(NetworkResponse response) {
                networkResponse.postValue(response);
                responseMessage.postValue(Constants.ERROR_RESPONSE);
            }
        }, user);
    }

    public LiveData<String> getResponseMessage(){
        return responseMessage;
    }

    public LiveData<NetworkResponse> getNetworkResponse(){
        return networkResponse;
    }
}
