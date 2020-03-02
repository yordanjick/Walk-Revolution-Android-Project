package com.example.cse110_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.UserEntry;
import com.example.cse110_project.firestore.FirestoreUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.firestore.core.View;
import com.squareup.okhttp.Route;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class TeamMembersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_routes);

        final TextView userList = findViewById(R.id.member_list);
        final CollectionReference routesRef = FirestoreUtil.USERS_REF;

        // Retrieve all team routes
        // TODO retrieve all team email addresses

        ArrayList<String> emails = new ArrayList<>();
        emails.add("test1@ucsd.edu");
        emails.add("test2@ucsd.edu");

        // Retrieve routes associated with team emails
        final StringBuilder builder = new StringBuilder();
        for(String email: emails) {
            routesRef.document(email).collection(FirestoreUtil.USERS_KEY)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(!task.isSuccessful()) {
                                Log.e(this.getClass().getSimpleName(), "Failed");
                                return;
                            }

                            for(QueryDocumentSnapshot doc: task.getResult()) {
                                UserEntry userEntry = doc.toObject(UserEntry.class);
                                // Log.d("retrieve", routeEntry.toString());
                                builder.append(userEntry.getUserEmail());
                                builder.append("-");
                                builder.append(userEntry.getUserName());
                                builder.append("-");
                                builder.append(userEntry.getTeamID());
                                builder.append("\n");

                            }

                            userList.append(builder.toString());
                        }
                    });
        }
    }
}
