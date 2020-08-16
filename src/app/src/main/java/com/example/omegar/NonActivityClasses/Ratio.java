package com.example.omegar.NonActivityClasses;

import java.util.ArrayList;
import java.util.Date;

public class Ratio {
    //All measurements for this class are for the day of the objects creation.
    //Any additional views such as weekly average will be calculated in another class
    private double omega3Total;		//Measured in mg
    private double omega6Total;		//Measured in mg
    private double ratio;
    private Date dateCreated;		//Convert to Calendar later
    private ArrayList<Meal> mealHist;
    //Add timestamp

    public Ratio() {
        this.omega3Total = 0;
        this.omega6Total = 0;
        this.mealHist = new ArrayList<Meal>();
    }

    //Setters
    public void setOmega3Total(double amount) {
        this.omega3Total = amount;
    }

    public void setOmega6Total(double amount) {
        this.omega6Total = amount;
    }
    //


    //Getters
    public double getOmega3Total() {
        return this.omega3Total;
    }

    public double getOmega6Total() {
        return this.omega6Total;
    }

    public Meal getMeal(int i) throws IndexOutOfBoundsException, NullPointerException {
        return this.mealHist.get(i);
    }
    //


    //General Methods
    public boolean inputMeal(Meal meal) {
        this.mealHist.add(meal);
        this.omega3Total += meal.getOmega3Total();
        this.omega6Total += meal.getOmega6Total();

        return true;
    }


}