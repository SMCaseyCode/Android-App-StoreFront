package com.example.scamegg.Item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.scamegg.db.AppDatabase;

@Entity(tableName = AppDatabase.ITEM_TABLE)
public class Item {

    @PrimaryKey(autoGenerate = true)
    private int itemID;

    private String itemName;
    private String itemCategory;
    private int itemQuantity;
    private String itemPrice;

    public Item(String itemName, String itemCategory, int itemQuantity, String itemPrice) {
        this.itemName = itemName;
        this.itemCategory = itemCategory;
        this.itemQuantity = itemQuantity;
        this.itemPrice = itemPrice;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemCategory() {
        return itemCategory;
    }

    public void setItemCategory(String itemCategory) {
        this.itemCategory = itemCategory;
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(int itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }
}
