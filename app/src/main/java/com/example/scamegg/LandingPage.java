package com.example.scamegg;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.scamegg.Fragments.CartFragment;
import com.example.scamegg.Fragments.HomeFragment;
import com.example.scamegg.Fragments.SettingsFragment;
import com.example.scamegg.Fragments.UserFragment;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class LandingPage extends AppCompatActivity {

    private static final String SHARED_PREFS = "SHARED_PREFS";

    ScameggDAO mScameggDAO;

    private int mAdminID = 0;

    private SharedPreferences mPreferences;

    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    UserFragment userFragment = new UserFragment();
    CartFragment cartFragment = new CartFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, homeFragment).commit();
                        return true;
                    case R.id.user:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, userFragment).commit();
                        return true;
                    case R.id.cart:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, cartFragment).commit();
                        return true;
                    case R.id.settings:
                        getSupportFragmentManager().beginTransaction().replace(R.id.flFragment, settingsFragment).commit();
                        return true;
                }

                return false;
            }
        });

        getPrefs();

        getDatabase();

    }

    private void getPrefs(){
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

}