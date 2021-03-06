package com.example.cse110_project;

import android.app.Application;

import com.example.cse110_project.team.FirestoreToUserDatabaseAdapter;
import com.example.cse110_project.team.UserDatabase;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.FirebaseFirestore;

public class WWRApplication extends Application {
    private static UserDatabase userDatabase;
    private static GoogleSignInAccount userAccount;
    private static boolean loggedIn = false;

    static String COLLECTION_KEY = "users";
    static String FIRST_NAME_KEY = "first_name";
    static String LAST_NAME_KEY = "last_name";
    static String TEAM_ID_KEY = "team_id";
    static String EMAIL_KEY = "email";
    static String TOKEN_KEY = "token";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static UserDatabase getUserDatabase() {
        return userDatabase;
    }

    public static UserDatabase setUserDatabase() {
        return userDatabase = new FirestoreToUserDatabaseAdapter(FirebaseFirestore.getInstance(), COLLECTION_KEY, EMAIL_KEY, FIRST_NAME_KEY, LAST_NAME_KEY, TEAM_ID_KEY, TOKEN_KEY);
    }

    public static boolean isLoggedIn() { return loggedIn; }

    public static GoogleSignInAccount getUserAccount() { return userAccount; }

    public static void setUserAccount(GoogleSignInAccount account) {
        loggedIn = true;
        userAccount = account;
    }
}
