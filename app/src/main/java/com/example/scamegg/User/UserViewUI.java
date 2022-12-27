package com.example.scamegg.User;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.scamegg.Admin.ViewUsersActivity;
import com.example.scamegg.R;

public class UserViewUI extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_ui);

        int[] userImages = {R.drawable.ic_baseline_user_24, R.drawable.ic_baseline_admin_panel_settings_24};

        ImageView userImage = findViewById(R.id.userImage);
        TextView pageTitle = findViewById(R.id.usernameTitle);
        TextView username = findViewById(R.id.username);
        TextView userID = findViewById(R.id.userID);
        TextView password = findViewById(R.id.password);
        TextView adminStatus = findViewById(R.id.adminStatus);

        Button backBTN = findViewById(R.id.aboutBackBTN);

        int adminID = getIntent().getIntExtra("isAdmin", 0);
        int intUserID = getIntent().getIntExtra("userID", 0);

        String passedUsername = getIntent().getStringExtra("Username");
        String passedUserID = String.valueOf(intUserID);
        String passedPassword = getIntent().getStringExtra("Password");
        String adminStatusConverted;

        if (adminID == 1){
            adminStatusConverted = "True";
        } else {
            adminStatusConverted = "False";
        }

        userImage.setImageResource(userImages[adminID]);
        pageTitle.setText(passedUsername);
        userID.setText("USER ID: " + passedUserID);
        username.setText("USERNAME: " + passedUsername);
        password.setText("PASSWORD: " + passedPassword);
        adminStatus.setText("ADMIN: " + adminStatusConverted);

        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ViewUsersActivity.class);
                startActivity(intent);
            }
        });

    }
}