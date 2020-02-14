package com.example.cse110_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.GoogleFitAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";
    public static final double AVERAGE_STRIDE_LENGTH = 0.413;
    public static final int INCH_PER_FOOT = 12;
    public static final int FEET_PER_MILE = 5280;

    private FitnessService fitnessService;
    private Calendar calendar;

    private TextView stepCounter;
    private TextView walkDistance;
    private long stepCount;

    public int userHeight;
    public boolean heightSet;

    private long startCount;
    private long startTime;
  
    private UserData userObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        stepCounter = findViewById(R.id.steps_walked);
        walkDistance = findViewById(R.id.dist_walked);

        if(fitnessService == null) {
            fitnessService = new GoogleFitAdapter(this);
        }

        this.calendar = Calendar.getInstance();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Button routes_page = (Button)findViewById(R.id.routes_button);
        final Button add_routes = (Button) findViewById(R.id.add_routes_button);
        final Button stop_button = (Button)findViewById(R.id.stop_button);
        final Button updateButton = (Button)findViewById(R.id.update_button);

        userObserver = new UserData(this);

        routes_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // TODO add method call to launchActivity to launch routes page activity
            }
        });

        add_routes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                startCount = stepCount;
                startTime = calendar.getTimeInMillis();
                routes_page.setVisibility(View.INVISIBLE);
                add_routes.setVisibility(View.INVISIBLE);
                updateButton.setVisibility(View.INVISIBLE);
                stop_button.setVisibility(View.VISIBLE);
                // TODO add method call to launchActivity to launch add routes activity
            }
        });
        stop_button.setVisibility(View.INVISIBLE);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long sessionSteps = stepCount - startCount;
                long sessionTime = calendar.getTimeInMillis() - startTime;
                double sessionMiles = convertStepsToMiles(sessionSteps);
                routes_page.setVisibility(View.VISIBLE);
                add_routes.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.VISIBLE);
                stop_button.setVisibility(View.INVISIBLE);

                // TODO: set the above variables to Intent
                /*
                Intent intent = new Intent(AddRouteActivity.class);
                intent.putExtra(Intent.RECORDED_STEPS, sessionSteps);
                intent.putExtra(Intent.RECORDED_TIME, sessionTime);
                intent.putExtra(Intent.RECORDED_MILES, sessionMiles);
                startActivity(intent);
                 */
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStepCount(fitnessService.getStepCount());
            }
        });

        int height = userObserver.getUserHeight();

        // height has not been set if userData is returning -1, default value
        heightSet = height != -1;

        // if height has not been set, show height input prompt

        if(!heightSet) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View heightView = getLayoutInflater().inflate(R.layout.dialog_height, null);
            final EditText heightInput = (EditText) heightView.findViewById(R.id.user_height);
            Button confirmHeight = (Button) heightView.findViewById(R.id.confirm_height);

            mBuilder.setView(heightView);
            final AlertDialog dialog = mBuilder.create();

            confirmHeight.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!heightInput.getText().toString().isEmpty()) {
                        userHeight = Integer.parseInt(heightInput.getText().toString());
                        userObserver.updateHeight(userHeight);
                      
                        heightSet = true;
                        Toast.makeText(MainActivity.this,
                                R.string.success_height_msg, Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    } else {
                        Toast.makeText(MainActivity.this,
                                R.string.error_height_msg, Toast.LENGTH_SHORT).show();
                    }
                    fitnessService.setup();
                }
            });
            dialog.show();

        }
        else {
            fitnessService.setup();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void setStepCount(final long count) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                stepCount = count;
                stepCounter.setText(String.valueOf(stepCount));
                walkDistance.setText(String.valueOf(convertStepsToMiles(stepCount)));
            }
        });
    }

    public double convertStepsToMiles(long numSteps) {
        if(this.heightSet) {
            return (double) (numSteps * this.userHeight * AVERAGE_STRIDE_LENGTH / INCH_PER_FOOT / FEET_PER_MILE);
        } else {
            return 0;
        }
    }

    public void setFitnessService(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }
}
