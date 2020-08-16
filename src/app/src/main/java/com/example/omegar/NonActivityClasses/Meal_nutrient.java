package com.example.omegar.NonActivityClasses;

import com.google.gson.annotations.SerializedName;

public class Meal_nutrient {
    @SerializedName("food_code")
    public String food_code;
    @SerializedName("nutrient_value")
    public String nutrient_value;
    @SerializedName("standard_error")
    public String standard_error;
    @SerializedName("number_observation")
    public String number_observation;
    @SerializedName("nutrient_name_id")
    public String nutrient_name_id;
    @SerializedName("nutrient_web_name")
    public String nutrient_web_name;
    @SerializedName("nutrient_source_id")
    public String nutrient_source_id;
}
