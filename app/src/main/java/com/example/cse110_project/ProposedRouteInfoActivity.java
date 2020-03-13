package com.example.cse110_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.firestore.FirestoreUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ProposedRouteInfoActivity extends AppCompatActivity {
    private String userEmail = WWRApplication.getUserAccount().getEmail();
    private static String teamId;
    public static String accept_name;
    public static String decline_name;
    public static String host_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposed_route_info);

        Intent intent = getIntent();
        // final RouteEntry routeEntry = (RouteEntry) getIntent().getSerializableExtra(ProposedRouteActivity.ROUTE_EXTRA_NAME);
        final String proposedRoute = intent.getStringExtra("proposedRoute");
        String proposedDate = intent.getStringExtra("proposedDate");
        String proposedTime = intent.getStringExtra("proposedTime");
        String emailAddress=intent.getStringExtra("emailAddress");
        TextView routeTitle = (TextView) findViewById(R.id.proposed_route_name);
        routeTitle.setText(proposedRoute);

        TextView proposedRouteDate = (TextView) findViewById(R.id.proposed_date);
        proposedRouteDate.setText(proposedDate);

        TextView proposedRouteTime = (TextView) findViewById(R.id.proposed_time);
        proposedRouteTime.setText(proposedTime);

        Button walk = findViewById(R.id.walk_button);
        walk.setVisibility(View.GONE);

        updateAccDecName(walk);
        final FirebaseFirestore firestore=FirebaseFirestore.getInstance();


        // TODO: LIST OF TEAM MEMBERS WITH THEIR STATUS FOR PROPOSED WALK PUT NAME BACK

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final HashMap<String, String> data = new HashMap<>();

        data.put("receiverName", "");
        data.put("receiverEmail", emailAddress);
        data.put("senderEmail", WWRApplication.getUserAccount().getEmail());

       String user_Email = WWRApplication.getUserAccount().getEmail();

       Log.d("ProposedRouteInfo", "Accept name: " + accept_name);
       Log.d("ProposedRouteInfo", "Host email: " + host_email);
       Log.d("ProposedRouteInfo", "User email: " + user_Email);


        Button accept = findViewById(R.id.accept_button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("proposedRoutes").document(proposedRoute)
                        .update("accept_name",accept_name+" "+WWRApplication.getUserAccount().getGivenName()+",");
                data.put(getString(R.string.message_type), "Accept Proposal");
                database.collection("messages")
                        .add(data);
                finish();
            }
        });

        final Button decline = findViewById(R.id.decline_button);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firestore.collection("proposedRoutes").document(proposedRoute)
                        .update("decline_name",decline_name+" "+ WWRApplication.getUserAccount().getGivenName()+",");
                data.put(getString(R.string.message_type), "Decline Proposal");
                database.collection("messages")
                        .add(data);
                finish();
            }
        });
        



    }

    public void updateAccDecName(final View view){
        final CollectionReference usersRef = FirestoreUtil.USERS_REF;
        final CollectionReference proposedRouteRef = FirestoreUtil.PROPOSED_ROUTES_REF;
        usersRef
                .whereEqualTo("email", userEmail)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot document : task.getResult()){
                                teamId = document.getString("team_id");

                                proposedRouteRef
                                        .whereEqualTo("team_id", teamId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Log.d("ProposedRouteInfo", document.getData().toString());
                                                        // final RouteEntry entry = document.toObject(RouteEntry.class);

                                                        accept_name = document.getString("accept_name");
                                                        decline_name = document.getString("decline_name");
                                                        host_email = document.getString("hostEmail");
                                                        if(host_email.equals(userEmail))
                                                            view.setVisibility(View.VISIBLE);
                                                    }
                                                }else{
                                                    Log.d("docError", "Error getting documents: ", task.getException());
                                                }

                                            }
                                        });
                            }
                        }
                    }
                });
        Log.d("ProposedRouteInfo", "user email: " + userEmail);
        Log.d("ProposedRouteInfo", "team id: " + teamId);
    }

}
