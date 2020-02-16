package com.example.cse110_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

public class RoutesListActivity extends AppCompatActivity {
    private static final int MAX_NAME_LEN = 15, MAX_START_LEN = 10, TEXT_EMPTY = 3;
    private static final String UNRECORDED_DATA = "--", ELLIPSE = "...";
    private static final String ROUTE_FORMAT = "%-" + MAX_NAME_LEN + "s %-"
                                                + MAX_START_LEN + "s %5s %5s";
    private static final int PADDING = 10, MARGIN = 20;
    public static final String ROUTE_ID = "routeId";
    private RetrieveRoutesTask retrieveRoutesTask = null;

    private class RetrieveRoutesTask extends AsyncTask<String, String, String> {
        private RouteEntry[] entries;

        @Override
        protected String doInBackground(String... strings) {
            RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
            RouteEntryDAO dao = database.getRouteEntryDAO();
            entries = dao.getAllRoutes();
            return "Read " + entries.length + " entries";
        }

        @Override
        protected void onPostExecute(String s) {
            final LinearLayout listLayout = findViewById(R.id.routes_list_layout);
            listLayout.removeAllViews();
            listLayout.refreshDrawableState();
            String text;
            for(RouteEntry entry: entries) {
                Button routeButton = new Button(RoutesListActivity.this);
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
                if(name.length() > MAX_NAME_LEN)
                    name = name.substring(0, MAX_NAME_LEN-TEXT_EMPTY) + ELLIPSE;
                if(start.length() > MAX_START_LEN)
                    start = start.substring(0, MAX_START_LEN-TEXT_EMPTY) + ELLIPSE;
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
                routeButton.setTypeface(Typeface.MONOSPACE);
                routeButton.setLetterSpacing(0);
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
                startActivityForResult(create, (int)(Math.random()*1000));
            }
        });

        updateRoutes();
    }

    @Override
    protected void onPostResume() {
        retrieveRoutesTask = new RetrieveRoutesTask();
        retrieveRoutesTask.execute();
        super.onPostResume();
    }

    private void updateRoutes() {
        retrieveRoutesTask = new RetrieveRoutesTask();
        retrieveRoutesTask.execute();
    }
}
