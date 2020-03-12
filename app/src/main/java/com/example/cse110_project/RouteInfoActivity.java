package com.example.cse110_project;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.example.cse110_project.firestore.FirestoreUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

public class RouteInfoActivity extends AppCompatActivity {
    public static final String DATE_FORMAT = "%02d/%02d/%4d", UNRECORDED_DATA = "--";
    public static final int INTENT_ID = 10;
    private RetrieveRouteTask retrieveRouteTask;
    private UpdateRouteTask updateRouteTask;
    private int routeId;
    private RouteEntry route;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private String proposedDate, proposedTime;
    public static String teamId;

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

            showRouteInfo(route);
        }
    }

    private class UpdateRouteTask extends AsyncTask<Intent, String, String> {

        @Override
        protected String doInBackground(Intent... intents) {
            RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
            RouteEntryDAO dao = database.getRouteEntryDAO();

            Intent intent = intents[0];
            Calendar now = Calendar.getInstance();
            int month = now.get(Calendar.MONTH)+1;
            int date = now.get(Calendar.DAY_OF_MONTH);
            int year = now.get(Calendar.YEAR);
            dao.updateRouteWithData(routeId, date, month, year
                    , intent.getLongExtra("routeTime", 0)
                    , intent.getLongExtra("routeSteps", 0)
                    , intent.getDoubleExtra("routeMiles", 0));
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            retrieveRouteTask = new RetrieveRouteTask();
            retrieveRouteTask.execute(routeId);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_route_info);

        // Test whether from local database or from object
        final RouteEntry routeEntry = (RouteEntry) getIntent().getSerializableExtra(TeamRoutesActivity.ROUTE_EXTRA_NAME);
        if (routeEntry != null) {
            showRouteInfo(routeEntry);
        } else {
            // Get intent route id
            routeId = getIntent().getIntExtra(RoutesListActivity.ROUTE_ID, -1);
            Log.d(RouteInfoActivity.class.getSimpleName(), Integer.toString(routeId));


            Button startButton = findViewById(R.id.start_route_button);
            startButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Create new Intent
                    Intent intent = new Intent(RouteInfoActivity.this, WalkActivity.class);
                    intent.putExtra("routeTitle", route.getRouteName());
                    intent.putExtra("routeId", routeId);

                    startActivityForResult(intent, INTENT_ID);
                }
            });

            retrieveRouteTask = new RetrieveRouteTask();
            retrieveRouteTask.execute(routeId);
        }
    }

    private void showRouteInfo(final RouteEntry route) {
        TextView nameText = findViewById(R.id.name_text);
        nameText.setText(route.getRouteName());

        TextView startText = findViewById(R.id.start_text);
        startText.setText(route.getStartPoint());

        TextView stepText = findViewById(R.id.step_text);
        if (route.getSteps() == -1) stepText.setText(UNRECORDED_DATA);
        else stepText.setText(NumberFormatter.formatStep(route.getSteps()));

        TextView distanceText = findViewById(R.id.distance_text);
        if (route.getDistance() == -1.0) distanceText.setText(UNRECORDED_DATA);
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
        Button proposeRouteButton = findViewById(R.id.propose_route_button);
        proposeRouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(RouteInfoActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                proposedDate = (monthOfYear + 1) + "/" + dayOfMonth + "/" + year;
                                final Calendar c = Calendar.getInstance();
                                mHour = c.get(Calendar.HOUR_OF_DAY);
                                mMinute = c.get(Calendar.MINUTE);

                                // Launch Time Picker Dialog
                                TimePickerDialog timePickerDialog = new TimePickerDialog(RouteInfoActivity.this,
                                        new TimePickerDialog.OnTimeSetListener() {
                                            @Override
                                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                                proposedTime = hourOfDay + ":" + minute;
                                                // When Date and Time is set, store to DB then go to proposed Route screen
                                                FirestoreUtil.addProposedRoute(route, proposedDate, proposedTime);
                                                Intent intent = new Intent(RouteInfoActivity.this, ProposedRouteActivity.class);
                                                startActivityForResult(intent, INTENT_ID);
                                            }
                                        } , mHour, mMinute, false);
                                timePickerDialog.show();
                            }
                        }, mYear, mMonth, mDay);



                final CollectionReference usersRef = FirestoreUtil.USERS_REF;

                usersRef
                        .whereEqualTo("email", WWRApplication.getUserAccount().getEmail())
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if(task.isSuccessful()){
                                    for(QueryDocumentSnapshot document : task.getResult()){
                                        teamId=document.getString("team_id");
                                    }
                                }
                            }
                        });
                usersRef
                        .whereEqualTo("team_id", teamId)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("docResultsforPropose", document.getId() + " => " + document.getData());


                                        String firstName = document.getString("first_name");
                                        String lastName = document.getString("last_name");
                                        String email = document.getString("email");
                                        FirebaseFirestore database = FirebaseFirestore.getInstance();
                                        HashMap<String, String> data = new HashMap<>();
                                        data.put(getString(R.string.message_type), getString(R.string.team_walk_invitation));
                                        data.put("receiverName", firstName+" "+lastName);
                                        data.put("receiverEmail", email);
                                        data.put("senderEmail", WWRApplication.getUserAccount().getEmail());
                                        database.collection("messages")
                                                .add(data);

                                    }
                                }
                            }
                        });
                datePickerDialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == INTENT_ID && resultCode == Activity.RESULT_OK && data != null) {
            updateRouteTask = new UpdateRouteTask();
            updateRouteTask.execute(data);
        }
    }
}
