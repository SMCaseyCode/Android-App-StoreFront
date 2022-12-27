package com.example.scamegg.Item;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.scamegg.Admin.EditItemActivity;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.Locale;

public class EditItemUIActivity extends AppCompatActivity {

    ScameggDAO mScameggDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item_uiactivity);

        ImageView itemImage = findViewById(R.id.itemImage);
        EditText itemName = findViewById(R.id.editName);
        EditText itemPrice = findViewById(R.id.editPrice);
        EditText itemQuantity = findViewById(R.id.editQuantity);
        EditText itemCategory = findViewById(R.id.editCategory);

        Button submitBTN = findViewById(R.id.submitBTN);
        Button backBTN = findViewById(R.id.backBTN);

        String passedItemName = getIntent().getStringExtra("itemName");
        String passedItemPrice = getIntent().getStringExtra("itemPrice");
        String passedItemQuantity = getIntent().getStringExtra("itemQuantity");
        String passedItemCategory = getIntent().getStringExtra("itemCategory");

        int[] itemImages = {R.drawable.gpunew, R.drawable.cpusvg, R.drawable.mobosvg};

        itemImage.setImageResource(itemImages[getImageID(passedItemCategory)]);
        itemName.setText(passedItemName);
        itemPrice.setText(passedItemPrice);
        itemQuantity.setText(passedItemQuantity);
        itemCategory.setText(passedItemCategory);

        getDatabase();

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newName = itemName.getText().toString().trim();
                String newPrice = itemPrice.getText().toString().trim();
                String newQuantity = itemQuantity.getText().toString().trim();
                String newCategory = itemCategory.getText().toString().trim();

                switch (newCategory.toLowerCase(Locale.ROOT)){
                    case "gpu":
                        newCategory = "GPU";
                        break;

                    case "cpu":
                        newCategory = "CPU";
                        break;

                    case "motherboard":
                        newCategory = "Motherboard";
                        break;

                }

                Item updateItem = mScameggDAO.getItemByName(passedItemName);

                updateItem.setItemName(newName);
                updateItem.setItemPrice(newPrice);
                updateItem.setItemCategory(newCategory);

                if (isInt(updateItem, newQuantity)){
                    mScameggDAO.update(updateItem);

                    Toast.makeText(EditItemUIActivity.this, "Edit Successful", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(EditItemUIActivity.this, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
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

    private int getImageID(String category){
        switch (category.toLowerCase(Locale.ROOT)){
            case "gpu":
                return 0;

            case "cpu":
                return 1;

            case "motherboard":
                return 2;

            default:
                return 0;
        }
    }

    private boolean isInt(Item item, String newQuantity){

        try {
            item.setItemQuantity(Integer.parseInt(newQuantity));
        } catch (Exception e){
            return false;
        }

        return true;
    }
}