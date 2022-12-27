package com.example.scamegg;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

// This is the screen users see if NOT LOGGED IN, offers login or create an account.

public class MainActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String SHARED_PREFS = "SHARED_PREFS";

    private Button mLoginButton;
    private Button mCreateAccountButton;

    private SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getPrefs();

        if (checkForPrefs()){
            Intent intent = new Intent(getApplicationContext(), LandingPage.class);
            startActivity(intent);
        }

        mLoginButton = findViewById(R.id.mainLoginButton);
        mCreateAccountButton = findViewById(R.id.mainCreateAccountButton);


        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        mCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewAccountActivity.class);
                startActivity(intent);
            }
        });

    }

    private boolean checkForPrefs() {
        return mPreferences.contains(USER_ID_KEY);
    }

    private void getPrefs(){
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

}