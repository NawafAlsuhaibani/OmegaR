package com.example.omegar.AdminPortal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.omegar.NonActivityClasses.MealReference;
import com.example.omegar.R;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public class Showdata extends AppCompatActivity implements AdminGetServiceListener {

    private static final String TAG = "Showdata";
    ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdata);
        Log.d(TAG, "onCreate: Started.");
        mListView = (ListView) findViewById(R.id.listView);
        AdminGetService ADG = new AdminGetService(this);
        ADG.getDataShowData();


        MealReference matt11 = new MealReference();


        ArrayList<MealReference> MealList = new ArrayList<>();
        MealList.add(matt11);
        getMealLists();

        MealListAdapter adapter = new MealListAdapter(this, R.layout.adapter_view_layout, MealList);
        mListView.setAdapter(adapter);
    }

    private void getMealLists() {
    }

    @Override
    public void generateDataLine(ArrayList<Entry> values, ArrayList<String> values1) {

    }

    @Override
    public void generateDataPie(ArrayList<PieEntry> values) {

    }

    @Override
    public void generateDataBar(ArrayList<BarEntry> values, ArrayList<String> values1) {

    }

    @Override
    public void reportRetrievalFailure(String Exception) {

    }

    @Override
    public void AddCharts() {

    }

    @Override
    public void getShowdata(ArrayList<MealReference> MR) {

        MealListAdapter adapter = new MealListAdapter(this, R.layout.adapter_view_layout, MR);
        mListView.setAdapter(adapter);

    }
}
