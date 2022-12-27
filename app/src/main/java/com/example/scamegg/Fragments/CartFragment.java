package com.example.scamegg.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scamegg.Cart.Cart;
import com.example.scamegg.Cart.CartItemModel;
import com.example.scamegg.Cart.CartRecyclerViewAdapter;
import com.example.scamegg.Item.Item;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class CartFragment extends Fragment {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String SHARED_PREFS = "SHARED_PREFS";

    private Activity referenceActivity;
    private View parentHolder;

    private ScameggDAO mScameggDAO;
    private SharedPreferences mPreferences;

    private ArrayList<CartItemModel> mCartItemModels = new ArrayList<>();

    private List<Item> mItemList = new ArrayList<>();

    private int[] mItemImages = {R.drawable.gpunew, R.drawable.cpusvg, R.drawable.mobosvg};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_cart, container, false);

        getDatabase();
        getPreferences();
        setItemModels();

        RecyclerView recyclerView = parentHolder.findViewById(R.id.recyclerView);
        Objects.requireNonNull(recyclerView.getItemAnimator()).setRemoveDuration(160);

        CartRecyclerViewAdapter adapter = new CartRecyclerViewAdapter(parentHolder, referenceActivity, mCartItemModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(referenceActivity));

        return parentHolder;
    }

    private void setItemModels(){
        int userID = mPreferences.getInt(USER_ID_KEY, -1);

        mCartItemModels.clear();
        List<Cart> cartList = mScameggDAO.getAllCartsByUserID(userID);

        for (int i = 0; i < cartList.size(); i++){

            Cart cart = cartList.get(i);
            Item item = mScameggDAO.getItemByItemID(cart.getItemID());

            String itemName = item.getItemName();
            String itemPrice = item.getItemPrice();
            int cartQuantity = cart.getQuantity();
            int imageID = 0;

            switch (item.getItemCategory().toLowerCase(Locale.ROOT)){
                case "gpu":
                    imageID = 0;
                    break;

                case "cpu":
                    imageID = 1;
                    break;

                case "motherboard":
                    imageID = 2;
                    break;
            }

            mCartItemModels.add(new CartItemModel(itemName, cartQuantity, itemPrice, mItemImages[imageID]));
        }
    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(referenceActivity, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    private void getPreferences(){
        mPreferences = referenceActivity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }
}