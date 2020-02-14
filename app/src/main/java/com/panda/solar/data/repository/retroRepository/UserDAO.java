package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.User;

import java.util.List;

public interface UserDAO {

    public MutableLiveData<User> addUser(User user);

    public MutableLiveData<User> getUserById(String id);

    public MutableLiveData<User> getUserByUsername(String username);

    public MutableLiveData<List<User>> getUsers(String query);

    public MutableLiveData<User> updateUser(User user);

}
