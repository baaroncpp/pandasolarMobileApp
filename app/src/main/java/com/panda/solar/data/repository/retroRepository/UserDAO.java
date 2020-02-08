package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.User;

import java.util.List;

public interface UserDAO {

    public User addUser(String jwt, User user);

    public User getUserById(String jwt, String id);

    public User getUserByUsername(String jwt, String username);

    public List<User> getUsers(String jwt, String query);

    public User updateUser(String jwt, User user);

}
