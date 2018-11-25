package com.teamawsome.awsomeeat.Model;

import com.google.firebase.database.Exclude;

public class Food {

    private String Name;
    @Exclude
    private String Image;
    @Exclude
    private String Description;
    @Exclude
    private String Price;
    @Exclude
    private String Discount;
    @Exclude
    private String MenuId;
    public String Category;


    private String id;
    public Food() {

    }

    public Food(String name, String image, String description, String price, String discount, String menuId, String category ) {
        this.Name = name;
        this.Image = image;
        this.Description = description;
        this.Price = price;
        this.Discount= discount;
        this.MenuId = menuId;
        this.Category = category;

    }

    public Food(String name, String category) {
        this.Name = name;
        this.Category = category;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

   public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getDiscount() {
        return Discount;
    }

    public void setDiscount(String discount) {
        Discount = discount;
    }

    public String getMenuId() {
        return MenuId;
    }

    public void setMenuId(String menuId) {
        MenuId = menuId;
    }


}
