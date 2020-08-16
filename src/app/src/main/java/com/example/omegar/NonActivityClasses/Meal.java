package com.example.omegar.NonActivityClasses;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;


public class Meal implements Serializable {

    private String name;
    private String uID;
    private String mealType;   // meal type pre time of the day (breakfast, lunch ...)
    private double omega3;        //mg per 100g of food
    private double omega6;        //mg per 100g of food
    private double xamount;        //grams of food
    private double omega3Total;
    private double omega6Total;
    private String mealDate;


    public String getMealID() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd--HH:mmss");


        this.mealID = format.format(new Date());
        return mealID;
    }

    public void setmealID(String mealID) {
        mealID = mealID;
    }

    private String mealID;


    //Add picture

    public Meal() {

    }



    public String getuID() {
        return uID;
    }

    public String getMealType() {
        return mealType;
    }



    public Meal(String name, String uID, String mealType, double omega3, double omega6, double xamount) {
        this.name = name;
        this.uID = uID;
        this.mealType = mealType;
        this.omega3 = omega3;
        this.omega6 = omega6;
        this.xamount = xamount;
        this.omega3Total = calcTotal(this.omega3, this.xamount);
        this.omega6Total = calcTotal(this.omega6, this.xamount);
        this.mealDate = mealDateNow();

        //the date the user input the meal
    }

    //Setters
    public void setName(String name) {
        this.name = name;
    }

    public void setOmega3(double omega3) {
        this.omega3 = omega3;
    }

    public void setOmega6(double omega6) {
        this.omega6 = omega6;
    }

    public void setXamount(double xamount) {
        this.xamount = xamount;
    }


    //Getters
    public String getName() {
        return this.name;
    }

    public double getOmega3() {
        return this.omega3;
    }

    public double getOmega6() {
        return this.omega6;
    }

    public double getXamount() {
        return this.xamount;
    }

    public double getOmega3Total() {
        if (this.omega3Total == -1) {
            System.out.println("Error: Required Omega3 Info Not Found.");
            return 0;
        } else {
            this.omega3Total = calcTotal(this.omega3, this.xamount);
            return this.omega3Total;
        }
    }

    public double getOmega6Total() {
        if (this.omega6Total == -1) {
            System.out.println("Error: Required Omega6 Info Not Found.");
            return 0;
        } else {
            this.omega6Total = calcTotal(this.omega6, this.xamount);
            return this.omega6Total;
        }
    }


    //returns the day of the month in integer (e.g. 2020-06-30)
    public String getMealDate() {


        return this.mealDate;
    }

    public String mealDateNow() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String date = format.format(new Date());

        return date;
    }

    public void SetMealDate(String date) {


        this.mealDate = date;

    }

    //General Methods
    private double calcTotal(double omegaAcid, double amount) {
        double total = (omegaAcid * amount) / 100;

        return total;
    }


}