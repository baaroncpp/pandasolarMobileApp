package com.panda.solar.data.repository.roomRepository;

import android.app.Application;
import android.os.AsyncTask;
import com.panda.solar.Model.entities.Token;
import com.panda.solar.data.repository.PandaAppDB;

public class TokenRoomRepository {

    private TokenRoomDAO tokenRoomDAO;

    public TokenRoomRepository(Application application){
        PandaAppDB pandaAppDB = PandaAppDB.getInstance(application);
        tokenRoomDAO = pandaAppDB.tokenRoomDAO();
    }

    public void saveToken(Token token){
        new SaveTokenAsyncTask(tokenRoomDAO).execute(token);
    }

    public void refreshToken(Token token){
        new RefreshTokenAsyncTask(tokenRoomDAO).execute(token);
    }

    public void deleteToken(Token token){
        new DeleteTokenAsyncTask(tokenRoomDAO).execute();
    }

    public Token getToken(){
        return new GetTokenAsyncTask(tokenRoomDAO).doInBackground();
    }

    public static class SaveTokenAsyncTask extends AsyncTask<Token, Void, Void>{

        private TokenRoomDAO tokenRoomDAO;

        public SaveTokenAsyncTask(TokenRoomDAO tokenRoomDAO){
            this.tokenRoomDAO = tokenRoomDAO;
        }

        @Override
        protected Void doInBackground(Token... tokens) {
            tokenRoomDAO.saveToken(tokens[0]);
            return null;
        }
    }

    public static class DeleteTokenAsyncTask extends AsyncTask<Token, Void, Void>{

        private TokenRoomDAO tokenRoomDAO;

        public DeleteTokenAsyncTask(TokenRoomDAO tokenRoomDAO){
            this.tokenRoomDAO = tokenRoomDAO;
        }

        @Override
        protected Void doInBackground(Token... tokens) {
            tokenRoomDAO.deleteToken(tokens[0]);
            return null;
        }
    }

    public static class RefreshTokenAsyncTask extends AsyncTask<Token, Void, Void>{

        private TokenRoomDAO tokenRoomDAO;

        public RefreshTokenAsyncTask(TokenRoomDAO tokenRoomDAO){
            this.tokenRoomDAO = tokenRoomDAO;
        }

        @Override
        protected Void doInBackground(Token... tokens) {
            tokenRoomDAO.refreshToken(tokens[0]);
            return null;
        }
    }

    public static class GetTokenAsyncTask extends AsyncTask<Void, Void, Token>{

        private TokenRoomDAO tokenRoomDAO;

        public GetTokenAsyncTask(TokenRoomDAO tokenRoomDAO){
            this.tokenRoomDAO = tokenRoomDAO;
        }

        @Override
        protected Token doInBackground(Void... voids) {
            return tokenRoomDAO.getToken();
        }
    }
}
