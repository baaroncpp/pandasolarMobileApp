package com.panda.solar.data.repository;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.panda.solar.data.repository.roomRepository.TokenRoomDAO;

public abstract class PandaAppDB extends RoomDatabase {

    private static PandaAppDB instance;

    public abstract TokenRoomDAO tokenRoomDAO();//here room subclasses our abstract class, creates all the methods

    public static synchronized PandaAppDB getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), PandaAppDB.class, "panda_app_database")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }
}
