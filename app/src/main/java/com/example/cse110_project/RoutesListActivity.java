package com.example.cse110_project;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.room.Room;

import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.Locale;

public class RoutesListActivity extends AppCompatActivity {
    private static final String ROUTE_FORMAT = "%-10s %-6d %-5.1f %-5d";
    private static final int PADDING = 10, MARGIN = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RouteEntryDatabase database = Room.databaseBuilder(getApplicationContext(), RouteEntryDatabase.class, RouteEntryDatabase.DB_NAME).allowMainThreadQueries().build();
        RouteEntryDAO dao = database.getRouteEntryDAO();

        LinearLayout listLayout = findViewById(R.id.routes_list_layout);
        String text;
        RouteEntry[] entries = dao.getAllRoutes();
        for(RouteEntry entry: entries) {
            Button routeButton = new Button(this);
            routeButton.setBackgroundColor(Color.LTGRAY);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            param.setMargins(PADDING, PADDING, PADDING, PADDING);
            routeButton.setLayoutParams(param);
            routeButton.setTypeface(Typeface.MONOSPACE);

            text = String.format(Locale.US, ROUTE_FORMAT, entry.getRouteName(), entry.getSteps(), entry.getDistance(), entry.getTime());
            routeButton.setText(text);
            routeButton.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);
            listLayout.addView(routeButton);
        }
    }
}
