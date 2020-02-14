package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walk);
        Intent intent = getIntent();
        if(getIntent().getExtras() != null)
        {
            String routeTitleDB = intent.getStringExtra("ROUTE_TITLE");
            TextView routeTitle = (TextView) findViewById(R.id.routeTitle);
            routeTitle.setText(routeTitleDB);
        }

        fitnessService = new GoogleFitAdapter(this);

        this.calendar = Calendar.getInstance();

        final Button walkStopButton = (Button)findViewById(R.id.walkStopButton);
        startCount = fitnessService.getStepCount();
        startTime = calendar.getTimeInMillis();

        walkStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity mainActivity = new MainActivity();
                long stopCount = fitnessService.getStepCount();
                long sessionSteps = stopCount - startCount;
                long sessionTime = calendar.getTimeInMillis() - startTime;
                double sessionMiles = mainActivity.convertStepsToMiles(sessionSteps);

                    /*
                Intent intent = new Intent(AddRouteActivity.class);
                intent.putExtra(Intent.RECORDED_STEPS, sessionSteps);
                intent.putExtra(Intent.RECORDED_TIME, sessionTime);
                intent.putExtra(Intent.RECORDED_MILES, sessionMiles);
                startActivity(intent);
                 */
            }
        });
    }

}
