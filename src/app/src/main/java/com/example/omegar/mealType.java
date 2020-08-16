package com.example.omegar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.example.omegar.MealinputQuery.SearchViewActivity;

public class mealType extends AppCompatActivity {

    CheckBox btnCNF;
    CheckBox btnUSDA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_type);

        btnCNF = findViewById(R.id.buttonCNF);
        btnUSDA = findViewById(R.id.buttonUSDA);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //change the value multiplied at the end between 0-1 to set popup window dimensions (1 = full screen 0 = doesn't show)
        int width = (int) ((metrics.widthPixels) * 0.8);
        int height = (int) ((metrics.heightPixels) * 0.8);

        getWindow().setLayout(width, height);

        Button breakfast = findViewById(R.id.mealBreakfast);
        Button lunch = findViewById(R.id.mealLunch);
        Button dinner = findViewById(R.id.mealDinner);


        breakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent nextAct = new Intent(getBaseContext(), SearchViewActivity.class);
                    String mealType = "Breakfast";
                    String datasource = getDatasource();
                    nextAct.putExtra("mealType", mealType);
                    nextAct.putExtra("datasource", datasource);
                    startActivity(nextAct);
            }
        });

        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent nextAct = new Intent(getBaseContext(), SearchViewActivity.class);
                    String mealType = "Lunch";
                    String datasource = getDatasource();
                    nextAct.putExtra("mealType", mealType);
                    nextAct.putExtra("datasource", datasource);
                    startActivity(nextAct);
                }
        });

        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent nextAct = new Intent(getBaseContext(), SearchViewActivity.class);
                    String mealType = "Dinner";
                    String datasource = getDatasource();
                    nextAct.putExtra("mealType", mealType);
                    nextAct.putExtra("datasource", datasource);
                    startActivity(nextAct);
                }
        });


    }
    public String getDatasource(){
        if((btnCNF.isChecked())&&(!btnUSDA.isChecked())) {
            return "CNF";

        }else if((btnUSDA.isChecked())&&(!btnCNF.isChecked())){
            return "USDA";
        } else {
            return "CNF,USDA";
        }
    }
}