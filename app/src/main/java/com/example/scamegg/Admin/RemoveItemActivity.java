package com.example.scamegg.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scamegg.Item.Item;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

public class RemoveItemActivity extends AppCompatActivity {

    EditText mItemNameField;
    EditText mItemQuantityField;

    Button mSubmitButton;
    Button mBackButton;

    private ScameggDAO mScameggDAO;
    private Item mItem;
    private String mItemName;
    private int mItemQuantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_item);

        mItemNameField = findViewById(R.id.removeItemName);
        mItemQuantityField = findViewById(R.id.removeItemQuantity);

        mSubmitButton = findViewById(R.id.removeItemSubmitBTN);
        mBackButton = findViewById(R.id.removeBackBTN);

        getDatabase();

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setValuesFromDisplay();
                mItem = mScameggDAO.getItemByName(mItemName);
                removeItemFromDatabase(mItem, mItemQuantity);

                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);

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

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    private void setValuesFromDisplay() {

        mItemName = mItemNameField.getText().toString();
        mItemQuantity = Integer.parseInt(mItemQuantityField.getText().toString());

    }

    private void removeItemFromDatabase(Item item, int itemQuantity){

        if (item.getItemQuantity() <= itemQuantity){
            mScameggDAO.delete(item);
        } else {
            item.setItemQuantity(item.getItemQuantity() - itemQuantity);
            mScameggDAO.update(item);
        }
    }
}