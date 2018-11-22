package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class User {
    public String uid;
    public String adress;
    @Exclude
    public String id;


    public User(){

    }

    public User(String adress, String uid) {
        this.adress = adress;
        this.uid = uid;
    }




    /*
    private String Name;
    private String Password;
    private String Phone;


    public User()
    {

    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String roll) {
        Phone = Phone;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
    */
}
