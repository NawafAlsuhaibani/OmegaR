package com.example.omegar.report;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.omegar.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class activityReport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        NavController navController = Navigation.findNavController(this, R.id.activity_main_navhostfragment);
        BottomNavigationView bottomNavigation = findViewById(R.id.activity_main_bottomnavigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);
    }
}
