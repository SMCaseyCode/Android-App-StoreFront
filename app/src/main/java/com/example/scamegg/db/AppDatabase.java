package com.example.scamegg.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.scamegg.Cart.Cart;
import com.example.scamegg.Item.Item;
import com.example.scamegg.Orders.Order;
import com.example.scamegg.User.User;

@Database(entities = {User.class, Item.class, Cart.class, Order.class}, version = 14)
public abstract class AppDatabase extends RoomDatabase {

    public static final String DB_NAME = "SCAMEGG_DATABASE";
    public static final String SCAMEGG_TABLE = "SCAMEGG_TABLE";
    public static final String USER_TABLE = "USER_TABLE";
    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String CART_TABLE = "CART_TABLE";
    public static final String ORDER_TABLE = "ORDER_TABLE";

    private static volatile AppDatabase instance;
    private static final Object LOCK = new Object();

    public abstract ScameggDAO getScameggDAO();

    public static AppDatabase getInstance(Context context){
        if (instance == null){
            synchronized (LOCK) {
                if (instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME).build();
                }
            }
        }

        return instance;
    }
}


