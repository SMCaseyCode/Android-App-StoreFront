package com.example.scamegg.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scamegg.Item.EditItemRecyclerViewAdapter;
import com.example.scamegg.Item.Item;
import com.example.scamegg.Item.ItemModel;
import com.example.scamegg.db.RecyclerViewInterface;
import com.example.scamegg.R;
import com.example.scamegg.Item.EditItemRecyclerViewActivity;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class EditItemActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<ItemModel> mItemModels = new ArrayList<>();
    private List<Item> mItemList = new ArrayList<>();
    private ScameggDAO mScameggDAO;

    private Item mItem;
    private int imageID;

    private int[] mItemImages = {R.drawable.gpunew, R.drawable.cpusvg, R.drawable.mobosvg};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);

        RecyclerView recyclerView = findViewById(R.id.editItemsRecyclerView);

        getDatabase();
        setItemModels();

        Button backBTN = findViewById(R.id.backBTN);

        EditItemRecyclerViewAdapter adapter = new EditItemRecyclerViewAdapter(this, mItemModels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setItemModels(){
        mItemList = mScameggDAO.getAllItems();

        for (int i = 0; i < mItemList.size(); i++){
            mItem = mItemList.get(i);

            switch (mItem.getItemCategory().toLowerCase(Locale.ROOT)){
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

            mItemModels.add(new ItemModel(mItem.getItemName(), mItem.getItemCategory(), mItem.getItemPrice(), mItemImages[imageID]));
        }
    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    @Override
    public void onItemClick(int position) {
        Item tempItem = mScameggDAO.getItemByName(mItemModels.get(position).getItemName());

        int itemQuantity = tempItem.getItemQuantity();
        int itemID = tempItem.getItemID();

        Intent intent = new Intent(getApplicationContext(), EditItemRecyclerViewActivity.class);

        intent.putExtra("ItemName", mItemModels.get(position).getItemName());
        intent.putExtra("ItemCategory", mItemModels.get(position).getItemCategory());
        intent.putExtra("ItemPrice", mItemModels.get(position).getItemPrice());
        intent.putExtra("ItemQuantity", itemQuantity);
        intent.putExtra("ItemID", itemID);

        startActivity(intent);
    }
}