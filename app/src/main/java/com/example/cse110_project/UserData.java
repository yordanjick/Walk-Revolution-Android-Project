package com.example.cse110_project;

import android.app.Activity;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

// class that stores the height (potentially other) user data
public class UserData {


    public static final double AVERAGE_STRIDE_LENGTH = 0.413;
    public static final int INCH_PER_FOOT = 12;
    public static final int FEET_PER_MILE = 5280;

    public Context activity;

    // creates a user data object that stores user data (height for now, can be changed)
    public UserData(Context activity)
    {
        this.activity = activity;

    }

    public void updateHeight(int height)
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(HEIGHT_KEY, height);
        editor.apply();
    }

    public int getUserHeight()
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("homepage", MODE_PRIVATE);
        return sharedPreferences.getInt(HEIGHT_KEY, -1);
    }

    public void clearUserData()
    {
        SharedPreferences sharedPreferences = activity.getSharedPreferences("homepage", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    public double convertStepsToMiles(long numSteps) {
        return NumberFormatter.convertStepsToMiles(numSteps, this.getUserHeight());
    }
}
