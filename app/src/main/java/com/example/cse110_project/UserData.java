package com.example.cse110_project;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

public class UserData{

    public static final double AVERAGE_STRIDE_LENGTH = 0.413;
    public static final int INCH_PER_FOOT = 12;
    public static final int FEET_PER_MILE = 5280;

    private int userHeight;

    Activity homepage;

    // creates a user data object that stores user data (height for now, can be changed)
    public UserData(Activity homepage)
    {
        this.homepage = homepage;
        userHeight = -1;

    }

    // data [0] should be height
    public void update(String userData)
    {
        SharedPreferences sharedPreferences = homepage.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.apply();
    }

    public void updateHeight(int height)
    {
        SharedPreferences sharedPreferences = homepage.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("height", height);
        editor.apply();
    }

    public int getUserHeight()
    {
        SharedPreferences sharedPreferences = homepage.getSharedPreferences("homepage", MODE_PRIVATE);
        int height = sharedPreferences.getInt("height", -1);
        return height;
    }
    public void clearUserData()
    {
        SharedPreferences sharedPreferences = homepage.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public double convertStepsToMiles(long numSteps) {
        if(this.getUserHeight() != -1) {
            return (double) (numSteps * this.getUserHeight() * AVERAGE_STRIDE_LENGTH / INCH_PER_FOOT / FEET_PER_MILE);
        } else {
            return 0;
        }
    }
}
