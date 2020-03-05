package com.example.cse110_project.team;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FirestoreToUserDatabaseAdapter implements UserDatabase {
    private static final String TAG = FirestoreToUserDatabaseAdapter.class.getSimpleName();

    private CollectionReference users;
    private String dataKey;
    private String firstNameKey;
    private String lastNameKey;
    private String teamIdKey;

    public FirestoreToUserDatabaseAdapter(FirebaseFirestore firestore, String collectionKey,
                                          String dataKey, String firstNameKey, String lastNameKey, String teamIdKey) {
        this.dataKey = dataKey;
        this.firstNameKey = firstNameKey;
        this.lastNameKey = lastNameKey;
        this.teamIdKey = teamIdKey;

        this.users = firestore.collection(collectionKey);
    }

    public void addUser(final GoogleSignInAccount account) {

        users.document(account.getEmail()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if(document.exists()) {
                        return;
                    } else {
                        Map<String, String> newUserData = new HashMap<>();

                        newUserData.put(firstNameKey, account.getGivenName());
                        newUserData.put(lastNameKey, account.getFamilyName());
                        newUserData.put(teamIdKey, "");


                        users.document(account.getEmail()).set(newUserData);
                    }
                }
            }
        });
    }

    public void makeNewTeam(String userEmail) {
        DocumentReference user = users.document(userEmail);
        Map<String, String> teamIdForUser = new HashMap<>();
        teamIdForUser.put(teamIdKey, userEmail);

        user.set(teamIdForUser);
    }

    public void addToTeam(String receiverEmail, String teamHostEmail) {
        users
                .document(receiverEmail)
                .collection(this.dataKey)
                .document(this.teamIdKey)
                .set(teamHostEmail);
    }
}
