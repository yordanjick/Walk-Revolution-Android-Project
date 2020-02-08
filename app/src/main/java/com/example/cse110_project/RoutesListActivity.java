package com.example.cse110_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

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
    private static final String ROUTE_FORMAT = "%10s";
    private static final int PADDING = 10, MARGIN = 20;
    public static final String ROUTE_ID = "routeId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routes_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
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
            final int routeId = entry.getId();

            text = String.format(Locale.US, ROUTE_FORMAT, entry.getRouteName());
            routeButton.setText(text);
            routeButton.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);

            routeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(RoutesListActivity.this, RouteInfoActivity.class);
                    intent.putExtra(ROUTE_ID, routeId);
                    startActivity(intent);
                    Log.d("stdout","Click button with id:" + routeId);
                }
            });
            listLayout.addView(routeButton);
        }
    }
}
