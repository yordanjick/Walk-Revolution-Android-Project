package com.example.cse110_project;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

// class that stores the height (potentially other) user data
public class UserData{

    public Activity activity;

    // creates a user data object that stores user data (height for now, can be changed)
    public UserData(Activity activity)
    {
        this.activity = activity;

    }

    public void updateHeight(int height)
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("height", height);
        editor.apply();
    }

    public int getUserHeight()
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("homepage", MODE_PRIVATE);
        int height = sharedPreferences.getInt("height", -1);
        return height;
    }
    public void clearUserData()
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
