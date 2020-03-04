package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.User;

import java.util.List;

import retrofit2.Response;

public interface UserDAO {

    public MutableLiveData<User> addUser(User user);

    public MutableLiveData<User> getUserById(String id);

    public MutableLiveData<User> getUser();

    public MutableLiveData<List<User>> getUsers(String userType, int page, int size, String sortby, String sortorder);

    public MutableLiveData<User> updateUser(User user);

    public Response getUserResponse();

}
