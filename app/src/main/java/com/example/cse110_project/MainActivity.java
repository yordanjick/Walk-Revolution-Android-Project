package com.example.cse110_project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public boolean heightSet;

    private UserData userObserver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userObserver = new UserData(this);



        Button routes_page = (Button)findViewById(R.id.routes_button);
        Button add_routes = (Button) findViewById(R.id.add_routes_button);

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
                // TODO add method call to launchActivity to launch add routes activity
            }
        });


        int height = userObserver.getUserHeight();

        // height has not been set if userData is returning -1, default value
        heightSet = height != -1;

        // if height has not been set, show height input prompt
        if(!heightSet)
        {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainActivity.this);
            View heightView = getLayoutInflater().inflate(R.layout.dialog_height, null);
            final EditText heightInput = (EditText) heightView.findViewById(R.id.user_height);
            Button confirmHeight = (Button) heightView.findViewById(R.id.confirm_height);

            mBuilder.setView(heightView);
            final AlertDialog dialog = mBuilder.create();

            // if height confirmed, store in height stored preferences
            confirmHeight.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view)
                {
                    if(!heightInput.getText().toString().isEmpty())
                    {
                        int userHeight = Integer.parseInt(heightInput.getText().toString());

                        userObserver.updateHeight(userHeight);


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
