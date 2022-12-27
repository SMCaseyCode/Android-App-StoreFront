package com.example.scamegg.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.scamegg.Item.Item;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.Objects;

public class AddItemActivity extends AppCompatActivity {

    private EditText mAddItemName;
    private EditText mAddCategory;
    private EditText mAddQuantity;
    private EditText mAddPrice;

    private ScameggDAO mScameggDAO;

    String[] mValidEntries = {"GPU", "CPU", "Motherboard"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        mAddItemName = findViewById(R.id.addItemName);
        mAddCategory = findViewById(R.id.addItemCategory);
        mAddQuantity = findViewById(R.id.addItemQuantity);
        mAddPrice = findViewById(R.id.addItemPrice);

        Button mAddItem = findViewById(R.id.addItemBTN);
        Button mBackButton = findViewById(R.id.addBackBTN);

        getDatabase();

        mAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item item = getValuesFromDisplay();

                if (checkCategoryValidity(item)){

                    if (!checkForExistingInventory(item)){
                        addItemToDatabase(item);
                    }

                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                }

            }

        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });


    }

    private Item getValuesFromDisplay() {

        String mItemName = mAddItemName.getText().toString();
        String mItemCategory;
        int mItemQuantity = Integer.parseInt(mAddQuantity.getText().toString());
        String mItemPrice = mAddPrice.getText().toString();

        switch (mAddCategory.getText().toString().trim().toLowerCase()){
            case "gpu":
                mItemCategory = "GPU";
                break;

            case "cpu":
                mItemCategory = "CPU";
                break;

            case "motherboard":
                mItemCategory = "Motherboard";
                break;

            default:
                mItemCategory = "INVALID ENTRY";
        }

        return new Item(mItemName, mItemCategory, mItemQuantity,"$" + mItemPrice);
    }

    private boolean checkCategoryValidity(Item item){

        for (String mValidEntry : mValidEntries) {
            if (Objects.equals(item.getItemCategory(), mValidEntry)) {
                return true;
            }
        }

        Toast.makeText(AddItemActivity.this, "Invalid Category Entry", Toast.LENGTH_SHORT).show();

        return false;
    }

    private void addItemToDatabase(Item item){
        mScameggDAO.insert(item);
    }

    private boolean checkForExistingInventory(Item item){

        Item tempItem = mScameggDAO.getItemByName(item.getItemName());

        if (tempItem != null){
            if (Objects.equals(tempItem.getItemName(), item.getItemName())){
                tempItem.setItemQuantity(item.getItemQuantity() + tempItem.getItemQuantity());
                mScameggDAO.update(tempItem);
                Toast.makeText(AddItemActivity.this, "Added Quantity to " + item.getItemName(), Toast.LENGTH_SHORT).show();
                return true;
            }
        }

        return false;
    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    private boolean isInt(Item item, String newQuantity){ //TODO: Make adding items exception proof

        try {
            item.setItemQuantity(Integer.parseInt(newQuantity));
        } catch (Exception e){
            return false;
        }

        return true;
    }

}