package com.example.task2.User;

public class User {
    public String id;
    public String firstname;
    public String lastname;
    public String email;
    public double age;
    public String city;
    public String country;

    public  String img;

    public User(String id, String firstname, String lastname, String email, double age, String city, String country, String img) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.age = age;
        this.city = city;
        this.country = country;
        this.img = img;
    }

    public User() {
        this.id = id;
        this.firstname = "";
        this.lastname = "";
        this.email = "";
        this.age = 0;
        this.city = "";
        this.country = "";
        this.img = "";
    }


}
