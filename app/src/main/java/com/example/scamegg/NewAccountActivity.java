package com.example.scamegg;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.scamegg.User.User;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

public class NewAccountActivity extends AppCompatActivity {

    private Button mSubmitButton;

    private String mUsername;
    private String mPassword;

    private EditText mUsernameField;
    private EditText mPasswordField;

    private User mUser;
    private int mIsAdmin = 0;

    private ScameggDAO mScameggDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        getDatabase();

        mSubmitButton = findViewById(R.id.CreateSubmitButton);

        mSubmitButton = findViewById(R.id.CreateSubmitButton);

        mUsernameField = findViewById(R.id.CreateUsernameEditText);
        mPasswordField = findViewById(R.id.CreatePasswordEditText);

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getValuesFromDisplay();
                addUserToDatabase(user);

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
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

    private User getValuesFromDisplay(){

        mUsername = mUsernameField.getText().toString();
        mPassword = mPasswordField.getText().toString();

        return new User(mUsername, mPassword, mIsAdmin);
    }

    private void addUserToDatabase(User user){
        mScameggDAO.insert(user);
        Toast.makeText(this, "Successfully Created New User", Toast.LENGTH_SHORT).show();
    }
}