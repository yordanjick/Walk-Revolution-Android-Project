package com.example.cse110_project;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.team.UserDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class AcceptActivity extends AppCompatActivity {

@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accept);
        Intent intent=getIntent();
        final String emailAddress=intent.getStringExtra("email_address");
        final String senderName=intent.getStringExtra("sender_name");
        if(senderName!=null) {
            TextView textView = findViewById(R.id.request_from);
            textView.setText(emailAddress);

            Button accept = findViewById(R.id.accept_button);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDatabase userDatabase = WWRApplication.getUserDatabase();
                    userDatabase.addToTeam(WWRApplication.getUserAccount().getEmail(), emailAddress);
                    finish();
                }
            });

            Button decline = findViewById(R.id.decline_button);
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        else{
            TextView textView = findViewById(R.id.request_from);
            textView.setText("Proposed Routes");
            final FirebaseFirestore database = FirebaseFirestore.getInstance();
            final HashMap<String, String> data = new HashMap<>();

            data.put("receiverName", "");
            data.put("receiverEmail", emailAddress);
            data.put("senderEmail", WWRApplication.getUserAccount().getEmail());



            Button accept = findViewById(R.id.accept_button);
            accept.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.put(getString(R.string.message_type), "Accept Proposal");
                    database.collection("messages")
                            .add(data);
                    finish();
                }
            });

            Button decline = findViewById(R.id.decline_button);
            decline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.put(getString(R.string.message_type), "Decline Proposal");
                    database.collection("messages")
                            .add(data);
                    finish();
                }
            });
        }
    }
    @Override
    protected void onDestroy() {
//test
        super.onDestroy();
    }
}
