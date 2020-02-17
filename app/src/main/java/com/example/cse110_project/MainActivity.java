package com.example.cse110_project;

import android.content.Context;
import android.content.Intent;


import android.os.AsyncTask;
import android.os.Bundle;



import android.content.SharedPreferences;

import android.os.Bundle;

import com.example.cse110_project.fitness.FitnessService;
import com.example.cse110_project.fitness.GoogleFitAdapter;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static String NO_LAST_WALK = "You haven't walked today!"
            , LAST_WALK_FORMAT = "Last Walk: %s %s %s";
    public static final String FITNESS_SERVICE_KEY = "FITNESS_SERVICE_KEY";

    public FitnessService fitnessService;
    private Calendar calendar;

    private TextView stepCounter;
    private TextView walkDistance;
    private long stepCount;

    private SharedPreferences mockStepSharedPref;

    public int userHeight;
    public boolean heightSet;
    private GetMostRecentWalkTask getMostRecentWalkTask;
    public static final String TAG="DEBUG";

    private class GetMostRecentWalkTask extends AsyncTask<String, String, RouteEntry> {

        @Override
        protected RouteEntry doInBackground(String... strings) {
            RouteEntryDatabase database = RouteEntryDatabase.getDatabase(getApplicationContext());
            RouteEntryDAO dao = database.getRouteEntryDAO();

            RouteEntry[] routes = dao.getMostRecentUpdatedRoute();
            if(routes.length == 0) return null;
            else return routes[0];
        }

        @Override
        protected void onPostExecute(RouteEntry routeEntry) {
            TextView text = findViewById(R.id.last_run_text);
            Calendar date = Calendar.getInstance();

            // Show no last walk when:
            // 1. No last walk in database
            // 2. Last walk is not today
            // 3. No walk time, step or distance (just created but haven't walk)
            if(routeEntry == null || date.get(Calendar.MONTH) != routeEntry.getMonth()
                    || date.get(Calendar.DAY_OF_MONTH)+1 != routeEntry.getDate()
                    || date.get(Calendar.YEAR) != routeEntry.getYear()
                    || routeEntry.getTime() < 0 || routeEntry.getSteps() < 0
                    || routeEntry.getDistance() < 0) {
                Log.d("Info", routeEntry.getTime() + " " + routeEntry.getSteps() + " " + routeEntry.getDistance());
                Log.d("Info", routeEntry.getTime() + " " + routeEntry.getSteps() + " " + routeEntry.getDistance());
                text.setText(NO_LAST_WALK);
            } else {
                text.setText(String.format(LAST_WALK_FORMAT
                        , NumberFormatter.formatStep(routeEntry.getSteps())
                        , NumberFormatter.formatDistance(routeEntry.getDistance())
                        , NumberFormatter.formatTime(routeEntry.getTime())));
            }
        }
    }

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

        // Show last intentional walk
        getMostRecentWalkTask = new GetMostRecentWalkTask();
        getMostRecentWalkTask.execute();

        final Button routes_page = (Button)findViewById(R.id.routes_button);
        final Button add_routes = (Button) findViewById(R.id.add_routes_button);
        final Button stop_button = (Button)findViewById(R.id.stop_button);
        final Button updateButton = (Button)findViewById(R.id.update_button);
        final Button mockStepTimeButton = (Button)findViewById(R.id.go_to_mock_button);

        userObserver = new UserData(this);
        userObserver.clearUserData();

        mockStepSharedPref = getBaseContext().getSharedPreferences(getString(
                R.string.mock_shared_pref_key), Context.MODE_PRIVATE);

        routes_page.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                // Cancel task if still running
                if(getMostRecentWalkTask.getStatus() == AsyncTask.Status.RUNNING)
                    getMostRecentWalkTask.cancel(true);
                Intent routeList = new Intent(MainActivity.this, RoutesListActivity.class);
                startActivity(routeList);
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
            }
        });
        stop_button.setVisibility(View.INVISIBLE);
        stop_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStepCount(fitnessService.getStepCount() +
                        mockStepSharedPref.getLong(getString(R.string.mock_step_key), 0));
                long sessionSteps = stepCount - startCount;
                long sessionTime = calendar.getTimeInMillis() - startTime;
                double sessionMiles = userObserver.convertStepsToMiles(sessionSteps);
                routes_page.setVisibility(View.VISIBLE);
                add_routes.setVisibility(View.VISIBLE);
                updateButton.setVisibility(View.VISIBLE);
                stop_button.setVisibility(View.INVISIBLE);

                Intent intent = new Intent(MainActivity.this, CreateRouteActivity.class);
                intent.putExtra("routeSteps", sessionSteps);
                intent.putExtra("routeTime", sessionTime/1000);
                intent.putExtra("routeMiles", sessionMiles);
                startActivity(intent);
            }
        });

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setStepCount(fitnessService.getStepCount() +
                        mockStepSharedPref.getLong(getString(R.string.mock_step_key), 0));
            }
        });

        mockStepTimeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MockStepTimeActivity.class);
                startActivityForResult(intent, 0);
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
    protected void onResume() {
        super.onResume();
        getMostRecentWalkTask = new GetMostRecentWalkTask();
        getMostRecentWalkTask.execute();

    }

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        setStepCount(fitnessService.getStepCount() +
                mockStepSharedPref.getLong(getString(R.string.mock_step_key), 0));
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
                walkDistance.setText(String.valueOf(userObserver.convertStepsToMiles(stepCount)));
            }
        });
    }

    public void setFitnessService(FitnessService fitnessService) {
        this.fitnessService = fitnessService;
    }
}
