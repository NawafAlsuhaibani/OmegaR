package com.example.omegar.MealinputQuery;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.database.sqlite.SQLiteStatement;


public class DatabaseAccess {
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DatabaseAccess instance;

    private DatabaseAccess(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }


    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }
        return instance;
    }


    public void open() {
        this.database = openHelper.getWritableDatabase();
    }

    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public Cursor commonfoodsquery(String OmegaTable){
        Cursor cursor = database.query(OmegaTable,new String[]{"description,amount,data_source"},"", null, null, null, "amount DESC","20");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor commonfoods(){
        //Cursor cursor = database.rawQuery("SELECT description,amount,data_source FROM amount, name WHERE name.fdc_id = amount.fdc_id AND nutrient_id IN (1278,1280,1272,1404,1405,868) ORDER BY amount DESC LIMIT 30",null);
        Cursor cursor = database.query("commonfoods",new String[]{"description,amount,data_source"},"", null, null, null, "amount DESC","20");

        if (cursor != null) {
            cursor.moveToFirst();
        }

        return cursor;
    }

    public Cursor searchMeal(String inputText, String datasource) {

        String iT = inputText;
        String dt = datasource;
        System.out.println(iT + "It" + dt + "dt");

        SQLiteStatement stmt;
        Cursor mCursor;


        //String MY_QUERY = "SELECT f.fdc_id as _id,f.description FROM food f INNER JOIN food_nutrient fn ON f.fdc_id=fn.fdc_id WHERE fn.amount > 0 AND f.description LIKE '%" + inputText + "%' LIMIT 100";
        //database.rawQuery(MY_QUERY, new String[]{String.valueOf(propertyId)});
        if (datasource.equals("CNF,USDA")) {
            mCursor = database.query("Vfood", new String[]{"fdc_id as _id", "description", "data_source"}, "description MATCH ?", new String[]{iT}, null, null, null);
        } else {
            mCursor = database.query("Vfood", new String[]{"fdc_id as _id", "description", "data_source"}, "data_source = ? AND description MATCH ?", new String[]{dt, iT}, null, null, null);
        }


        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;

    }

    public double[] getOmega(String inputID) {
        double[] omega = new double[2];
        double omega6Value = 0;
        double omega3Value = 0;
        double amount = 0;
        int nutrient_id = 0;
        int[] omega6 = {1316, 1321, 1313, 1406, 1408, 869};
        int[] omega3 = {1278, 1280, 1272, 1404, 1405, 868};

        Cursor mCursor = database.query("amount", new String[]{"nutrient_id, amount"}, "fdc_id = ? AND nutrient_id IN (1412,1316, 1404, 1321, 1313, 1405, 1406, 1414, 1408, 1278, 2014, 2015, 1280, 1272,868,869)", new String[]{inputID}, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
            while (!mCursor.isAfterLast()) {
                nutrient_id = mCursor.getInt(mCursor.getColumnIndexOrThrow("nutrient_id"));
                amount = mCursor.getDouble(mCursor.getColumnIndexOrThrow("amount"));

                for (int element : omega6) {
                    if (nutrient_id == element) {
                        omega6Value += amount;
                    }
                }
                for (int element : omega3) {
                    if (nutrient_id == element) {
                        omega3Value += amount;

                    }
                }
                mCursor.moveToNext();
            }

            mCursor.close();

            omega[0] = omega3Value;
            omega[1] = omega6Value;
        }

        return omega;
    }

}
