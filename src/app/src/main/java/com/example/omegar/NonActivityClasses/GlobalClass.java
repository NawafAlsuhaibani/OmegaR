package com.example.omegar.NonActivityClasses;

import android.app.Application;
import android.util.Log;

import java.util.Calendar;
import java.sql.*; //not needed i think
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;


public class GlobalClass extends Application {

    //variables
    private boolean loadNutrient = false; //if nutrients_amount.json is loaded once, this would be true and no need to load again

    ArrayList<Meal_nutrient> CNF_nutrients = new ArrayList<>();

    DBConnector dbC = new DBConnector();
    private MealData meals;

    private ArrayList<Meal> allMeals;


    //MealData strictly for MealHistory.activity
    private MealData monthlyMeals;
    private MealData weeklyMeals;

    //Constructor
    //public so that login.java can make this obj.
    public GlobalClass() {
        meals = new MealData();

        allMeals = new ArrayList<Meal>();
    }

    //METHODS

    /*Getter and Setter methods*/

    public boolean isLoadNutrient() {
        return loadNutrient;
    }

    public MealData getMeals() {
        return this.meals;
    }

    public ArrayList<Meal_nutrient> getCNF_nutrients() {
        return CNF_nutrients;
    }

    public void setLoadNutrient(boolean f) {
        loadNutrient = f;
    }

    /*End of Getter and Setter methods*/

    public void addNutrients(Meal_nutrient nutrient) {
        CNF_nutrients.add(nutrient);
    }
}
