package com.example.scamegg.Cart;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.scamegg.db.AppDatabase;

@Entity(tableName = AppDatabase.CART_TABLE)
public class Cart {

    @PrimaryKey(autoGenerate = true)
    private int cartID;

    private int userID;
    private int itemID;
    private int quantity;

    public Cart(int userID, int itemID, int quantity) {
        this.userID = userID;
        this.itemID = itemID;
        this.quantity = quantity;
    }

    public int getCartID() {
        return cartID;
    }

    public void setCartID(int cartID) {
        this.cartID = cartID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
