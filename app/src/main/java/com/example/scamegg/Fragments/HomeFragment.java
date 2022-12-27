package com.example.scamegg.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.scamegg.HomeRecyclerViewActivity;
import com.example.scamegg.Item.Item;
import com.example.scamegg.Item.ItemModel;
import com.example.scamegg.Item.ItemRecyclerViewAdapter;
import com.example.scamegg.db.RecyclerViewInterface;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements RecyclerViewInterface {

    private Activity referenceActivity;
    private View parentHolder;

    private ScameggDAO mScameggDAO;

    private ArrayList<ItemModel> mItemModels = new ArrayList<>();

    private List<Item> mItemList = new ArrayList<>();
    private Item mItem;

    private int imageID;

    private int[] mItemImages = {R.drawable.gpunew, R.drawable.cpusvg, R.drawable.mobosvg};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_home, container, false);

        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);

        RecyclerView recyclerView = parentHolder.findViewById(R.id.mRecyclerView);
        mItemModels.clear();

        getDatabase();
        checkForItems();
        setItemModels();

        ItemRecyclerViewAdapter adapter = new ItemRecyclerViewAdapter(referenceActivity, mItemModels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(gridLayoutManager);

        // Inflate the layout for this fragment
        return parentHolder;
    }

    private void checkForItems(){

        // Adds items if there are none.
        if (mScameggDAO.getAllItems().size() <= 0){
            Item item = new Item("Nvidia RTX 4090", "GPU", 5, "$1499.99");
            Item item2 = new Item("Ryzen 9 7950X", "CPU", 8, "$699.99");
            Item item3 = new Item("ASUS Z390 Maximus Hero XI", "Motherboard", 12, "$499.99");
            Item item4 = new Item("AMD Ryzen 9 5900X", "CPU", 7, "$399.99");
            Item item5 = new Item("ASUS ROG STRIX X670E", "Motherboard", 20, "$499.99");
            Item item6 = new Item("Intel Core i5-13600K", "CPU", 9, "$319.99");
            Item item7 = new Item("NVIDIA RTX 4080 16GB", "GPU", 25, "$1199.99");
            Item item8 = new Item("GIGABYTE NVIDIA RTX 3060 12GB", "GPU", 12, "$399.99");
            mScameggDAO.insert(item, item2, item3, item4, item5, item6);
        }

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
        mScameggDAO = Room.databaseBuilder(referenceActivity, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    public void onItemClick(int position) {

        Intent intent = new Intent(referenceActivity, HomeRecyclerViewActivity.class);

        intent.putExtra("ItemName", mItemModels.get(position).getItemName());
        intent.putExtra("ItemCategory", mItemModels.get(position).getItemCategory());
        intent.putExtra("ItemPrice", mItemModels.get(position).getItemPrice());

        startActivity(intent);
    }
}