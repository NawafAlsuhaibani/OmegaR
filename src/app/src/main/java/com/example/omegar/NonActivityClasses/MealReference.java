package com.example.omegar.NonActivityClasses;


import com.google.firebase.database.DataSnapshot;

public class MealReference {
    private float omega3;        //mg per 100g of food
    private float omega6;        //mg per 100g of food

    private double xamount;        //grams of food

    private float omega3Total;
    private float omega6Total;
    private String mealType;
    private String name;
    public float totalBF;
    public float totalLUNCH;
    public float totalDINNER;
    public float total;
    float ratio;
    public String mealDate;

    public MealReference() {
        this.omega3Total = -1;
        this.omega6Total = -1;
    }


    public MealReference(DataSnapshot snapshot) {
        this.mealDate = snapshot.child("mealDate").getValue(String.class);
        this.mealType = snapshot.child("mealType").getValue(String.class);
        this.name = snapshot.child("name").getValue(String.class);
        this.omega3Total = snapshot.child("omega3Total").getValue(Float.class);
        this.omega6Total = snapshot.child("omega6Total").getValue(Float.class);
        this.xamount = snapshot.child("xamount").getValue(Double.class);
    }

    public MealReference(float omega3, float omega6, double xamount, String mealType) {

        this.omega3 = omega3;
        this.omega6 = omega6;
        this.xamount = xamount;
        this.omega3Total = calcTotal(this.omega3, this.xamount);
        this.omega6Total = calcTotal(this.omega6, this.xamount);

        this.mealType = mealType;
    }

    public MealReference(String name, String mealDate, String mealType, double xamount, float omega3Total, float omega6Total) {
        this.name = name;
        this.mealDate = mealDate;
        this.mealType = mealType;
        this.xamount = xamount;
        this.omega3Total = omega3Total;
        this.omega3Total = omega6Total;
    }

    //Setters
    public void setOmega3(float omega3) {
        this.omega3 = omega3;
    }

    public void setOmega6(float omega6) {
        this.omega6 = omega6;
    }


    public void setXamount(double xamount) {
        this.xamount = xamount;

    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    //Getters
    public String getName() {
        return name;
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

    public float getTotalLUNCH() {
        return totalLUNCH;
    }

    public float getTotalBF() {
        return totalBF;
    }

    public float getTotalDINNER() {
        return totalDINNER;
    }

    public String getMealDate() {
        return mealDate;
    }

    public String getMealType() {
        return mealType;
    }

    public float Omega3Total() {
        return omega3Total;
    }

    public float Omega6Total() {
        return omega6Total;
    }

    public float getOmega3Total() {
        if (this.omega3Total == -1) {
            System.out.println("Error: Required Omega3 Info Not Found.");
            return 0;
        } else {
            this.omega3Total = calcTotal(this.omega3, this.xamount);
            return this.omega3Total;
        }
    }

    public float getOmega6Total() {
        if (this.omega6Total == -1) {
            System.out.println("Error: Required Omega6 Info Not Found.");
            return 0;
        } else {

            this.omega6Total = calcTotal(this.omega6, this.xamount);

            return this.omega6Total;
        }
    }

    //General Methods
    private float calcTotal(double omegaAcid, double amount) {
        ratio = (float) ((omegaAcid * amount) / 100);
        return ratio;
    }

    public float sumofOmega() {
        return omega6Total + omega3Total;
    }

    public float getOmegaRatio() {
        float checkRatio = getOmega6Total() / getOmega3Total();
        if (Float.isNaN(checkRatio)) {
            return 0f;
        } else {
            return checkRatio;
        }
    }

    public float getOmega3TotalShowData() {
        return Omega3Total();
    }

    public float getOmega6TotalShowData() {
        return Omega6Total();
    }
}
