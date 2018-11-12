package com.teamawsome.awsomeeat.Model;

import java.util.List;

public class Request {

    private String roll;
    private String name;
    private String address;
    private String total;
    private String status;
    private List<Order> foods; //list of food order

    public Request() {
    }

    public Request(String roll, String name, String address, String total, List<Order> foods) {
        this.roll = roll;
        this.name = name;
        this.address = address;
        this.total = total;
        this.foods = foods;
        this.status="0"; //Default value is 0, 0:Placed, 1: rejected, 2:accpeted
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public String getRoll() {
        return roll;
    }

    public void setRoll(String roll) {
        this.roll = roll;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<Order> getFoods() {
        return foods;
    }

    public void setFoods(List<Order> foods) {
        this.foods = foods;
    }
}
