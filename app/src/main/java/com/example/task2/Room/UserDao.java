package com.example.task2.Room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM Users")
    List<UsersTable> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertUser(UsersTable userstable);

    @Query("DELETE FROM Users")
    void deleteAllUsers();

    @Query("SELECT * FROM Users WHERE uid = :uid")
    UsersTable getUserById(String uid);


}
