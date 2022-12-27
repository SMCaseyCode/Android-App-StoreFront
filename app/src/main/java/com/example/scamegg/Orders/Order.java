package com.example.scamegg.Orders;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.scamegg.db.AppDatabase;

@Entity(tableName = AppDatabase.ORDER_TABLE)
public class Order {

    @PrimaryKey(autoGenerate = true)
    private int orderID;

    private int userID;
    private String itemID;
    private String quantity;
    private String orderTotal;

    public Order(int userID, String itemID, String quantity, String orderTotal) {
        this.userID = userID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.orderTotal = orderTotal;
    }

    public int getOrderID() {
        return orderID;
    }

    public void setOrderID(int orderID) {
        this.orderID = orderID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }
}
