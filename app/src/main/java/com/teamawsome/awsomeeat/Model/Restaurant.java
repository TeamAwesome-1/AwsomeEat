package com.teamawsome.awsomeeat.Model;

import com.google.firebase.firestore.Exclude;

public class Restaurant {
    public String name;
    public String adress;
    public String pictureUrl;
    public String phoneNumber;
    public String id;

    public Restaurant() {

    }

    public Restaurant(String name, String adress, String phoneNumber, String pictureUrl) {
        this.name = name;
        this.adress = adress;
        this.phoneNumber = phoneNumber;
        this.pictureUrl = pictureUrl;


    }

    public String getName() {
        return name;
    }

    public String getAdress() {
        return adress;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getId () {
        return id;
    }


}

