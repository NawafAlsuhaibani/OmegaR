package com.example.omegar.NonActivityClasses;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import static android.icu.lang.UProperty.MATH;

public class MealData {
    public List<Meal> getMeals() {
        return meals;
    }

    List<Meal> meals = new ArrayList<Meal>();
    double saveo6 = 0;

    public double getO6() {
        return o6;
    }

    public double getO3() {
        return o3;
    }

    double o6 = 0;
    double o3 = 0;
    private int simpleRatio = 0;
    //Constructor
    public MealData(){

    }


    //Methods
    public void addMeal(Meal m){
        meals.add(m);
    }


    //Todo: 02/26/20

    //This method returns an arrayList that holds all meals eaten by this user at this date
    public List<Meal> getMealsAtDate(int date){
        List<Meal> tempMeals = new ArrayList<Meal>();

        //iterate through all meals
        for (Meal m: meals) {
          /*  if(m.getMealDate().get(Calendar.DATE) == date){
                tempMeals.add(m);
            }*/
        }



        return tempMeals;
    }


    //This method will calculate the total O3 from all meals eaten this date.
    public int calculateRoundedTotalOmega3ofMealsAtDate(int date){
        List<Meal> tempMeals = getMealsAtDate(date);
        double total = 0;

        for (Meal m: tempMeals) {
            total += m.getOmega3();
        }

        return (int) Math.round(total);
    }


    public String calculate(){

        StringBuilder builder = new StringBuilder();

        for(Meal m : meals){
            o6+=m.getOmega6();
            o3+=m.getOmega3();
        }
        double complexRatio = 0;

        int roundedO6 = (int) Math.round(o6);
        int roundedO3 = (int) Math.round(o3);
        if(roundedO6>roundedO3){
            try {
                complexRatio = o6/o3;
                simpleRatio = (int) Math.round(complexRatio);
                builder.append(simpleRatio + " : 1");
            } catch (ArithmeticException e){
                builder.append(simpleRatio + ": 0");
            }
        } else {
            try{
                complexRatio = o3/o6;
                simpleRatio = (int) Math.ceil(complexRatio);
                builder.append("1: " + simpleRatio);
            } catch (ArithmeticException e){
                builder.append("0 : " + simpleRatio);
            }
        }

        return builder.toString();
    }

    public double getOmegaRatio(){
        return o6/o3;
    }
    public int getSize(){return meals.size();}
    public int getSimpleRatio(){
        return simpleRatio;
    }



}