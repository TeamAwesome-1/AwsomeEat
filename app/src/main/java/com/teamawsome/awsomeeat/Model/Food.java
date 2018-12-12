package com.teamawsome.awsomeeat.Model;

public class Food {

    private String name;
    private String image;
    private String description;
    private String price;
    private String discount;
    private String restaurantId;
    private String category;
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
        this.category = category;

    }

    public Food(String name, String price, String restaurantId, String category, String image) {
        this.name = name;
        this.price = price;
        this.restaurantId = restaurantId;
        this.category = category;
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
        this.description = description;
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

    public String getCategory() { return category; }

    public void setCategory(String category) { this.category = category; }


}
