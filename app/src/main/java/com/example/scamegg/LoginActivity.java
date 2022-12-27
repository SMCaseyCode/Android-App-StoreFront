package com.example.scamegg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.scamegg.User.User;
import com.example.scamegg.db.AppDatabase;
import com.example.scamegg.db.ScameggDAO;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String USER_ID_KEY = "USER_ID_KEY";
    private static final String ADMIN_ID = "ADMIN_ID";
    private static final String SHARED_PREFS = "SHARED_PREFS";
    private static final String USERNAME = "USERNAME";
    private static final String PASSWORD = "PASSWORD";
    private EditText mUsernameField;
    private EditText mPasswordField;

    private Button mLoginButton;

    private ScameggDAO mScameggDAO;

    private List<User> mUsers = new ArrayList<>();

    private User mUser;
    private SharedPreferences mPreferences = null;

    private int mUserID = -1;

    private String mUsername;
    private String mPassword;
    private int mIsAdmin = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getDatabase();
        checkForUsers();

        mUsernameField = findViewById(R.id.LoginUsernameEditText);
        mPasswordField = findViewById(R.id.LoginPasswordEditText);

        mLoginButton = findViewById(R.id.LoginSubmitButton);

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getValuesFromDisplay();
                if (checkForUserInDatabase()){
                    if(!validatePassword()){
                        Toast.makeText(LoginActivity.this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
                    }else{
                        addToPrefs(user);

                        Toast.makeText(LoginActivity.this, "Welcome " + user.getUsername() + "!", Toast.LENGTH_SHORT).show();

                        Intent intent = intentFactory(getApplicationContext(), user.getUserID());
                        startActivity(intent);

                    }
                }
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

        if (checkForUserInDatabase()){
            mIsAdmin = mScameggDAO.getUserByUsername(mUsername).isAdmin();
        }


        return new User(mUsername, mPassword, mIsAdmin);
    }

    private boolean checkForUserInDatabase(){
        mUser = mScameggDAO.getUserByUsername(mUsername);
        if (mUser == null){
            Toast.makeText(this, "Invalid Username/Password", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validatePassword(){
        return mUser.getPassword().equals(mPassword);
    }

    private void checkForUsers() {
        //Checks for user
        mUserID = getIntent().getIntExtra(USER_ID_KEY, -1);

        if (mUserID == -1){

            if (mPreferences == null){
                getPrefs();
            }

            mUserID = mPreferences.getInt(USER_ID_KEY, -1);

            if (mUserID != -1){
                return;
            }

            List<User> users = mScameggDAO.getAllUsers();

            if (users.size() <= 0){
                User defaultUser = new User("testuser","123",0);
                User defaultUser2 = new User("admin","admin",1);
                User defaultUser3 = new User("genericUser","115",0);
                User defaultUser4 = new User("genericUser2","117",0);
                User defaultUser5 = new User("NotABot","1010",0);
                User defaultUser6 = new User("New Admin","Password",1);
                User defaultUser7 = new User("Not A Stolen Account","Easy Password",0);
                mScameggDAO.insert(defaultUser,
                        defaultUser2,
                        defaultUser3,
                        defaultUser4,
                        defaultUser5,
                        defaultUser6,
                        defaultUser7 );
            }
        }
    }

    private void addToPrefs(User user){
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = mPreferences.edit();

        editor.putString(USERNAME, user.getUsername());
        editor.putString(PASSWORD, user.getPassword());
        editor.putInt(ADMIN_ID, user.isAdmin());
        editor.putInt(USER_ID_KEY, mScameggDAO.getUserByUsername(user.getUsername()).getUserID());

        editor.apply();
    }

    private void getPrefs(){
        mPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
    }

    public Intent intentFactory(Context context, int userId){
        Intent intent = new Intent(context, LandingPage.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        return intent;
    }

}


