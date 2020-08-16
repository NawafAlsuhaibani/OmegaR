package com.example.omegar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.omegar.MealinputQuery.DatabaseOpenHelper;

public class DbHandler { //
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase database;
    private static DbHandler instance;

    private DbHandler(Context context) {
        this.openHelper = new DatabaseOpenHelper(context);
    }

    public static DbHandler getInstance(Context context) {
        if (instance == null) {
            instance = new DbHandler(context);
        }
        return instance;
    }


    public void close() {
        if (database != null) {
            this.database.close();
        }
    }

    public void save() {
        this.database = openHelper.getWritableDatabase();
        database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS Vfood USING FTS4(fdc_id,description,data_source)");
        String query = "SELECT * FROM Vfood";
        Cursor cr = database.rawQuery(query, null);
        if (cr.getCount() < 2) {
            database.execSQL("INSERT INTO Vfood SELECT fdc_id,description,data_source FROM name");
        }

        database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS commonfoodsO3 USING FTS4(description,amount,data_source)");
        database.execSQL("CREATE VIRTUAL TABLE IF NOT EXISTS commonfoodsO6 USING FTS4(description,amount,data_source)");

        String commonfoodqueryO3 = "SELECT * FROM commonfoodsO3";
        Cursor cursorO3 = database.rawQuery(commonfoodqueryO3, null);
        if (cursorO3.getCount() < 2) {
            database.execSQL("INSERT INTO commonfoodsO3 SELECT description,amount,data_source FROM amount, name WHERE name.fdc_id = amount.fdc_id AND nutrient_id IN (1278,1280,1272,1404,1405,868) ORDER BY amount DESC LIMIT 30");
        }

        String commonfoodqueryO6 = "SELECT * FROM commonfoodsO6";
        Cursor cursorO6 = database.rawQuery(commonfoodqueryO6, null);
        if (cursorO6.getCount() < 2) {
            database.execSQL("INSERT INTO commonfoodsO6 SELECT description,amount,data_source FROM amount, name WHERE name.fdc_id = amount.fdc_id AND nutrient_id IN (1316,1321,1313,1406,1408,869) ORDER BY amount DESC LIMIT 30");
        }

        close();
    }
}
