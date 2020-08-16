package com.example.omegar.AdminPortal;

import com.example.omegar.NonActivityClasses.MealReference;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;

public interface AdminGetServiceListener {
    void generateDataLine(ArrayList<Entry> values, ArrayList<String> values1);

    void generateDataPie(ArrayList<PieEntry> values);

    void generateDataBar(ArrayList<BarEntry> values, ArrayList<String> values1);

    void reportRetrievalFailure(String Exception);

    void AddCharts();

    void getShowdata(ArrayList<MealReference> MR);
}
