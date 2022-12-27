package com.example.scamegg.User;

public class UserModel {

    private int mUserID;
    private String mUsername;
    private String mPassword;
    private int mIsAdmin;

    public UserModel(int mUserID, String mUsername, String mPassword, int mIsAdmin) {
        this.mUserID = mUserID;
        this.mUsername = mUsername;
        this.mPassword = mPassword;
        this.mIsAdmin = mIsAdmin;
    }

    public int getUserID() {
        return mUserID;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public int getIsAdmin() {
        return mIsAdmin;
    }
}
