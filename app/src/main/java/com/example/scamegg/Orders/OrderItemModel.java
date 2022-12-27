package com.example.scamegg.Orders;

public class OrderItemModel {

    private int userID;
    private String itemID;
    private String quantity;
    private String orderTotal;

    public OrderItemModel(int userID, String itemID, String quantity, String orderTotal) {
        this.userID = userID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.orderTotal = orderTotal;
    }

    public int getUserID() {
        return userID;
    }

    public String getItemID() {
        return itemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getOrderTotal() {
        return orderTotal;
    }
}
