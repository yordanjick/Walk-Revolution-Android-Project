package com.example.cse110_project;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.CollectionReference;
public class InviteActivity extends AppCompatActivity {
    CollectionReference chat;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        final Button addButton = findViewById(R.id.invite_button);
        final TextView userName = findViewById(R.id.invitename);
        final TextView userEmail = findViewById(R.id.inviteaddress);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = userEmail.getText().toString().trim();
                String name = userName.getText().toString().trim();

                // Test valid input, for now only @gmail.com and @ucsd.edu are accepted
                if(email.endsWith("@gmail.com") || email.endsWith("@ucsd.edu")) {
                    // Test name not empty
                    if(name.equals("")) {
                        Toast.makeText(getApplicationContext(), "Name Can't Be Empty", Toast.LENGTH_LONG).show();
                    }

                    // Push user receiver and sender info to database for processing
                    WWRApplication.getUserDatabase().pushInvite(email, name, WWRApplication.getUserAccount().getEmail());

                    // Notify user
                    Toast.makeText(getApplicationContext(), "Invitation Sent", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Email", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
