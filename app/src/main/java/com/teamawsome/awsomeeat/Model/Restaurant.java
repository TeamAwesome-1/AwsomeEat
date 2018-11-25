package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class Restaurant {
    public String name;
    public String adress;
    public String pictureUrl;
    @Exclude
    private String id;

    public Restaurant(){
        
    }

    public Restaurant(String name, String adress) {
        this.name = name;
        this.adress = adress;
       // this.pictureUrl = pictureUrl;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}



