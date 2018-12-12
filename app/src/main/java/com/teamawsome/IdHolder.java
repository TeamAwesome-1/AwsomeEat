package com.teamawsome;

import com.teamawsome.awsomeeat.Model.Food;
import com.teamawsome.awsomeeat.Model.Restaurant;

public class IdHolder {

    private static String restaurantId;
    private static String categoryId;
    private static String foodId;
    private static Restaurant restaurant;
    private static Food selectedFood;

    private static final IdHolder idHolder = new IdHolder();

    private IdHolder(){

    }

    public static IdHolder getInstance(){
        return idHolder;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        idHolder.restaurant = restaurant;
    }

    public Food getSelectedFood() {
        return selectedFood;
    }

    public void setSelectedFood(Food seletedFood) {
        idHolder.selectedFood = seletedFood;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        idHolder.restaurantId = restaurantId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        idHolder.categoryId = categoryId;
    }

    public String getFoodId() {
        return foodId;
    }

    public void setFoodId(String foodId) {
        idHolder.foodId = foodId;
    }
}
