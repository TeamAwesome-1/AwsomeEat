package com.teamawsome.awsomeeat.Model;

import com.google.firebase.database.Exclude;

public class Food {

    private String name;
    @Exclude
    private String image;
    @Exclude
    private String description;
    @Exclude
    private String price;
    @Exclude
    private String discount;
    @Exclude
    private String restaurantId;

  /*  public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        this.Category = category;
    } */

    public String Category;
    private String id;



    public Food() {

    }

    public Food(String name, String image, String description, String price, String discount, String restaurantId, String category ) {
        this.name = name;
        this.image = image;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.restaurantId = restaurantId;
        this.Category = category;

    }

    public Food(String name, String price, String restaurantId, String Category, String image) {
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
        this.Category = Category;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   //TODO ändra category till att skrivas med gemener istället annars fungerar inte gettern
   /*public String getCategory() {
        return Category;
    }*/



    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }


}
