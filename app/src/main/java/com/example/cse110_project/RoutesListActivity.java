package com.example.cse110_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class RoutesListActivity extends AppCompatActivity {
    private static final int MAX_TEXT_LEN = 20, TEXT_EMPTY = 5;
    private static final String UNRECORDED_DATA = "--";
    private static final String ROUTE_FORMAT = "%-" + MAX_TEXT_LEN + "s %-"
                                                + MAX_TEXT_LEN + "s %8s %8s";
    private static final int PADDING = 10, MARGIN = 20;
    public static final String ROUTE_ID = "routeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab_add_route);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(RoutesListActivity.this, CreateRouteActivity.class);
                startActivity(create);
            }
        });

        RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
        RouteEntryDAO dao = database.getRouteEntryDAO();

        LinearLayout listLayout = findViewById(R.id.routes_list_layout);
        String text;
        RouteEntry[] entries = dao.getAllRoutes();
        for(RouteEntry entry: entries) {
            Button routeButton = new Button(this);
            routeButton.setBackgroundColor(Color.LTGRAY);
            LinearLayout.LayoutParams param = new LinearLayout
                    .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(PADDING, PADDING, PADDING, PADDING);
            routeButton.setLayoutParams(param);
            routeButton.setAllCaps(false);
            final int routeId = entry.getId();

            String name = entry.getRouteName();
            String start = entry.getStartPoint();
            if(name.length() > MAX_TEXT_LEN)
                name = name.substring(0, MAX_TEXT_LEN-TEXT_EMPTY) + "...";
            if(start.length() > MAX_TEXT_LEN)
                start = start.substring(0, MAX_TEXT_LEN-TEXT_EMPTY) + "...";
            String steps = UNRECORDED_DATA;
            String distance = UNRECORDED_DATA;
            if(entry.getSteps() >= 0) {
                steps = NumberFormatter.formatStep(entry.getSteps());
            }
            if(entry.getDistance() >= 0) {
                distance = NumberFormatter.formatDistance(entry.getDistance());
            }

            text = String.format(Locale.US, ROUTE_FORMAT, name, start, steps, distance);
            routeButton.setText(text);
            routeButton.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);

            routeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RoutesListActivity.this
                            , RouteInfoActivity.class);
                    intent.putExtra(ROUTE_ID, routeId);
                    startActivity(intent);
                }
            });
            listLayout.addView(routeButton);
        }
    }
}
