package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class User {
    private String uid;
    private String adress;

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    private boolean admin;
    @Exclude
    public String id;


    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public User() {

    }


    public User(String adress, String uid) {
        this.adress = adress;
        this.uid = uid;
        this.admin = false;
    }

    public User(String uid){
        this.uid = uid;
        this.admin = false;
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
