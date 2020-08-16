package com.example.omegar;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omegar.NonActivityClasses.AutocomplateCookingStyleAdapter;
import com.example.omegar.NonActivityClasses.AutocompleteFoodAdapter;
import com.example.omegar.NonActivityClasses.GlobalClass;
import com.example.omegar.NonActivityClasses.Meal;
import com.example.omegar.NonActivityClasses.Meal_nutrient;
import com.example.omegar.NonActivityClasses.food;
import com.example.omegar.NonActivityClasses.foodArray;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Array;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class MealInput2 extends AppCompatActivity {
    private FirebaseDatabase fBase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = fBase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private String mealType;

    GlobalClass gloClass;
    List<String> cookingStyleAvailable = new ArrayList<>();

    //String builder for separating food and cooking style
    StringBuilder NameNCookingStyle = new StringBuilder();
    StringBuilder NameNCookingStyleCommonFoods = new StringBuilder();
    foodArray converted = new foodArray();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_input2);


        //get meal type from the previous intent

        final Intent intent=getIntent();

        mealType=intent.getStringExtra("mealType");


        gloClass = (GlobalClass) getApplication();

        // Get a reference to the AutoCompleteTextView in the layout
        final Spinner cookingStyle = findViewById(R.id.cookingStyle);

        if(intent.hasExtra("food_name")){
            //get food info from common foods
            String commonfoodinput = getIntent().getStringExtra("food_name");
            connectedfromcommonfoods(commonfoodinput);

        }

        readJSON("nutrient_database/food_api.json");

        //list created for autocomplete adapter
        List<food> listForAdapter = new ArrayList<>(converted.array);
        AutocompleteFoodAdapter adapter = new AutocompleteFoodAdapter(this,listForAdapter);
        final AutoCompleteTextView foodNameInput = findViewById(R.id.autoCompleteTextView2);
        foodNameInput.setAdapter(adapter);
