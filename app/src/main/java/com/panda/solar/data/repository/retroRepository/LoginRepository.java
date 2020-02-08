package com.panda.solar.data.repository.retroRepository;

import com.panda.solar.Model.entities.Login;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.data.network.PandaCoreAPI;
import com.panda.solar.data.network.RetroService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginRepository {

    private PandaCoreAPI pandaCoreAPI;
    private static Token token;
    private static String bad_request = "";
    private static String connection_fail = "";

    public LoginRepository() {
        this.pandaCoreAPI = RetroService.getPandaCoreAPI();
    }

    public Token userLogin(Login login){

        Call<Token> call = pandaCoreAPI.bkLogin(login);
        call.enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                if(!response.isSuccessful()){
                    bad_request = "BAD REQUEST";
                    return;
                }
                token = response.body();
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                //connection_fail = "CONNECTION FAILURE";
                token = new Token("sdgbuighiusdhughduihg");
                return;
            }
        });
        return token;
    }

    public static String getBad_request() {
        return bad_request;
    }

    public static String getConnection_fail() {
        return connection_fail;
    }
}
