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
import android.widget.LinearLayout;
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

public class ProposedRouteActivity extends AppCompatActivity {
    private static final int MAX_NAME_LEN = 15, MAX_START_LEN = 10, TEXT_EMPTY = 3;
    private static final String UNRECORDED_DATA = "--", ELLIPSE = "...";
    private static final String ROUTE_FORMAT = "%-" + MAX_NAME_LEN + "s %-"
            + MAX_START_LEN + "s %5s %5s";
    private static final int PADDING = 10, MARGIN = 20;
    public static final String ROUTE_EXTRA_NAME = "routeEntry";
    private static String userEmail=WWRApplication.getUserAccount().getEmail();
    private static String teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposed_route);

        final CollectionReference usersRef = FirestoreUtil.USERS_REF;
        final CollectionReference proposedRouteRef = FirestoreUtil.PROPOSED_ROUTES_REF;
        Intent intent=getIntent();
        final String emailAddress=intent.getStringExtra("email_address");
        final LinearLayout proposedRouteList = findViewById(R.id.proposed_route_list_layout);
        usersRef
            .whereEqualTo("email", userEmail)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        teamId=document.getString("team_id");
                    }

                    proposedRouteRef
                        .whereEqualTo("team_id", teamId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    // final RouteEntry entry = document.toObject(RouteEntry.class);
                                    final RouteEntry entry = document.get("route", RouteEntry.class);
                                    RelativeLayout relativeLayout = new RelativeLayout(ProposedRouteActivity.this);
                                    final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                    // Initial text view
                                    TextView initialView = new TextView(ProposedRouteActivity.this);
                                    initialView.setText(""+Character.toUpperCase(entry.getUserEmail().charAt(0)));
                                    initialView.setBackgroundColor(Color.RED);
                                    initialView.setTextSize(20);
                                    initialView.setWidth(100);
                                    initialView.setHeight(126);
                                    //initialView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                    initialView.setGravity(Gravity.CENTER);

                                    relativeLayout.addView(initialView);

                                    Button proposedRouteButton = new Button(ProposedRouteActivity.this);
                                    proposedRouteButton.setBackgroundColor(Color.LTGRAY);
                                    proposedRouteButton.setAllCaps(false);

                                    final String proposedDate = document.getString("routeDate");
                                    final String proposedTime = document.getString("routeTime");
                                    String accepted_name = "";
                                    String declined_name = "";

                                    Map<String, Map<String, Object>> acceptedUsers = (Map<String, Map<String, Object>>) document.get("acceptedUsers");
                                    Map<String, Map<String, Object>> declinedUsers = (Map<String, Map<String, Object>>) document.get("declinedUsers");

                                    if(acceptedUsers != null) {
                                        for(String key : acceptedUsers.keySet()) {
                                            accepted_name += ", " + acceptedUsers.get(key).get("first_name");
                                        }
                                    }

                                    if(declinedUsers != null) {
                                        for(String key : declinedUsers.keySet()) {
                                            declined_name += ", " + declinedUsers.get(key).get("first_name");
                                        }
                                    }

                                    TextView accept=findViewById(R.id.proposed_accept);
                                    if(accepted_name != "") {
                                        accept.setText(accepted_name + " accepted the proposed route");
                                    } else {
                                        accept.setText("No one accepted the proposed route");
                                    }
                                    TextView decline=findViewById(R.id.proposed_decline);

                                    if(declined_name != "") {
                                        decline.setText(declined_name+" declined the proposed route");
                                    }
                                    String name = entry.getRouteName();
                                    String start = entry.getStartPoint();
                                    if (name.length() > MAX_NAME_LEN)
                                        name = name.substring(0, MAX_NAME_LEN - TEXT_EMPTY) + ELLIPSE;
                                    if (start.length() > MAX_START_LEN)
                                        start = start.substring(0, MAX_START_LEN - TEXT_EMPTY) + ELLIPSE;
                                    String steps = UNRECORDED_DATA;
                                    String distance = UNRECORDED_DATA;
                                    if (entry.getSteps() >= 0) {
                                        steps = NumberFormatter.formatStep(entry.getSteps());
                                    }
                                    if (entry.getDistance() >= 0) {
                                        distance = NumberFormatter.formatDistance(entry.getDistance());
                                    }

                                    proposedRouteButton.setText(String.format(Locale.US, ROUTE_FORMAT, name, start, steps, distance));
                                    proposedRouteButton.setTypeface(Typeface.MONOSPACE);
                                    proposedRouteButton.setLetterSpacing(0);
                                    proposedRouteButton.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);

                                    proposedRouteButton.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            // TODO: Intent to accept/decline? With team status

                                            Intent nintent = new Intent(ProposedRouteActivity.this
                                                    , ProposedRouteInfoActivity.class);
                                            nintent.putExtra("proposedRoute", entry.getRouteName());
                                            nintent.putExtra("proposedDate", proposedDate);
                                            nintent.putExtra("proposedTime", proposedTime);
                                            nintent.putExtra("emailAddress",emailAddress);
                                            startActivity(nintent);
                                        }
                                    });
                                    relativeLayout.addView(proposedRouteButton);

                                    params.addRule(RelativeLayout.RIGHT_OF, initialView.getId());
                                    params.addRule(RelativeLayout.END_OF, initialView.getId());
                                    params.setMargins(100,0,0,0);
                                    proposedRouteButton.setLayoutParams(params);

                                    proposedRouteList.addView(relativeLayout);
                                }
                            } else {
                                Log.d("docError", "Error getting documents: ", task.getException());
                            }
                            }
                        });
                }
                }
            });
    }


}