package com.example.omegar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omegar.MealinputQuery.DatabaseAccess;
import com.example.omegar.MealinputQuery.DatabaseOpenHelper;
import com.example.omegar.NonActivityClasses.CommonFoodsCardAdapter;
import com.example.omegar.NonActivityClasses.CommonFoodsCards;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CommonFoods extends AppCompatActivity {

    DatabaseAccess databaseAccess;

    //back button
    private Button backBtn;

    //omega3 and 6 arraylists stored in cardview
    private ArrayList<CommonFoodsCards> omega3Cardlist = new ArrayList<>();
    private ArrayList<CommonFoodsCards> omega6Cardlist = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_foods);

        //back button
        backBtn = findViewById(R.id.back10);
        backBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                CommonFoods.super.onBackPressed();
            }
        });

        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();


        //display omega3
        RecyclerView omega3recycler = (RecyclerView) findViewById(R.id.omega3list);
        displayRecycler(omega3recycler,omega3Cardlist);
        fromsql("commonfoodsO3",omega3Cardlist);

        //display omega6
        RecyclerView omega6recycler = (RecyclerView) findViewById(R.id.omega6list);
        displayRecycler(omega6recycler,omega6Cardlist);
        fromsql("commonfoodsO6",omega6Cardlist);
    }

    public void fromsql(String OmegaTable, ArrayList<CommonFoodsCards> omegaCardList){
        Cursor cursor = databaseAccess.commonfoodsquery(OmegaTable);

        while (!cursor.isAfterLast()) {
            String description = cursor.getString(cursor.getColumnIndex("description"));
            String amount = cursor.getString(cursor.getColumnIndex("amount"));
            String datasource = cursor.getString(cursor.getColumnIndex("data_source"));

            CommonFoodsCards foods = new CommonFoodsCards(description,amount,datasource);
            omegaCardList.add(foods);

            cursor.moveToNext();
        }
        cursor.close();
    }


    //displays a recycler view to OmegaRecycler using omegaCardList
    public void displayRecycler(RecyclerView OmegaRecycler, ArrayList<CommonFoodsCards> omegaCardList){
        OmegaRecycler.setLayoutManager(new LinearLayoutManager(this));
        CommonFoodsCardAdapter omega6adapter = new CommonFoodsCardAdapter(this, omegaCardList);
        OmegaRecycler.setAdapter(omega6adapter);
        OmegaRecycler.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.HORIZONTAL));

    }
}