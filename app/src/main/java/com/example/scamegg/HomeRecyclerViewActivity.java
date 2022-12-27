package com.example.scamegg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.scamegg.Cart.Cart;
import com.example.scamegg.Item.Item;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.List;
import java.util.Locale;

public class HomeRecyclerViewActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String SHARED_PREFS = "SHARED_PREFS";

    ScameggDAO mScameggDAO;

    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_recycler_view);

        int[] mItemImages = {R.drawable.gpunew, R.drawable.cpusvg, R.drawable.mobosvg};

        ImageView itemImage = findViewById(R.id.recyclerItemImage);
        TextView itemName = findViewById(R.id.recyclerItemName);
        TextView itemCategory = findViewById(R.id.recyclerItemCategory);
        TextView itemPrice = findViewById(R.id.recyclerItemPrice);

        Button backButton = findViewById(R.id.BackBTN);
        Button addToCartButton = findViewById(R.id.homeAddToCartBTN);

        int imageID = 0;

        switch (getIntent().getStringExtra("ItemCategory").toLowerCase(Locale.ROOT)){
            case "gpu":
                break;

            case "cpu":
                imageID = 1;
                break;

            case "motherboard":
                imageID = 2;
                break;

        }

        itemImage.setImageResource(mItemImages[imageID]);
        itemName.setText(getIntent().getStringExtra("ItemName"));
        itemCategory.setText("CATEGORY: " + getIntent().getStringExtra("ItemCategory"));
        itemPrice.setText("ITEM PRICE: " + getIntent().getStringExtra("ItemPrice"));

        getDatabase();
        getPrefs();

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                startActivity(intent);
            }
        });

        addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(mScameggDAO.getItemByName(getIntent().getStringExtra("ItemName")).getItemID());
            }
        });
    }

    private void addToCart(int itemID){

        int userID = mPreferences.getInt(USER_ID_KEY, -1);
        List<Cart> allCarts;
        boolean found = false;

        Item updateItem = mScameggDAO.getItemByItemID(itemID);

        if (updateItem.getItemQuantity() > 0){

            allCarts = mScameggDAO.getAllCartsByUserID(userID);

            for (int i = 0; i < allCarts.size(); i++){

                Cart tempCart = allCarts.get(i);

                if (tempCart.getItemID() == itemID){
                    tempCart.setQuantity(tempCart.getQuantity() + 1);
                    mScameggDAO.update(tempCart);
                    found = true;
                }
            }

            if (!found){
                Cart cart = new Cart(userID, itemID, 1);
                mScameggDAO.insert(cart);
            }

            updateItem.setItemQuantity(updateItem.getItemQuantity() - 1);
            mScameggDAO.update(updateItem);

            Toast.makeText(HomeRecyclerViewActivity.this, "Added to Cart!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "This Item is Out of Stock!", Toast.LENGTH_SHORT).show();
        }

    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    private void getPrefs(){
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }
}