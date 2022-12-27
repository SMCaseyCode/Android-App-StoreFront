package com.example.scamegg.Orders;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scamegg.LandingPage;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String SHARED_PREFS = "SHARED_PREFS";

    private ScameggDAO mScameggDAO;
    private SharedPreferences mPreferences;

    private ArrayList<OrderItemModel> mOrderItemModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getDatabase();
        getPreferences();
        setItemModels();

        RecyclerView recyclerView = findViewById(R.id.orderRecyclerView);

        OrderRecyclerViewAdapter adapter = new OrderRecyclerViewAdapter(this, mOrderItemModels);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Button backButton = findViewById(R.id.BackBTN);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
                startActivity(intent);
            }
        });


    }

    private void setItemModels(){
        int userID = mPreferences.getInt(USER_ID_KEY, -1);

        mOrderItemModels.clear();
        List<Order> orderList = mScameggDAO.getAllOrdersByUserID(userID);

        for (int i = 0; i < orderList.size(); i++){

            Order order = orderList.get(i);


           mOrderItemModels.add(new OrderItemModel(userID, order.getItemID(), order.getQuantity(), order.getOrderTotal()));
        }
    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    private void getPreferences(){
        mPreferences = getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }
}

