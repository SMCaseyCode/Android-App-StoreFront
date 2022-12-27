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
import android.widget.TextView;

import com.example.scamegg.MainActivity;
import com.example.scamegg.Orders.OrderActivity;
import com.example.scamegg.R;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

public class UserFragment extends Fragment {

    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String USERNAME = "USERNAME";

    Activity referenceActivity;
    View parentHolder;

    ScameggDAO mScameggDAO;

    private SharedPreferences mPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        referenceActivity = getActivity();
        parentHolder = inflater.inflate(R.layout.fragment_user, container, false);

        TextView titleText = parentHolder.findViewById(R.id.userFragmentTextView);
        TextView cardUsername = parentHolder.findViewById(R.id.username);

        Button ordersBTN = parentHolder.findViewById(R.id.ordersButton);
        Button logoutBTN = parentHolder.findViewById(R.id.logoutButton);

        getDatabase();
        getPrefs();

        titleText.setText("Hello, " + mPreferences.getString(USERNAME,"NAME_ERROR"));
        cardUsername.setText("Name: " + mPreferences.getString(USERNAME, "NAME_ERROR"));

        ordersBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(referenceActivity, OrderActivity.class);
                startActivity(intent);
            }
        });

        logoutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOutUser();
            }
        });

        // Inflate the layout for this fragment
        return parentHolder;
    }

    private void getDatabase(){
        mScameggDAO = Room.databaseBuilder(referenceActivity, AppDatabase.class, AppDatabase.DB_NAME)
                .allowMainThreadQueries()
                .build()
                .getScameggDAO();
    }

    private void getPrefs(){
        mPreferences = referenceActivity.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
    }

    private void clearPrefs(){
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.clear();
        editor.apply();
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
}