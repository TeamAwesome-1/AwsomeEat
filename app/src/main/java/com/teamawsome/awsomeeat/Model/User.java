package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class User {
    private String uid;
    private String adress;
    private boolean admin = false;
    @Exclude
    public String id;

    public User() {

    }

    public User(String uid, boolean admin){
        this.uid = uid;
        this.admin = admin;
    }

    public User(String adress, String uid) {
        this.adress = adress;
        this.uid = uid;
        this.admin = false;
    }

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

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
