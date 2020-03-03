package com.example.cse110_project.fitness;

import android.content.Context;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.fitness.FitnessOptions;
import com.google.android.gms.fitness.data.DataType;

public class GoogleFitAccountHandler {
    private static GoogleSignInAccount account;
    private static FitnessOptions fitnessOptions;

    public static void login(Context context) {
        GoogleSignIn.getLastSignedInAccount(context);
        fitnessOptions = FitnessOptions.builder()
                .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .addDataType(DataType.AGGREGATE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
                .build();

        if (account == null ) {
            account = GoogleSignIn.getAccountForExtension(context, fitnessOptions);
        }
    }

    public static GoogleSignInAccount getAccount() {
        return account;
    }

    public static FitnessOptions getOptions() {
        return fitnessOptions;
    }
}
