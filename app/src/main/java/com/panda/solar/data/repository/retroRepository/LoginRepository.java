package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import com.panda.solar.data.network.mocks.LoginRetroService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private PandaCoreAPI pandaCoreAPI;
    private static Token token;

    public LoginRepository() {
        this.pandaCoreAPI = LoginRetroService.getPandaCoreAPI();
    }

    public Token userLogin(Login login){

        Call<Token> call = pandaCoreAPI.bkLogin(login);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!response.isSuccessful()){
                    return;
                }
                token = response.body();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                return;
            }
        });
        return token;
    }

}
