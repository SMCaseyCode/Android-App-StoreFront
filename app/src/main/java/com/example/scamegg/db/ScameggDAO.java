package com.example.scamegg.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;


import com.example.scamegg.Cart.Cart;
import com.example.scamegg.Item.Item;
import com.example.scamegg.Orders.Order;
import com.example.scamegg.User.User;

import java.util.List;

@Dao
public interface ScameggDAO {

    //---------------- USER DATABASE START --------------------

    @Insert
    void insert(User... users);

    @Update
    void update(User... users);

    @Delete
    void delete(User users);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE)
    List<User> getAllUsers();

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userID = :userID")
    User getUserByUserID(int userID);

    //---------------- USER DATABASE END --------------------


    //---------------- ITEM DATABASE START ------------------

    @Insert
    void insert(Item... items);

    @Update
    void update(Item... items);

    @Delete
    void delete(Item... items);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE)
    List<Item> getAllItems();

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE itemName = :itemName")
    Item getItemByName(String itemName);

    @Query("SELECT * FROM " + AppDatabase.ITEM_TABLE + " WHERE itemID = :itemID")
    Item getItemByItemID(int itemID);


    //---------------- ITEM DATABASE END --------------------

    //---------------- CART DATABASE START ------------------
    @Insert
    void insert(Cart... carts);

    @Update
    void update(Cart... carts);

    @Delete
    void delete(Cart... carts);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE userID = :userID ")
    List<Cart> getAllCartsByUserID(int userID);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE userID = :userID")
    Cart getCartByUserID(int userID);

    @Query("SELECT * FROM " + AppDatabase.CART_TABLE + " WHERE quantity = :quantity")
    Cart getCartByItemID(int quantity);

    //---------------- CART DATABASE END ------------------

    //---------------- ORDER DATABASE START ------------------
    @Insert
    void insert(Order... orders);

    @Update
    void update(Order... orders);

    @Delete
    void delete(Order... orders);

    @Query("SELECT * FROM " + AppDatabase.ORDER_TABLE + " WHERE userID = :userID ")
    List<Order> getAllOrdersByUserID(int userID);

    //---------------- ORDER DATABASE END ------------------


}
