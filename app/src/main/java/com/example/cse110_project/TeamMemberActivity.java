package com.example.cse110_project;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cse110_project.firestore.FirestoreUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class TeamMemberActivity extends AppCompatActivity {
    private static final int MAX_NAME_LEN = 25, MAX_START_LEN = 10, TEXT_EMPTY = 3;
    private static final String ELLIPSE = "...";
    private static final int PADDING = 10, MARGIN = 20;
    private String userEmail;
    private static String teamId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_team_member);
        userEmail = WWRApplication.getUserAccount().getEmail();

        final CollectionReference usersRef = FirestoreUtil.USERS_REF;
       // usersRef.document(email).collection(FirestoreUtil.ROUTES_KEY);
        /*Map<String, Object> data1 = new HashMap<>();
        data1.put("firstName", "Test");
        data1.put("lastName", "Tester");
        data1.put("teamID", 1);
        usersRef.document("User1").set(data1);

        Map<String, Object> data2 = new HashMap<>();
        data2.put("firstName", "Testy");
        data2.put("lastName", "Testin");
        data2.put("teamID", 1);
        usersRef.document("User2").set(data2);
        */
        final LinearLayout teamMemberList = findViewById(R.id.team_member_list_layout);
        final FloatingActionButton inviteButton = (FloatingActionButton) findViewById(R.id.invite_button);
        final Button proposedRoutesButton = (Button) findViewById(R.id.proposed_routes_button);
        usersRef
            .whereEqualTo("email", userEmail)
            .get()
            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for(QueryDocumentSnapshot document : task.getResult()){
                        teamId = document.getString("team_id");

                        usersRef
                            .whereEqualTo("team_id", teamId)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Log.d("docResults", document.getId() + " => " + document.getData());
                                        RelativeLayout relativeLayout = new RelativeLayout(TeamMemberActivity.this);
                                        final RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);

                                        // Initial text view
                                        TextView initialView = new TextView(TeamMemberActivity.this);

                                        String firstName = document.getString("first_name");
                                        String lastName = document.getString("last_name");
                                        StringBuilder sb = new StringBuilder();
                                        sb.append(firstName.charAt(0)).append(lastName.charAt(0));
                                        String initials = sb.toString();
                                        initials.toUpperCase();
                                        initialView.setText(initials);
                                        initialView.setBackgroundColor(Color.RED);
                                        initialView.setTextSize(20);
                                        initialView.setWidth(100);
                                        initialView.setHeight(89);
                                        //initialView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                                        initialView.setGravity(Gravity.CENTER);

                                        relativeLayout.addView(initialView);

                                        TextView teamMember = new TextView(TeamMemberActivity.this);
                                        teamMember.setBackgroundColor(Color.LTGRAY);
                                        teamMember.setAllCaps(false);

                                        String name = firstName + " " + lastName;
                                        if (name.length() > MAX_NAME_LEN)
                                            name = name.substring(0, MAX_NAME_LEN - TEXT_EMPTY) + ELLIPSE;
                                        teamMember.setText(String.format(name));
                                        teamMember.setTypeface(Typeface.MONOSPACE);
                                        teamMember.setLetterSpacing(0);
                                        teamMember.setPadding(MARGIN, MARGIN, MARGIN, MARGIN);

                                        relativeLayout.addView(teamMember);

                                        params.addRule(RelativeLayout.RIGHT_OF, initialView.getId());
                                        params.addRule(RelativeLayout.END_OF, initialView.getId());
                                        params.setMargins(100,0,0,0);
                                        teamMember.setLayoutParams(params);

                                        teamMemberList.addView(relativeLayout);
                                    }
                                } else {
                                    Log.d("docError", "Error getting documents: ", task.getException());
                                }
                                }
                            });
                    }
                }
                }
            });


        inviteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMemberActivity.this, InviteActivity.class);
                startActivityForResult(intent, 0);
            }
        });

        proposedRoutesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeamMemberActivity.this, ProposedRouteActivity.class);
                startActivityForResult(intent, 0);
            }
        });

    }

}
