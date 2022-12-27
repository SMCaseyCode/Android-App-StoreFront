package com.example.scamegg.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.scamegg.R;
import com.example.scamegg.User.User;
import com.example.scamegg.User.UserModel;
import com.example.scamegg.User.UserRecyclerViewAdapter;
import com.example.scamegg.User.UserViewUI;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.RecyclerViewInterface;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;

public class ViewUsersActivity extends AppCompatActivity implements RecyclerViewInterface {

    private ArrayList<UserModel> mUserModels = new ArrayList<>();
    private List<User> mUserList = new ArrayList<>();
    private ScameggDAO mScameggDAO;

    private User mUser;

    private int[] mUserImages = {R.drawable.ic_baseline_user_24, R.drawable.ic_baseline_admin_panel_settings_24};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        RecyclerView recyclerView = findViewById(R.id.viewUsersRecyclerView);

        getDatabase();
        setUserModels();

        Button backButton = findViewById(R.id.backBTN);

        UserRecyclerViewAdapter adapter = new UserRecyclerViewAdapter(this, mUserModels, this);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setUserModels() {

        mUserList = mScameggDAO.getAllUsers();

        for (int i = 0; i < mUserList.size(); i++) {
            mUser = mUserList.get(i);
            int isAdmin = mUser.isAdmin();

            mUserModels.add(new UserModel(mUser.getUserID(), mUser.getUsername(), mUser.getPassword() , mUserImages[isAdmin]));

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
        Intent intent = new Intent(getApplicationContext(), UserViewUI.class);

        User tempUser = mScameggDAO.getUserByUserID(mUserModels.get(position).getUserID());

        intent.putExtra("Username", mUserModels.get(position).getUsername());
        intent.putExtra("Password", mUserModels.get(position).getPassword());
        intent.putExtra("isAdmin", tempUser.isAdmin());
        intent.putExtra("userID", mUserModels.get(position).getUserID());

        startActivity(intent);
    }
}