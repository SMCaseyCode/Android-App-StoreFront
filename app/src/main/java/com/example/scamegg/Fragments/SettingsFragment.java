package com.example.scamegg.Fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.scamegg.AboutActivity;
import com.example.scamegg.Admin.AdminActivity;
import com.example.scamegg.MainActivity;
import com.example.scamegg.R;
import com.example.scamegg.User.User;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

public class SettingsFragment extends Fragment {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String ADMIN_ID = "ADMIN_ID";
    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";

    Activity referenceActivity;
    View parentHolder;

    private Button mLogOutButton;
    private Button mAdminButton;
    private Button mDeleteAcctButton;
    private Button mAboutButton;

    SharedPreferences mPreferences;
    ScameggDAO mScameggDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_settings, container, false);

        mLogOutButton = parentHolder.findViewById(R.id.settingsLogoutBTN);
        mAdminButton = parentHolder.findViewById(R.id.AdminBtn);
        mDeleteAcctButton = parentHolder.findViewById(R.id.DeleteAcctBTN);
        mAboutButton = parentHolder.findViewById(R.id.ProfileBTN);

        getDatabase();
        getPrefs();

        mLogOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });

        mAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(referenceActivity.getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            }
        });

        mDeleteAcctButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUserAccount();
            }
        });

        mAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(referenceActivity.getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        if (mPreferences.getInt(ADMIN_ID, 0) == 1){ //Sets Admin button visible if user is admin
            mAdminButton.setVisibility(View.VISIBLE);
        }

        // Inflate the layout for this fragment
        return parentHolder;
    }

    private void getPrefs(){
        mPreferences = referenceActivity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    private void logOutUser() {

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(referenceActivity);

        alertBuilder.setMessage(R.string.logout);

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        clearPrefs();
                        Intent intent = new Intent(referenceActivity.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing because it closes the prompt.
                    }
                });

        alertBuilder.show();

    }

    private void deleteUserAccount(){

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(referenceActivity);

        alertBuilder.setMessage("Are you sure? This WILL DELETE your account.");

        alertBuilder.setPositiveButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        User user = mScameggDAO.getUserByUsername(mPreferences.getString(USERNAME, "USERNAME_ERROR"));
                        clearPrefs();
                        mScameggDAO.delete(user);

                        Intent intent = new Intent(referenceActivity.getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });
        alertBuilder.setNegativeButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //Nothing because it closes the prompt.
                    }
                });

        alertBuilder.show();
    }

    private void clearPrefs(){
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.clear();
        editor.apply();

    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(referenceActivity, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }


}