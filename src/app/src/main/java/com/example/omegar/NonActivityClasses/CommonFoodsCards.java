package com.example.omegar.NonActivityClasses;

//Card View Object for common Foods
public class CommonFoodsCards {
    //Attributes
    String FoodName;
    String Omega;
    String DataSource;


    //Constructor method
    public CommonFoodsCards(String FoodName, String Omega, String DataSource){
        setFoodName(FoodName);
        setOmega(Omega);
        setDataSource(DataSource);
    }

    //getter setters
    public String getFoodName() {
        return FoodName;
    }

    public String getOmega() {
        return Omega;
    }

    public String getDataSource(){
        return DataSource;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public void setOmega(String omega) {
        Omega = omega;
    }

    public void setDataSource(String dataSource){
        DataSource = dataSource;
    }
}
