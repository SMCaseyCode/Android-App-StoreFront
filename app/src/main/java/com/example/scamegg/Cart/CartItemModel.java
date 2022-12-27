package com.example.scamegg.Cart;

public class CartItemModel {

    private String itemName;
    private int quantity;
    private String itemPrice;
    private int image;

    public CartItemModel(String itemName, int quantity, String itemPrice, int image) {
        this.itemName = itemName;
        this.quantity = quantity;
        this.itemPrice = itemPrice;
        this.image = image;
    }

    public String getItemName() {
        return itemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public int getImage() {
        return image;
    }
}
