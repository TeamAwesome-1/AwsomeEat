package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class userObject {
    String uid;
    String name;
    String adress;
    @Exclude
    public String id;


    public userObject(){

    }

    public userObject(String adress, String uid) {
        this.adress = adress;
        this.uid = uid;
    }

}



