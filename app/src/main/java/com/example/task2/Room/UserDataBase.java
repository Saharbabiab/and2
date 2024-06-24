package com.example.task2.Room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {UsersTable.class}, version = 2)

public abstract class UserDataBase extends RoomDatabase {
    private static UserDataBase instance;

    public static synchronized UserDataBase getInstance(Context context) {
        if(instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDataBase.class, "user_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract UserDao userDao();


}
