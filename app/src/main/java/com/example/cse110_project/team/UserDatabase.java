package com.example.cse110_project.team;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public interface UserDatabase {
    void updateUser(GoogleSignInAccount account, String token);
    void addToTeam(String receiverEmail, String teamHostEmail);
    void findTeam();
}
