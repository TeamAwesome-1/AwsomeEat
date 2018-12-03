package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class Restaurant {
    public String name;
    public String adress;
    public String pictureUrl;
    public String phoneNumber;
    @Exclude
    private String id;

    public Restaurant(){
        
    }

    public Restaurant(String name, String adress, String phoneNumber) {
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;

       // this.pictureUrl = pictureUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}



