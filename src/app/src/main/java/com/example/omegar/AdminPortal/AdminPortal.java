package com.example.omegar.AdminPortal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.omegar.R;

public class AdminPortal extends AppCompatActivity {
    CardView Meals;
    CardView FoodList;
    Intent AdminPortalMeals;
    Intent ShowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_portal);
        Meals = (CardView) findViewById(R.id.Meals);
        FoodList = (CardView) findViewById(R.id.FoodList);
        AdminPortalMeals = new Intent(this, AdminPortalMeals.class);
        ShowData = new Intent(this, Showdata.class);
        Meals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (AdminPortalMeals != null) startActivity(AdminPortalMeals);
            }
        });
        FoodList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ShowData != null) startActivity(ShowData);
            }
        });
    }
}