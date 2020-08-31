package com.panda.solar.viewModel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.data.repository.roomRepository.TokenRoomRepository;

public class TokenViewModel extends AndroidViewModel {

    TokenRoomRepository tokenRepository;

    public TokenViewModel(@NonNull Application application) {
        super(application);
        tokenRepository = new TokenRoomRepository(application);
    }

    public Token getToken(){return tokenRepository.getToken();}

    //delete on logout
    public void  deleteToken(Token token){tokenRepository.deleteToken(token);}

    //on login
    public void saveToken(Token token){tokenRepository.saveToken(token);}

}
