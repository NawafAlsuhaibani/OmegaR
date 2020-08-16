package com.example.omegar.report;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.omegar.Homepage;
import com.example.omegar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class option extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and UI accordingly.
        isloggedin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        variableInit();
        isloggedin();
        NavController navController = Navigation.findNavController(this, R.id.activity_main_navhostfragment);
        BottomNavigationView bottomNavigation = findViewById(R.id.activity_main_bottomnavigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }
    private void isloggedin() {
        if (currentUser != null) {
            if (!currentUser.getUid().isEmpty()) {
                Intent Homepage = new Intent(getBaseContext(), com.example.omegar.Homepage.class);
                startActivity(Homepage);
                finish();
            }
        }
    }
    private void variableInit() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
    }
}
