package com.example.omegar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

public class MultipleFoodsPopup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_foods_popup);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //change the value multiplied at the end between 0-1 to set popup window dimensions (1 = full screen 0 = doesn't show)
        int width = (int) ((metrics.widthPixels)*0.8);
        int height = (int) ((metrics.heightPixels)*0.8);

        getWindow().setLayout(width,height);

        Button addAnother = findViewById(R.id.addAnotherMeal);
        Button back = findViewById(R.id.multiMealBack);

        addAnother.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealType = new Intent(getBaseContext(), mealType.class);
                startActivity(mealType);
                finish();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mealIntent = new Intent(getBaseContext(), Homepage.class);
                startActivity(mealIntent);
            }
        });

    }
}