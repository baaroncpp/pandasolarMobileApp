package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.User;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRetroRepository implements UserDAO{

    private PandaCoreAPI pandaCoreAPI = pandaCoreAPI = RetroService.getPandaCoreAPI();
    private static String connection_failed = "";
    private static String bad_request = "";
    private User pandaUser;

    @Override
    public User addUser(String jwt, User user) {
        return null;
    }

    @Override
    public User getUserById(String jwt, String id) {

        Call<User> user = pandaCoreAPI.getUserById(jwt, id);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    bad_request="BAD REQUEST";
                }
                pandaUser = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                connection_failed = "CONNECTION FAILURE: "+t.getMessage();
            }
        });
        return pandaUser;
    }

    @Override
    public User getUserByUsername(String jwt, String username) {

        Call<User> user = pandaCoreAPI.getUserByUsername(jwt, username);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    bad_request="BAD REQUEST";
                }
                pandaUser = response.body();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                connection_failed = "CONNECTION FAILURE: "+t.getMessage();
            }
        });
        return pandaUser;
    }

    @Override
    public List<User> getUsers(String jwt, String query) {
        return null;
    }

    @Override
    public User updateUser(String jwt, User user) {
        return null;
    }

    public static String getBad_request() {
        return bad_request;
    }

    public User getPandaUser() {
        return pandaUser;
    }
}
