package com.example.cse110_project;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
public class InviteActivity extends AppCompatActivity {
    CollectionReference chat;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        Button add=findViewById(R.id.invite_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });
        chat=FirebaseFirestore.getInstance().collection("chats").document("chats").collection("chats");

        chat.addSnapshotListener((newChatSnapShot,error)->{
            if(newChatSnapShot!=null&&newChatSnapShot.isEmpty()){
                
            }

        });

    }
}
