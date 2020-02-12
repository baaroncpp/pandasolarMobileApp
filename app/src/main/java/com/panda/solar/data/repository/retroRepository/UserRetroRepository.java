package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

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
    private static UserRetroRepository instance;

    public static UserRetroRepository getInstance(){
        if(instance == null){
            instance = new UserRetroRepository();
        }
        return instance;
    }

    @Override
    public User addUser(User user) {
        return null;
    }

    @Override
    public User getUserById(String id) {

        Call<User> user = pandaCoreAPI.getUserById(id);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    bad_request="BAD REQUEST";
                }
                pandaUser = response.body(); //.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                connection_failed = "CONNECTION FAILURE: "+t.getMessage();
            }
        });
        return pandaUser;
    }

    @Override
    public User getUserByUsername(String username) {

        Log.e("userRepository", "accessed");

        Call<User> user = pandaCoreAPI.getUserByUsername(username);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    bad_request="BAD REQUEST";
                }
                //pandaUser = response.body();//.postValue(response.body());
                pandaUser = userExample();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pandaUser = userExample();
                connection_failed = "CONNECTION FAILURE user: "+t.getMessage();
            }
        });
        return pandaUser;
    }

    @Override
    public List<User> getUsers(String query) {
        return null;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    public static String getBad_request() {
        return bad_request;
    }

    public User getPandaUser() {
        return pandaUser;
    }


    public User userExample(){

        User user = new User();
        user.setEmail("baaronlubega1@yahoo.com");
        user.setFirstname("Timothy");
        user.setLastname("Hatanga");
        user.setUsertype("ADMIN");
        user.setIsactive(true);
        user.setPrimaryphone("256773039553");

        return user;
    }
}
