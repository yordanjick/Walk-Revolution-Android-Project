package com.example.cse110_project.team;

import androidx.annotation.NonNull;

import com.example.cse110_project.WWRApplication;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreToUserDatabaseAdapter implements UserDatabase {
    private CollectionReference users;
    private String emailKey;
    private String firstNameKey;
    private String lastNameKey;
    private String teamIdKey;
    private String tokenKey;

    public FirestoreToUserDatabaseAdapter(FirebaseFirestore firestore, String collectionKey, String emailKey,
                                          String firstNameKey, String lastNameKey, String teamIdKey, String tokenKey) {
        this.emailKey = emailKey;
        this.firstNameKey = firstNameKey;
        this.lastNameKey = lastNameKey;
        this.teamIdKey = teamIdKey;
        this.tokenKey = tokenKey;

        this.users = firestore.collection(collectionKey);
    }

    public HashMap<String, String> newUser(final GoogleSignInAccount account, final String token) {
        HashMap<String, String> newUserData = new HashMap<>();

        newUserData.put(firstNameKey, account.getGivenName());
        newUserData.put(lastNameKey, account.getFamilyName());
        newUserData.put(emailKey, account.getEmail());
        newUserData.put(teamIdKey, account.getEmail());
        newUserData.put(tokenKey, token);

        return newUserData;
    }

    public void updateUser(final GoogleSignInAccount account, final String token) {
        users.document(account.getEmail().replace('.',',')).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        return;
                    } else {
                        Map<String, String> newUserData = newUser(account, token);
                        WWRApplication.setUserData(newUserData);
                        users.document(account.getEmail().replace('.', ',')).set(newUserData);
                    }
                }
            }
        });
    }
    public void addToTeam(String receiverEmail, String teamHostEmail) {
        users
                .document(receiverEmail.replace('.', ','))
                .update(this.teamIdKey, teamHostEmail);
    }
}