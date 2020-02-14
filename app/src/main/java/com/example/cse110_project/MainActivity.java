package com.example.cse110_project;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static String NO_LAST_WALK = "You haven't walked today!"
            , LAST_WALK_FORMAT = "Last Walk: %s %s %s";

    public int userHeight;
    public boolean heightSet;
    private GetMostRecentWalkTask getMostRecentWalkTask;

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
                    || date.get(Calendar.DAY_OF_MONTH) != routeEntry.getDate()
                    || date.get(Calendar.YEAR) != routeEntry.getYear()
                    || routeEntry.getTime() < 0 || routeEntry.getSteps() < 0
                    || routeEntry.getDistance() < 0) {
                text.setText(NO_LAST_WALK);
            } else {
                text.setText(String.format(LAST_WALK_FORMAT
                        , NumberFormatter.formatStep(routeEntry.getSteps())
                        , NumberFormatter.formatDistance(routeEntry.getDistance())
                        , NumberFormatter.formatTime(routeEntry.getTime())));
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Show last intentional walk
        getMostRecentWalkTask = new GetMostRecentWalkTask();
        getMostRecentWalkTask.execute();

        Button routes_page = (Button)findViewById(R.id.routes_button);
        Button add_routes = (Button) findViewById(R.id.add_routes_button);

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
                // Cancel task if still running
                if(getMostRecentWalkTask.getStatus() == AsyncTask.Status.RUNNING)
                    getMostRecentWalkTask.cancel(true);
                // TODO add method call to launchActivity to launch add routes activity
            }
        });
//for test
        if(!heightSet)
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View heightView = getLayoutInflater().inflate(R.layout.dialog_height, null);
            final EditText heightInput = (EditText) heightView.findViewById(R.id.user_height);
            Button confirmHeight = (Button) heightView.findViewById(R.id.confirm_height);

            mBuilder.setView(heightView);
            final AlertDialog dialog = mBuilder.create();

            confirmHeight.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    if(!heightInput.getText().toString().isEmpty())
                    {
                        userHeight = Integer.parseInt(heightInput.getText().toString());
                        heightSet = true;
                        Toast.makeText(MainActivity.this,
                                R.string.success_height_msg, Toast.LENGTH_SHORT).show();
                        dialog.cancel();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this,
                                R.string.error_height_msg, Toast.LENGTH_SHORT).show();
                    }
                }
            });

            dialog.show();

        }
    }

    public void launchActivity(Class activity)
    {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
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
}
