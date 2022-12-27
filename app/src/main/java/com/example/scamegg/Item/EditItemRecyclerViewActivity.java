package com.example.scamegg.Item;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.scamegg.Admin.EditItemActivity;
import com.example.scamegg.R;

import java.util.Locale;

public class EditItemRecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        int[] mItemImages = {R.drawable.gpunew, R.drawable.cpusvg, R.drawable.mobosvg};

        ImageView itemImage = findViewById(R.id.recyclerItemImage);
        TextView itemName = findViewById(R.id.recyclerItemName);
        TextView itemCategory = findViewById(R.id.recyclerItemCategory);
        TextView itemQuantity = findViewById(R.id.recyclerItemQuantity);
        TextView itemPrice = findViewById(R.id.recyclerItemPrice);
        TextView itemID = findViewById(R.id.recyclerItemID);
        Button editBTN = findViewById(R.id.recyclerEditBTN);
        Button backBTN = findViewById(R.id.backBTN);

        int tempItemQuantity = getIntent().getIntExtra("ItemQuantity", 0);
        String quantString = String.valueOf(tempItemQuantity);

        int tempItemID = getIntent().getIntExtra("ItemID", 0);
        String idString = String.valueOf(tempItemID);

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
        itemQuantity.setText("QUANTITY: " + quantString);
        itemPrice.setText("PRICE: " + getIntent().getStringExtra("ItemPrice"));
        itemID.setText("ITEM ID: " + idString);

        editBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), EditItemUIActivity.class);

                intent.putExtra("itemName", getIntent().getStringExtra("ItemName"));
                intent.putExtra("itemPrice",getIntent().getStringExtra("ItemPrice"));
                intent.putExtra("itemQuantity", quantString);
                intent.putExtra("itemCategory", getIntent().getStringExtra("ItemCategory"));

                startActivity(intent);
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
}