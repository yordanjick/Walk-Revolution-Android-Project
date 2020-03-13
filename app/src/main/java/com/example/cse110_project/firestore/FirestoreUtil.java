package com.example.cse110_project.firestore;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.cse110_project.WWRApplication;
import com.example.cse110_project.database.RouteEntry;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nullable;

public class FirestoreUtil {
    public static final String ROUTES_KEY = "routes";
    public static final String USERS_KEY = "users";
    public static final String PROPOSED_ROUTES_KEY = "proposedRoutes";
    public static final String TAG = "firestoreUtil";
    private static String teamId;

    public static FirebaseFirestore DATABASE = null;
    public static CollectionReference ROUTES_REF = null;
    public static CollectionReference USERS_REF = null;
    public static CollectionReference PROPOSED_ROUTES_REF = null;

    public static void initDataBase() {
        DATABASE = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setTimestampsInSnapshotsEnabled(true)
                .build();
        DATABASE.setFirestoreSettings(settings);

        // Initialize Routes collection
        ROUTES_REF = DATABASE.collection(ROUTES_KEY);

        // Initialize Users Collection
        USERS_REF = DATABASE.collection(USERS_KEY);

        // Initialize Proposed Routes Collection
        PROPOSED_ROUTES_REF = DATABASE.collection(PROPOSED_ROUTES_KEY);
    }

    public static void addUserRoute(RouteEntry route) {
        ROUTES_REF.document(route.getUserEmail()).collection(ROUTES_KEY).add(route);
    }

    public static void addProposedRoute(final RouteEntry route, final String proposedDate, final String proposedTime) {
        USERS_REF
                .whereEqualTo("email", route.getUserEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                teamId=document.getString("team_id");
                            }
                        }

                        Map<String, Object> proposedRoute = new HashMap<>();
                        proposedRoute.put("route", route);
                        proposedRoute.put("team_id", teamId);
                        proposedRoute.put("routeDate", proposedDate);
                        proposedRoute.put("routeTime", proposedTime);
                        proposedRoute.put("hostEmail", WWRApplication.getUserAccount().getEmail());
                        proposedRoute.put("status","");
                        proposedRoute.put("route_name",route.getRouteName());
                        PROPOSED_ROUTES_REF.document(route.getRouteName()).set(proposedRoute);
                    }
                });
    }

}
