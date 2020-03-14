package com.example.cse110_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MockStepTimeActivity extends AppCompatActivity {
    private MockStepTimeActivity MockScreen;
    private Button addStepsButton;
    private EditText editTime;
    private Button closeButton;
    private TextView counterView;
    private SharedPreferences mockDataSharedPref;
    private SharedPreferences.Editor mockDataEditor;
    private long counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mock_step_time);

        addStepsButton = (Button) findViewById(R.id.add_steps_button);
        editTime = (EditText) findViewById(R.id.input_time);
        closeButton = (Button) findViewById(R.id.close_mock);
        counterView = (TextView) findViewById(R.id.counter_view);

        mockDataSharedPref = getBaseContext().getSharedPreferences(
                getString(R.string.mock_shared_pref_key), Context.MODE_PRIVATE);

        mockDataEditor = mockDataSharedPref.edit();
        mockDataEditor.putLong(getString(R.string.mock_step_key), 0);
        counter = 0;

        addStepsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counter++;
                long stepCount = counter * 500;
                counterView.setText(String.valueOf(stepCount));
            }
        });

        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        long newCurrentTime = editTime.getText().toString().equals("") ? 0 : Long.parseLong(editTime.getText().toString());
        mockDataEditor.putLong(getString(R.string.mock_step_key), counter * 500);
        mockDataEditor.putLong(getString(R.string.mock_time_key), newCurrentTime);
        mockDataEditor.apply();
        mockDataEditor.commit();
    }
}
