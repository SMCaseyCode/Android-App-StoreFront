package com.example.scamegg.Admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.scamegg.LandingPage;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

public class AdminActivity extends AppCompatActivity {

    ScameggDAO mScameggDAO;

    private Button addItemBTN;
    private Button removeItemBTN;
    private Button editItemBTN;
    private Button viewUsersBTN;
    private Button backBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        addItemBTN = findViewById(R.id.adminAddItemBTN);
        removeItemBTN = findViewById(R.id.adminRemoveItemBTN);
        editItemBTN = findViewById(R.id.adminEditItemBTN);
        viewUsersBTN = findViewById(R.id.adminViewUsersBTN);
        backBTN = findViewById(R.id.adminBackBTN);

        getDatabase();

        addItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddItemActivity.class);
                startActivity(intent);
            }
        });

        removeItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RemoveItemActivity.class);
                startActivity(intent);
            }
        });

        editItemBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditItemActivity.class);
                startActivity(intent);
            }
        });

        viewUsersBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewUsersActivity.class);
                startActivity(intent);
            }
        });

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LandingPage.class);
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

}