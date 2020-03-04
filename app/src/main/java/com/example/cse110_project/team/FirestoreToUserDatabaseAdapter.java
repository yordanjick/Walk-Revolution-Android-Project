package com.example.cse110_project.team;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
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

    public void addUser(GoogleSignInAccount account) {
        Map<String, Map<String, Map<String, String>>> newUser = new HashMap<>();
        Map<String, Map<String, String>> newUserDataPath= new HashMap<>();
        Map<String, String> newUserData = new HashMap<>();

        newUserData.put(firstNameKey, account.getGivenName());
        newUserData.put(lastNameKey, account.getFamilyName());
        newUserData.put(teamIdKey, "");

        newUserDataPath.put(dataKey, newUserData);

        newUser.put(account.getId(), newUserDataPath);

        users.add(newUser);
    }

    public void makeNewTeam(String userId, String teamName) {
        DocumentReference user = users.document(userId);
        Map<String, String> teamIdForUser = new HashMap<>();
        teamIdForUser.put(teamIdKey, teamName);

        user.set(teamIdForUser);
    }

    public String getTeamId(String userId) {
        return users
                .document(userId)
                .collection(this.dataKey)
                .document(this.teamIdKey)
                .get()
                .getResult()
                .toObject(String.class);
    }

    public void addToTeam(String userId, String teamId) {
        users
                .document(userId)
                .collection(this.dataKey)
                .document(this.teamIdKey)
                .set(teamId);
    }

}
