package com.example.task2.Room;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "Users",indices = { @Index(value = {"uid"}, unique = true) })
public class UsersTable {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "counter")
    public  int counter;
    @ColumnInfo(name = "uid")
    public String uid;
    @ColumnInfo(name = "firstname")
    public String firstname;
    @ColumnInfo(name = "lastname")
    public String lastname;
    @ColumnInfo(name = "city")
    public String city;
    @ColumnInfo(name = "country")
    public String country;
    @ColumnInfo(name = "img")
    public  String img;
}
