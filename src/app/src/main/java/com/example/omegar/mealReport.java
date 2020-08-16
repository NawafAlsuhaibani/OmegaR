package com.example.omegar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;




public class mealReport extends AppCompatActivity {

    // TODO: 11/26/2019 Methods below were designed to input dummy values. Refine methods when we are in the next semester/implementing DB.
    //  e.g.
    //  (1) retrieve data from input.class
    //  (2) save series into local file to keep record
    //  (3) check local series file against DB to ensure accuracy of data.
    ImageButton imageButton;

    String GRAPH,from,to;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_report);
        GRAPH = getIntent().getStringExtra("GRAPH");

        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");


        NavController navController = Navigation.findNavController(this, R.id.activity_main_navhostfragment);
        BottomNavigationView bottomNavigation = findViewById(R.id.activity_main_bottomnavigation);
        NavigationUI.setupWithNavController(bottomNavigation, navController);

        imageButton = findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {


                finish();
            }
        });
    }
    public String getGRAPH(){
        return GRAPH;
    }

    public String getFrom(){
        return from;
    }
    public String getTo(){
        return to;
    }


}
