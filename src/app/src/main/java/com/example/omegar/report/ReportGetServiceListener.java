package com.example.omegar.report;

import com.example.omegar.NonActivityClasses.MealReference;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;

import java.util.ArrayList;

public interface ReportGetServiceListener {

    void reportRetrievalFailure(String Exception);

    void reportRetrievalSuccess(ArrayList<BarEntry> values);
}
