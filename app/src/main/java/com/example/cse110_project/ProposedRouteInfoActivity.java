package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ProposedRouteInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proposed_route_info);

        Intent intent = getIntent();
        // final RouteEntry routeEntry = (RouteEntry) getIntent().getSerializableExtra(ProposedRouteActivity.ROUTE_EXTRA_NAME);
        String proposedRoute = intent.getStringExtra("proposedRoute");
        String proposedDate = intent.getStringExtra("proposedDate");
        String proposedTime = intent.getStringExtra("proposedTime");
        String emailAddress=intent.getStringExtra("emailAddress");
        TextView routeTitle = (TextView) findViewById(R.id.proposed_route_name);
        routeTitle.setText(proposedRoute);

        TextView proposedRouteDate = (TextView) findViewById(R.id.proposed_date);
        proposedRouteDate.setText(proposedDate);

        TextView proposedRouteTime = (TextView) findViewById(R.id.proposed_time);
        proposedRouteTime.setText(proposedTime);

        final FirebaseFirestore database = FirebaseFirestore.getInstance();
        final HashMap<String, String> data = new HashMap<>();

        data.put("receiverName", "");
        data.put("receiverEmail", emailAddress);
        data.put("senderEmail", WWRApplication.getUserAccount().getEmail());



        Button accept = findViewById(R.id.accept_button);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put(getString(R.string.message_type), "Accept Proposal");
                database.collection("messages")
                        .add(data);
                finish();
            }
        });

        Button decline = findViewById(R.id.decline_button);
        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.put(getString(R.string.message_type), "Decline Proposal");
                database.collection("messages")
                        .add(data);
                finish();
            }
        });
        
        // TODO: LIST OF TEAM MEMBERS WITH THEIR STATUS FOR PROPOSED WALK
        // TODO: Can also had route info but don't know if there is room?

    }
}
