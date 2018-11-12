package com.teamawsome.awsomeeat.ResturantList;

import com.google.firebase.firestore.Exclude;

public class Restaurant {
    public String name;
    public String adress;
    public String pictureUrl;
    @Exclude
    public String id;

    public Restaurant(){
        
    }

    public Restaurant(String name, String adress, String pictureUrl) {
        this.name = name;
        this.adress = adress;
        this.pictureUrl = pictureUrl;
    }
}



