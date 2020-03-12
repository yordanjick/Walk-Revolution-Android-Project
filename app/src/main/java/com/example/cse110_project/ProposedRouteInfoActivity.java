package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;

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

        TextView routeTitle = (TextView) findViewById(R.id.proposed_route_name);
        routeTitle.setText(proposedRoute);

        TextView proposedRouteDate = (TextView) findViewById(R.id.proposed_date);
        proposedRouteDate.setText(proposedDate);

        TextView proposedRouteTime = (TextView) findViewById(R.id.proposed_time);
        proposedRouteTime.setText(proposedTime);

        
        // TODO: LIST OF TEAM MEMBERS WITH THEIR STATUS FOR PROPOSED WALK
        // TODO: Can also had route info but don't know if there is room?

    }
}
