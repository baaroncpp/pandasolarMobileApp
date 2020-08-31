package com.panda.solar.data.repository.roomRepository;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.panda.solar.Model.entities.Token;

@Dao
public interface TokenRoomDAO {

    @Insert
    void saveToken(Token token);

    @Update
    void refreshToken(Token token);

    @Delete
    void deleteToken(Token token);

    @Query("SELECT * FROM token_table ORDER BY token ASC LIMIT 1")
    Token getToken();

}
