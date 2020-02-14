package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

import java.util.Locale;

public class RouteInfoActivity extends AppCompatActivity {
    public static final String DATE_FORMAT = "%02d/%02d/%4d", UNRECORDED_DATA = "--";
    private RetrieveRouteTask retrieveRouteTask;
    private RouteEntry route;

    private class RetrieveRouteTask extends AsyncTask<Integer, String, RouteEntry> {

        @Override
        protected RouteEntry doInBackground(Integer... routeIds) {
            RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
            RouteEntryDAO dao = database.getRouteEntryDAO();

            route = null;
            int routeId = routeIds[0];
            if(routeId != -1) {
                route = dao.getRoute(routeId);
            }

            return route;
        }

        @Override
        protected void onPostExecute(RouteEntry route) {
            // Error retrieving route (id wrong)
            if(route == null) {
                Log.e(RouteInfoActivity.class.getName(), "Error retrieving route");
                finish();
            }

            TextView nameText = findViewById(R.id.name_text);
            nameText.setText(route.getRouteName());

            TextView startText = findViewById(R.id.start_text);
            startText.setText(route.getStartPoint());

            TextView stepText = findViewById(R.id.step_text);
            if (route.getSteps() == -1) stepText.setText(UNRECORDED_DATA);
            else stepText.setText(NumberFormatter.formatStep(route.getSteps()));

            TextView distanceText = findViewById(R.id.distance_text);
            if (route.getDistance() < 0) distanceText.setText(UNRECORDED_DATA);
            else distanceText.setText(NumberFormatter.formatDistance(route.getDistance()));

            TextView dateText = findViewById(R.id.date_text);
            if (route.getMonth() == -1 || route.getDate() == -1 || route.getYear() == -1)
                dateText.setText(UNRECORDED_DATA);
            else
                dateText.setText(String.format(Locale.US, DATE_FORMAT, route.getMonth()
                        , route.getDate(), route.getYear()));
            TextView timeText = findViewById(R.id.time_text);
            if (route.getTime() == -1)
                timeText.setText(UNRECORDED_DATA);
            else
                timeText.setText(NumberFormatter.formatTime(route.getTime()));

            TextView runText = findViewById(R.id.run_text);
            runText.setText(route.getRun() == -1 ? "" : RouteEntry.RUN_VAL[route.getRun()]);
            TextView surfaceText = findViewById(R.id.surface_text);
            surfaceText.setText(route.getTerrain() == -1 ? "" : RouteEntry.TERRAIN_VAL[route.getTerrain()]);
            TextView roadText = findViewById(R.id.road_text);
            roadText.setText(route.getRoadType() == -1 ? "" : RouteEntry.ROAD_TYPE_VAL[route.getRoadType()]);
            TextView conditionText = findViewById(R.id.condition_text);
            conditionText.setText(route.getRoadCondition() == -1 ? "" : RouteEntry.ROAD_CONDITION_VAL[route.getRoadCondition()]);
            TextView difficultyText = findViewById(R.id.difficulty_text);
            difficultyText.setText(route.getLevel() == -1 ? "" : RouteEntry.LEVEL_VAL[route.getLevel()]);
            TextView favoriteText = findViewById(R.id.favorite_text);
            favoriteText.setText(route.getFavorite() == 0 ? "Yes": "No");
            TextView noteText = findViewById(R.id.note_text);
            noteText.setText(route.getNote());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);

        // Get intent route id
        final int routeId = getIntent().getIntExtra(RoutesListActivity.ROUTE_ID, -1);
        Log.d(RouteInfoActivity.class.getSimpleName(),Integer.toString(routeId));

        Button startButton = findViewById(R.id.start_route_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cancel job if it isn't cancelled
                if(retrieveRouteTask.getStatus() == AsyncTask.Status.RUNNING)
                    retrieveRouteTask.cancel(true);

                // Create new Intent
                Intent intent = new Intent(RouteInfoActivity.this, WalkActivity.class);
                intent.putExtra("routeTitle", route.getRouteName());

                startActivity(intent);
            }
        });

        retrieveRouteTask = new RetrieveRouteTask();
        retrieveRouteTask.execute(routeId);
    }
}
