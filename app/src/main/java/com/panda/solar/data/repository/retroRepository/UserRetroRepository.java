package com.panda.solar.data.repository.retroRepository;

import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.panda.solar.Model.entities.AndroidTokens;
import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.Model.entities.User;
import com.panda.solar.data.network.NetworkResponse;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.data.network.mocks.LoginRetroService;
import com.panda.solar.utils.ResponseCallBack;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRetroRepository implements UserDAO{

    private PandaCoreAPI pandaCoreAPI = RetroService.getPandaCoreAPI();
    private PandaCoreAPI loginAPI = LoginRetroService.getPandaCoreAPI();
    private static UserRetroRepository instance;
    NetworkResponse netResponse = new NetworkResponse();

    public static UserRetroRepository getInstance(){
        if(instance == null){
            instance = new UserRetroRepository();
        }
        return instance;
    }

    @Override
    public MutableLiveData<Token> authenticateUser(final ResponseCallBack callBack, Login login) {

        final MutableLiveData<Token> resultData = new MutableLiveData<>();
        Call<Token> call = loginAPI.bkLogin(login);

        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!response.isSuccessful()){
                    netResponse.setBody(response.message());
                    netResponse.setCode(response.code());
                    callBack.onError(netResponse);
                    return;
                }
                callBack.onSuccess();
                resultData.postValue(response.body());
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return resultData;
    }

    @Override
    public MutableLiveData<User> addUser(final ResponseCallBack callBack, User user) {

        final MutableLiveData<User> dataResult = new MutableLiveData<>();
        Call<User> call = pandaCoreAPI.addUser(user);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    NetworkResponse netRes = new NetworkResponse();
                    netRes.setBody(response.message());
                    netRes.setCode(response.code());
                    callBack.onError(netRes);
                    return;
                }
                callBack.onSuccess();
                dataResult.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

    @Override
    public MutableLiveData<User> getUserById(String id) {

        final MutableLiveData<User> pandaUser = new MutableLiveData<>();
        Call<User> user = pandaCoreAPI.getUserById(id);
        user.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    return;
                }
                pandaUser.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                return;
            }
        });
        return pandaUser;
    }

    @Override
    public MutableLiveData<User> getUser(final ResponseCallBack callBack) {

        Log.e("userRepository", "accessed");
        final MutableLiveData<User> pandaUser = new MutableLiveData<>();

        Call<User> call = pandaCoreAPI.getUser();
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    NetworkResponse netRes = new NetworkResponse();
                    netRes.setBody(response.message());
                    netRes.setCode(response.code());
                    callBack.onError(netRes);
                    return;
                }
                callBack.onSuccess();
                pandaUser.postValue(response.body());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.onFailure();
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

    @Override
    public MutableLiveData<AndroidTokens> registerDeviceFCM(final ResponseCallBack callBack, final String deviceToken) {

        final MutableLiveData<AndroidTokens> dataResult = new MutableLiveData<>();
        Call<AndroidTokens> call = pandaCoreAPI.registerDevice(deviceToken);

        call.enqueue(new Callback<AndroidTokens>() {
            @Override
            public void onResponse(Call<AndroidTokens> call, Response<AndroidTokens> response) {
                if(!response.isSuccessful()){
                    NetworkResponse net = new NetworkResponse();
                    net.setCode(response.code());
                    net.setBody(response.message());
                    callBack.onError(net);
                    return;
                }
                dataResult.postValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<AndroidTokens> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

    @Override
    public MutableLiveData<User> changePassword(final ResponseCallBack callBack, String oldPassword, String newPassword) {

        final MutableLiveData<User> dataResult = new MutableLiveData<>();
        Call<User> call = pandaCoreAPI.changepassword(oldPassword, newPassword);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(!response.isSuccessful()){
                    NetworkResponse netResponse = new NetworkResponse();
                    netResponse.setCode(response.code());
                    try {
                        netResponse.setBody(new JSONObject(response.errorBody().string()).getString("error"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    callBack.onError(netResponse);
                    return;
                }
                dataResult.postValue(response.body());
                callBack.onSuccess();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                callBack.onFailure();
                return;
            }
        });
        return dataResult;
    }

    public User getPandaUser() {
        return null;
    }
}
