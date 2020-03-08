package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.cse110_project.database.RouteEntry;
import com.example.cse110_project.database.RouteEntryDAO;
import com.example.cse110_project.database.RouteEntryDatabase;
import com.example.cse110_project.firestore.FirestoreUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

import java.util.Calendar;

public class CreateRouteActivity extends AppCompatActivity {
    private RouteEntryDatabase database;
    private RouteEntryDAO dao;
    private InsertRouteTask insertRouteTask;

    private class InsertRouteTask extends AsyncTask<RouteEntry, String, String> {

        @Override
        protected String doInBackground(RouteEntry... routeEntries) {
            database = RouteEntryDatabase.getDatabase(getApplicationContext());
            dao = database.getRouteEntryDAO();
            dao.insertRoute(routeEntries[0]);
            Log.d(MainActivity.TAG,"create database in CreateRouteActivity");
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            setResult(RESULT_OK);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(MainActivity.TAG,"create routes' properties");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_route);

        // Get google fit data
        Intent intent = getIntent();
        final long steps = intent.getLongExtra("routeSteps", -1);
        final long time = intent.getLongExtra("routeTime", -1);
        final double distance = intent.getDoubleExtra("routeMiles", -1);

        final EditText name = findViewById(R.id.name_field);
        final EditText start = findViewById(R.id.start_field);

        final RadioGroup runtype = findViewById(R.id.run_type);
        final RadioButton runtype1 = findViewById(R.id.loop_button);
        RadioButton runtype2 = findViewById(R.id.out_and_back_button);

        final RadioGroup f_h = findViewById(R.id.flat_hilly);
        final RadioButton f_h1 = findViewById(R.id.flat_button);
        RadioButton f_h2 = findViewById(R.id.hilly_button);

        final RadioGroup routetype = findViewById(R.id.route_type);
        final RadioButton routetype1 = findViewById(R.id.streets_button);
        RadioButton routetype2 = findViewById(R.id.trail_button);

        final RadioGroup surfacetype = findViewById(R.id.surface_type);
        final RadioButton surfacetype1 = findViewById(R.id.even_button);
        RadioButton surfacetype2 = findViewById(R.id.uneven_button);

        final RadioGroup difficulity = findViewById(R.id.difficulty);
        final RadioButton difficulity1 = findViewById(R.id.easy_button);
        final RadioButton difficulity2 = findViewById(R.id.moderate_button);
        RadioButton difficulity3 = findViewById(R.id.hard_button);

        final CheckBox favorite=findViewById(R.id.favorite);
        final MultiAutoCompleteTextView note = findViewById(R.id.note_field);

        Button save = findViewById(R.id.save_buttton);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("") || start.getText().toString().equals("")
                       ) {
                    Toast.makeText(CreateRouteActivity.this, "Invalid Input", Toast.LENGTH_LONG).show();
                } else {
                    RouteEntry routeEntry = new RouteEntry(name.getText().toString(), start.getText().toString());

                    routeEntry.setSteps(steps);
                    routeEntry.setDistance(distance);
                    routeEntry.setTime(time);
                    routeEntry.setRun(runtype.getCheckedRadioButtonId() == runtype1.getId() ? 0 : 1);
                    routeEntry.setTerrain(f_h.getCheckedRadioButtonId() == f_h1.getId() ? 0 : 1);
                    routeEntry.setRoadType(routetype.getCheckedRadioButtonId() == routetype1.getId() ? 0 : 1);
                    routeEntry.setRoadCondition(surfacetype.getCheckedRadioButtonId() == surfacetype1.getId() ? 0 : 1);
                    if (difficulity.getCheckedRadioButtonId() == difficulity1.getId()) {
                        routeEntry.setLevel(0);
                    } else if (difficulity.getCheckedRadioButtonId() == difficulity2.getId()) {
                        routeEntry.setLevel(1);
                    } else {
                        routeEntry.setLevel(2);
                    }
                    routeEntry.setFavorite(favorite.isChecked()? 0:-1);
                    routeEntry.setNote(note.getText().toString());

                    Calendar now = Calendar.getInstance();
                    int month = now.get(Calendar.MONTH)+1;
                    int date = now.get(Calendar.DAY_OF_MONTH);
                    int year = now.get(Calendar.YEAR);
                    routeEntry.setDate(date);
                    routeEntry.setMonth(month);
                    routeEntry.setYear(year);

                    // Set username and email field
                    GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                    if (acct != null) {
                        // TODO get email account from user, now is null
                        String personId = acct.getId();
                        String personGivenName = acct.getGivenName();
                        String personEmail = acct.getEmail();
                        Log.d("retrieve", personGivenName + personEmail + personId);
                        routeEntry.setUserEmail(personEmail);
                        routeEntry.setUserName(personGivenName);
                    } else {
                        routeEntry.setUserEmail("test1@ucsd.edu");
                        routeEntry.setUserName("John");
                    }

                    // No double click while insert is running
                    if(insertRouteTask != null && insertRouteTask.getStatus()
                            == AsyncTask.Status.RUNNING) return;
                    insertRouteTask = new InsertRouteTask();
                    insertRouteTask.execute(routeEntry);

                    FirestoreUtil.addUserRoute(routeEntry);
                }
            }
        });


    }
}
