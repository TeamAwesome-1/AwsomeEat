package com.teamawsome.awsomeeat.Admin;

import com.google.firebase.firestore.Exclude;

public class RestaurantAdmin {
    public String name;
    public String adress;
    public String pictureUrl;
    @Exclude
    public String id;

    public RestaurantAdmin(){
        
    }

    public RestaurantAdmin(String name, String adress) {
     this.name = name;
     this.adress = adress;
        // this.pictureUrl = pictureUrl;
    }
}


