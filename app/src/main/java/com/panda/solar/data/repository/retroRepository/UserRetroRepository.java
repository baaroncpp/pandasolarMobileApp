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
    private static Response userResponse;
    private static UserRetroRepository instance;

    public static UserRetroRepository getInstance(){
        if(instance == null){
            instance = new UserRetroRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<User> addUser(User user) {
        return null;
    }

    @Override
    public MutableLiveData<User> getUserById(String id) {

        final MutableLiveData<User> pandaUser = new MutableLiveData<>();
        Call<User> user = pandaCoreAPI.getUserById(id);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response;
                if(!response.isSuccessful()){
                    bad_request="BAD REQUEST";
                }
                pandaUser.setValue(response.body()); //.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                pandaUser.setValue(userExample());
                connection_failed = "CONNECTION FAILURE: "+t.getMessage();
            }
        });
        return pandaUser;
    }

    @Override
    public MutableLiveData<User> getUser() {

        Log.e("userRepository", "accessed");
        final MutableLiveData<User> pandaUser = new MutableLiveData<>();

        Call<User> user = pandaCoreAPI.getUser();
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                userResponse = response;
                if(!response.isSuccessful()){
                    bad_request="BAD REQUEST";
                    Log.e("","Not zuccessful");
                    return;
                }
                //.postValue(response.body());
                pandaUser.postValue(response.body());
                Log.e("","response loaded");
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                connection_failed = "CONNECTION FAILURE user: "+t.getMessage();
                return;
            }
        });
        return pandaUser;
    }

    @Override
    public MutableLiveData<List<User>> getUsers(String userType, int page, int size, String sortby, String sortorder) {

       /* final MutableLiveData<List<User>> data = new MutableLiveData<>();
        Call<UserList> userListCall = pandaCoreAPI.getAllUsers(userType, page, size, sortby, sortorder);

        userListCall.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if(!response.isSuccessful()){
                    return;
                }
                data.postValue(response.body().getUsers());
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                return;
            }
        });
        return data;*/
       return null;
    }

    @Override
    public MutableLiveData<User> updateUser(User user) {
        return null;
    }

    public static String getBad_request() {
        return bad_request;
    }

    public User getPandaUser() {
        return null;
    }

    @Override
    public Response getUserResponse(){
        return userResponse;
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