//        foodNameInput.getText();

        //to obtain the value of user input/ chose for the food option
        foodNameInput.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object itemSelected = parent.getItemAtPosition(position);
                food foodSelected = (food) itemSelected;
                String foodName = foodSelected.getFood_name();
                getfoodnameinput(foodName);
            }
        });

        //add a default option for the spinner
        cookingStyleAvailable.add("-- Please choose one cooking style --");
        //regular setting of the spinner adapter
        final ArrayAdapter<String> cookingStyleAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cookingStyleAvailable);
        cookingStyleAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cookingStyle.setAdapter(cookingStyleAdapter);
        //set the default selected option as "-- Please choose one cooking style --"
        cookingStyle.setSelection(0);
        //to obtain the vaule of the user chose for the cooking style
        cookingStyle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cookingStyle.setSelection(position);
                Object cookingStyleSelected = parent.getItemAtPosition(position);
                NameNCookingStyle.append(cookingStyleSelected.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        final EditText xamount = findViewById(R.id.editText6);

        final Button mealInputButton = findViewById(R.id.input_meal); //change to button ID
        mealInputButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onClick(View v) {

                Meal meal = null;
                double amount;

                //Convert the user input into string
                String amountText = xamount.getText().toString();

                String foodAte = null;
                if(intent.hasExtra("food_name")){
                    foodAte = NameNCookingStyleCommonFoods.toString();
                }else{
                    foodAte = NameNCookingStyle.toString();
                }

                /*if (Double.parseDouble(amountText.toString()) < 100) {
                    Toast.makeText(MealInput2.this, "Please input more than 100g", Toast.LENGTH_LONG).show();
                    return;
                }*/

                double n3amount = 0;
                double n6amount = 0;
                if (foodAte.equals("") || foodAte == null) {
                    Toast.makeText(MealInput2.this, "Please input the food", Toast.LENGTH_LONG).show();
                    return;
                }
                if (amountText.equals("") || amountText == null) {
                    Toast.makeText(MealInput2.this, "Please input the weight", Toast.LENGTH_LONG).show();
                    return;
                }
                //if user entered something for both textViews, do the following
                if (!amountText.equals("") && !foodAte.equals("") && amountText != null && foodAte != null) {
                    amount = Double.parseDouble(amountText);
                    for (Meal_nutrient i : gloClass.getCNF_nutrients()) {
                        if (i.food_code.equals(converted.getFoodCode(foodAte))) {
                            if (i.nutrient_name_id.equals("868")) {
                                n3amount += (Double.parseDouble(i.nutrient_value) * amount / 100);
                            }
                            if (i.nutrient_name_id.equals("869")) {
                                n6amount += (Double.parseDouble((i.nutrient_value)) * amount / 100);
                            }
                        }
                    }

                    //add a new meal
                    Toast.makeText(MealInput2.this,  "Omega6: " + String.format("%1$,.2f",n6amount)+"\nOmega3: " + String.format("%1$,.2f",n3amount) , Toast.LENGTH_LONG).show();

                    //add a new meal to DB with meal is as ( used id + date+timestamp)

                    String uid = mAuth.getCurrentUser().getUid();
                    meal = new Meal(foodAte, uid, mealType, n3amount, n6amount, amount);
                    addMealtoDB(meal,uid);


                    Intent multiMeals = new Intent(getBaseContext(), MultipleFoodsPopup.class);
                    startActivity(multiMeals);
                }
            }
        });


        final Button backButton = findViewById(R.id.back); //change to button ID
        backButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent mealIntent = new Intent(getBaseContext(), Homepage.class);
                startActivity(mealIntent);
            }
        });
    }

    public int getfoodnameinput(String foodname){
        int foodfound = 0;
        if (NameNCookingStyle.length() != 0) {
            NameNCookingStyle.delete(0, NameNCookingStyle.length());
        }

        NameNCookingStyle.append(foodname);
        for (food i : converted.array) {
            if (i.getFood_name().equals(foodname)) {
                cookingStyleAvailable.add(i.getFood_CookingStyle());
                foodfound = 1;
            }
        }
        return foodfound;
    }

    public int readJSON(String filepath){
        int tracker = 0;
        try {
            //In this try clause, read and parse JSON file "food_api.json" to fill up the autocomplete textView
            //create an assetManager object to get access to "assets" folder in the app
            AssetManager assetManager = this.getAssets();
            //open up an input stream for "food_api.json"
            InputStream input = assetManager.open(filepath);
            int size = input.available();
            byte[] buffer = new byte[size];
            input.read(buffer);
            input.close();

            //converting the unparsed bytes into a string
            String unconverted = new String(buffer, StandardCharsets.UTF_8);

            //create JSON array with the unparsed string
            JSONArray jarray = new JSONArray(unconverted);

            //extracting JSONObject from JSONArray
            for (int i = 0; i < jarray.length(); i++) {
                JSONObject obj = jarray.getJSONObject(i);
                //get the value of food_code and food_description
                String code = obj.getString("food_code");
                String description = obj.getString("food_description");
                //store the data into a food object/model
                food foodI = new food(code, description);
                //add the food into an arraylist
                converted.add(foodI);
            }
            tracker = 1;
        } catch (IOException e) {
            Log.e("Chris", Log.getStackTraceString(e));

        } catch (JSONException e) {
            Log.e("IGAT ", Log.getStackTraceString(e));
        }
        return tracker;
    }

    public void connectedfromcommonfoods(String intentextra){
        //find food name
        String commonfoodsname = intentextra.substring(0,intentextra.contains(",")?intentextra.indexOf(","):intentextra.length());

        //find cooking style
        String commonfoodscookingstyle = intentextra.substring(intentextra.indexOf(","));

        AutoCompleteTextView foodNameInput = findViewById(R.id.autoCompleteTextView2);
        Spinner cookingStyle = findViewById(R.id.cookingStyle);

        if (NameNCookingStyleCommonFoods.length() != 0) {
            NameNCookingStyleCommonFoods.delete(0, NameNCookingStyleCommonFoods.length());
        }

        foodNameInput.setText(commonfoodsname);
        NameNCookingStyleCommonFoods.append(commonfoodsname);

        cookingStyleAvailable.add(commonfoodscookingstyle);
        cookingStyle.setSelection(0);
        NameNCookingStyleCommonFoods.append(commonfoodscookingstyle);
    }

    public void addMealtoDB(Meal meal, String uid){
        mDatabase = fBase.getReference().child("Meals");
        mDatabase.child(uid+"_"+meal.getMealID()).setValue(meal);
    }

}