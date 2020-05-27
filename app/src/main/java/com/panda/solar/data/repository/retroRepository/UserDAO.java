package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.AndroidTokens;
import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.Model.entities.User;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

import retrofit2.Response;

public interface UserDAO {

    MutableLiveData<Token> authenticateUser(ResponseCallBack callBack, Login login);

    MutableLiveData<User> addUser(ResponseCallBack callBack, User user);

    MutableLiveData<User> getUserById(String id);

    MutableLiveData<User> getUser(ResponseCallBack callBack);

    MutableLiveData<List<User>> getUsers(String userType, int page, int size, String sortby, String sortorder);

    MutableLiveData<User> updateUser(User user);

    MutableLiveData<AndroidTokens> registerDeviceFCM(ResponseCallBack callBack, String deviceToken);

    MutableLiveData<User> changePassword(ResponseCallBack callBack, String oldPassword, String newPassword);

}
