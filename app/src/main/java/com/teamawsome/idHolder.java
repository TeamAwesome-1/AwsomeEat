package com.teamawsome;

import com.teamawsome.awsomeeat.Model.Food;

public class idHolder {

    private static String restaurantId;
    private static String categoryId;
    private static String foodId;

    private static Food seletedFood;

    public static Food getSeletedFood() {
        return seletedFood;
    }

    public static void setSeletedFood(Food seletedFood) {
        idHolder.seletedFood = seletedFood;
    }

    public static String getRestaurantId() {
        return restaurantId;
    }

    public static void setRestaurantId(String restaurantId) {
        idHolder.restaurantId = restaurantId;
    }

    public static String getCategoryId() {
        return categoryId;
    }

    public static void setCategoryId(String categoryId) {
        idHolder.categoryId = categoryId;
    }

    public static String getFoodId() {
        return foodId;
    }

    public static void setFoodId(String foodId) {
        idHolder.foodId = foodId;
    }
}
