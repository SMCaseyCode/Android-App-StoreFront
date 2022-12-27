package com.example.scamegg.Item;

public class ItemModel {

    private String mItemName;
    private String mItemCategory;
    private String mItemPrice;
    private int mImage;

    public ItemModel(String mItemName, String mItemCategory, String mItemPrice, int mImage) {
        this.mItemName = mItemName;
        this.mItemCategory = mItemCategory;
        this.mItemPrice = mItemPrice;
        this.mImage = mImage;
    }

    public String getItemName() {
        return mItemName;
    }

    public String getItemCategory() {
        return mItemCategory;
    }

    public String getItemPrice() {
        return mItemPrice;
    }

    public int getImage() {
        return mImage;
    }
}
