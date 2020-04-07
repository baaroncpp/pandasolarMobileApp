package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.Model.entities.User;
import com.panda.solar.utils.ResponseCallBack;

import java.util.List;

import retrofit2.Response;

public interface UserDAO {

    public MutableLiveData<Token> authenticateUser(ResponseCallBack callBack, Login login);

    public MutableLiveData<User> addUser(ResponseCallBack callBack, User user);

    public MutableLiveData<User> getUserById(String id);

    public MutableLiveData<User> getUser(ResponseCallBack callBack);

    public MutableLiveData<List<User>> getUsers(String userType, int page, int size, String sortby, String sortorder);

    public MutableLiveData<User> updateUser(User user);

}
