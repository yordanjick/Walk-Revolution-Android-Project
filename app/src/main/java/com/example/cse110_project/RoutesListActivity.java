package com.example.cse110_project;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Locale;

public class RoutesListActivity extends AppCompatActivity {
    private static final String ROUTE_FORMAT = "%-10s %-6d %-5.1f %-5d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Get database TODO, change to persistent database
        RouteEntryDatabase database = Room.inMemoryDatabaseBuilder(getApplicationContext(), RouteEntryDatabase.class).allowMainThreadQueries().build();
        RouteEntryDAO dao = database.getRouteEntryDAO();

        // TODO Remove
        /*int numEntry = 20;
        String[] fakeRoutes = new String[numEntry];
        int[] steps = new int[numEntry];
        double[] distance = new double[numEntry];


        for(int i = 0; i < numEntry; i++) {
            fakeRoutes[i] = "R" + i;
            steps[i] = (int)(Math.random()*20000);
            distance[i] = Math.random()*50;
        }*/

        RouteEntry route = new RouteEntry("Run to LA", "San Diego");
        route.setSteps(2000);route.setDistance(10.4);route.setTime(500);
        RouteEntry route1 = new RouteEntry("Run to SD", "Long Beach");
        route1.setSteps(1500);route1.setDistance(25.4);route1.setTime(200);
        RouteEntry route2 = new RouteEntry("Run to LA", "San Diego");
        route2.setSteps(2000);route2.setDistance(100.4);route2.setTime(1200);
        dao.insertRoute(route);dao.insertRoute(route1);dao.insertRoute(route2);

        LinearLayout listLayout = findViewById(R.id.routes_list_layout);
        String text;
        RouteEntry[] entries = dao.getAllRoutes();
        for(RouteEntry entry: entries) {
            Button routeButton = new Button(this);
            routeButton.setBackgroundColor(Color.LTGRAY);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(10, 10, 10, 10);
            routeButton.setLayoutParams(param);
            routeButton.setTypeface(Typeface.MONOSPACE);

            text = String.format(Locale.US, ROUTE_FORMAT, entry.getRouteName(), entry.getSteps(), entry.getDistance(), entry.getTime());
            routeButton.setText(text);
            routeButton.setPadding(20, 20, 20, 20);
            listLayout.addView(routeButton);
        }
    }
}
