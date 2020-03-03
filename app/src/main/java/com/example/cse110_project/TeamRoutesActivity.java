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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;
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
import com.squareup.okhttp.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.annotation.Nullable;

public class TeamRoutesActivity extends AppCompatActivity {
    private static final int MAX_NAME_LEN = 15, MAX_START_LEN = 10, TEXT_EMPTY = 3;
    private static final String UNRECORDED_DATA = "--", ELLIPSE = "...";
    private static final String ROUTE_FORMAT = "%-" + MAX_NAME_LEN + "s %-"
            + MAX_START_LEN + "s %5s %5s";
    private static final int PADDING = 10, MARGIN = 20;
    public static final String ROUTE_EXTRA_NAME = "routeEntry";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_routes);

        final CollectionReference routesRef = FirestoreUtil.ROUTES_REF;

        // Retrieve all team routes
        // TODO retrieve all team email addresses, user name and background color

        ArrayList<String> emails = new ArrayList<>();
        emails.add("test1@ucsd.edu");
        emails.add("test2@ucsd.edu");

        // Retrieve routes associated with team emails
        final LinearLayout routesList = findViewById(R.id.team_routes_list_layout);
        for(String email: emails) {
            routesRef.document(email).collection(FirestoreUtil.ROUTES_KEY)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if(!task.isSuccessful()) {
                        Log.e(this.getClass().getSimpleName(), "Failed");
                        return;
                    }

                    for(QueryDocumentSnapshot doc: task.getResult()) {
                        final RouteEntry entry = doc.toObject(RouteEntry.class);
                        Log.d("retrieve", entry.toString());

                        RelativeLayout relativeLayout = new RelativeLayout(TeamRoutesActivity.this);
                        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                        // Initial text view
                        TextView initialView = new TextView(TeamRoutesActivity.this);
                        initialView.setText(""+Character.toUpperCase(entry.getUserEmail().charAt(0)));
                        initialView.setBackgroundColor(Color.RED);
                        initialView.setTextSize(20);
                        initialView.setWidth(100);
                        initialView.setHeight(126);
                        //initialView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                        initialView.setGravity(Gravity.CENTER);

                        relativeLayout.addView(initialView);

                        Button routeButton = new Button(TeamRoutesActivity.this);
                        routeButton.setBackgroundColor(Color.LTGRAY);
                        routeButton.setAllCaps(false);

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

                        routeButton.setText(String.format(Locale.US, ROUTE_FORMAT, name, start, steps, distance));
                        routeButton.setTypeface(Typeface.MONOSPACE);
                        routeButton.setLetterSpacing(0);
                        routeButton.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);

                        routeButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(TeamRoutesActivity.this
                                        , RouteInfoActivity.class);
                                intent.putExtra(ROUTE_EXTRA_NAME, entry);
                                startActivity(intent);
                            }
                        });
                        relativeLayout.addView(routeButton);

                        params.addRule(RelativeLayout.RIGHT_OF, initialView.getId());
                        params.addRule(RelativeLayout.END_OF, initialView.getId());
                        params.setMargins(100,0,0,0);
                        routeButton.setLayoutParams(params);

                        routesList.addView(relativeLayout);
                    }
                }
            });
        }
    }
}
