package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;

import com.panda.solar.Model.entities.User;

import java.util.List;

public interface UserDAO {

    public User addUser(User user);

    public User getUserById(String id);

    public User getUserByUsername(String username);

    public List<User> getUsers(String query);

    public User updateUser(User user);

}
