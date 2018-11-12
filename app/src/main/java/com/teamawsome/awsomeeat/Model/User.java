package com.teamawsome.awsomeeat.Model;

public class User {
    private String Name;
    private String Password;
    private String Roll;


    public User()
    {

    }

    public User(String name, String password) {
        Name = name;
        Password = password;
    }

    public String getPhone() {
        return Roll;
    }

    public void setPhone(String roll) {
        Roll = roll;
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
}
