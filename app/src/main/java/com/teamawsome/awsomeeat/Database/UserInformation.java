package com.teamawsome.awsomeeat.Database;

import android.util.Log;

public class UserInformation {
    private String adress;

    public String getAdress() {
        Log.d("User", "userInformation Class getAdress: " + adress);
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }
}
