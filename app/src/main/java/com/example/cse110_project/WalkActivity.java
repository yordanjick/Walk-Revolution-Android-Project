package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.GoogleFitAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

import static android.app.PendingIntent.getActivity;
import static com.example.cse110_project.MainActivity.FITNESS_SERVICE_KEY;

public class WalkActivity extends AppCompatActivity {
    private Calendar calendar;
    private long startCount;
    private long startTime;
    public FitnessService fitnessService;
    private SharedPreferences mockStepTimeSharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        Intent intent = getIntent();
        if(getIntent().getExtras() != null)
        {
            String routeTitleDB = intent.getStringExtra("routeTitle");
            TextView routeTitle = (TextView) findViewById(R.id.routeTitle);
            routeTitle.setText(routeTitleDB);
        }

        fitnessService = new GoogleFitAdapter(this);

        this.calendar = Calendar.getInstance();

        final Button walkStopButton = (Button)findViewById(R.id.walkStopButton);
        final Button mockStepTimeButton = (Button)findViewById(R.id.go_to_mock_button);

        startCount = fitnessService.getStepCount();
        startTime = calendar.getTimeInMillis();

        mockStepTimeSharedPref = getBaseContext().getSharedPreferences(
                getString(R.string.mock_shared_pref_key), Context.MODE_PRIVATE);

        walkStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserData userObserver = new UserData(WalkActivity.this);
                long stopCount = fitnessService.getStepCount();
                long mockAddSteps = mockStepTimeSharedPref.getLong(getString(R.string.mock_step_key), 0);
                long sessionSteps = stopCount - startCount + mockAddSteps;

                long currentTime = calendar.getTimeInMillis();
                long mockTime = mockStepTimeSharedPref.getLong(getString(R.string.mock_time_key), 0);
                long sessionTime = mockTime == 0 ?
                        currentTime - startTime : mockTime - startTime;

                double sessionMiles = userObserver.convertStepsToMiles(sessionSteps);

                Intent intent = new Intent(WalkActivity.this, RouteInfoActivity.class);
                intent.putExtra("routeSteps", sessionSteps);
                intent.putExtra("routeTime", sessionTime/1000);
                intent.putExtra("routeMiles", sessionMiles);
                Log.d("Intent", "valS = " + sessionSteps);
                Log.d("Intent", "valT = " + sessionTime/1000);
                Log.d("Intent", "valD = " + sessionMiles);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        mockStepTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WalkActivity.this, MockStepTimeActivity.class);
                startActivityForResult(intent, 0);
            }
        });
    }

}
