package com.example.cse110_project.firestore;

import android.util.Log;

import com.example.cse110_project.database.RouteEntry;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class FirestoreUtil {
    public static final String ROUTES_KEY = "routes";
    public static final String TAG = "firestoreUtil";

    public static FirebaseFirestore DATA_BASE = null;
    public static CollectionReference ROUTES_REF = null;

    public static void initDataBase() {
        DATA_BASE = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        DATA_BASE.setFirestoreSettings(settings);

        // Initialize Routes collection
        ROUTES_REF = DATA_BASE.collection(ROUTES_KEY);
    }

    public static void addUserRoute(RouteEntry route) {
        ROUTES_REF.document(route.getUserEmail()).collection(ROUTES_KEY).add(route);
    }
}
