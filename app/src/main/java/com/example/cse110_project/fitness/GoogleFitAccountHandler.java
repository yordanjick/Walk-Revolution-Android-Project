package com.example.cse110_project.fitness;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;

public class GoogleFitAccountHandler {
    private static GoogleSignInAccount account;
    private static FitnessOptions fitnessOptions;
    private static boolean loggedIn = false;

    public static void buildOptions() {
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();
    }

    public static void setAccount(GoogleSignInAccount acc) {
        account = acc;
        loggedIn = true;
    }

    public static boolean isLoggedIn() {
        return loggedIn;
    }

    public static GoogleSignInAccount getAccount() {
        return account;
    }

    public static FitnessOptions getOptions() {
        return fitnessOptions;
    }
}
