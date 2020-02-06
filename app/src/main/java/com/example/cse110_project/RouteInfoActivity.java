package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.os.Bundle;

import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

public class RouteInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);

        RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
        RouteEntryDAO dao = database.getRouteEntryDAO();
    }
}
