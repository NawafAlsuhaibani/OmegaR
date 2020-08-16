package com.example.omegar.NonActivityClasses;

import java.util.Calendar;

public class UserProfile {
    private static int accountID = 0;

    public String name;
    public CharSequence email;
    private Calendar DOB,currentDate;
    public String age, gender, medicalCondition;
    public String height,weight;//Measured in cm


    public UserProfile(String name, CharSequence email, String weight, String age, String gender, String medicalCondition) {
        this.name=name;
        this.email=email;
        this.weight=weight;
        this.age=age;
        this.gender=gender;
        this.medicalCondition=medicalCondition;
        currentDate = Calendar.getInstance();
    }

    //General Methods

    //Gets age based on DOB
    public int calculateAge() {
        int year = this.DOB.get(Calendar.YEAR);
        int month = this.DOB.get(Calendar.MONTH);
        int day = this.DOB.get(Calendar.DATE);
        int age;

        this.currentDate.add(Calendar.YEAR, year*-1);
        this.currentDate.add(Calendar.MONTH, month*-1);
        this.currentDate.add(Calendar.DATE, day*-1);

        age = currentDate.get(Calendar.YEAR);
        Calendar.getInstance();

        return age;
    }

    public void setEmail(String email) {
        this.email=email;
    }

}

