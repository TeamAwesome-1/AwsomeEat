package com.teamawsome.awsomeeat.Model;

public class Order {

    private String ProductId;
    private String RestaurantId;
    private String ProductName;
    private String Price;
    private String Quantity;
    private String UserId;
    private String documentId;

    public Order() {
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Order(String restaurantId, String productId, String productName, String quantity, String price, String userId ) {
        RestaurantId = restaurantId;
        ProductId = productId;
        ProductName = productName;
        Quantity = quantity;
        Price = price;
        UserId = userId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getProductId() {
        return ProductId;
    }

    public void setProductId(String productId) {
        ProductId = productId;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }


}
