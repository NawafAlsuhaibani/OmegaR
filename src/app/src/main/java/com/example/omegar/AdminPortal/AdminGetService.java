package com.example.omegar.AdminPortal;

import com.example.omegar.NonActivityClasses.MealReference;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminGetService {
    String todayDate;
    int TodayEqualToday;
    DateFormat df;
    private AdminGetServiceListener listener;
    ArrayList<String> DatetoIndex;
    ArrayList<Integer> NumberOfMeals;
    ArrayList<DataSnapshot> TodayDataSnapshot;
    DatabaseReference meals;
    ArrayList<Float> oRatio = new ArrayList<Float>();


    public AdminGetService(AdminGetServiceListener listener) {
        this.listener = listener;
        setup();
    }

    private void setup() {
        df = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = df.format(new Date());
        //Initialize your Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Reference to your entire Firebase database
        meals = database.getReference("Meals");
        DatetoIndex = new ArrayList<>();
        NumberOfMeals = new ArrayList<>();
        TodayDataSnapshot = new ArrayList<DataSnapshot>();
    }

    public void getData() {
        meals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    convertToValues(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.reportRetrievalFailure(databaseError.getMessage());
            }
        });

    }

    private void convertToValues(DataSnapshot dataSnapshot) {
        String key;
        String datefromDB;
        Matcher m;
        float tempRatio;
        Float tempOmega3;
        Float tempOmega6;
        Float tempDev;
        Pattern p = Pattern.compile("--(2[0-3]|[01][0-9]):");
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            key = snapshot.getKey();
            datefromDB = snapshot.child("mealDate").getValue(String.class);
            m = p.matcher(key);
            TodayEqualToday = todayDate.compareTo(datefromDB); // 0
            //if (TodayEqualToday == 0) {
            TodayDataSnapshot.add(snapshot);
            if (m.find()) {
                if (!DatetoIndex.contains(m.group(1))) { // if DatetoIndex contains the HOUR.
                    DatetoIndex.add(m.group(1));
                }
            }
            //}
        }
        Collections.sort(DatetoIndex);
        setupRatio();
        for (int i = 0; i < TodayDataSnapshot.size(); i++) {
            key = TodayDataSnapshot.get(i).getKey();
            System.out.println("key= " + key);
            m = p.matcher(key);
            if (m.find()) {
                int indexOfMeal = DatetoIndex.indexOf(m.group(1));
                int numOfmeal = NumberOfMeals.get(indexOfMeal);
                tempOmega3 = TodayDataSnapshot.get(i).child("omega3Total").getValue(Float.class);
                if (tempOmega3.isNaN() || tempOmega3.isInfinite()) tempOmega3 = 0f;
                tempOmega6 = TodayDataSnapshot.get(i).child("omega6Total").getValue(Float.class);
                if (tempOmega6.isNaN() || tempOmega6.isInfinite()) tempOmega6 = 0f;
                tempDev = tempOmega6 / tempOmega3;
                if (tempDev.isNaN() || tempDev.isInfinite()) tempDev = 0f;
                tempRatio = oRatio.get(indexOfMeal);
                oRatio.set(indexOfMeal, tempRatio + tempDev);
                NumberOfMeals.set(indexOfMeal, numOfmeal + 1);
            }
        }
        ArrayList<Entry> values1 = new ArrayList<>();
        ArrayList<BarEntry> BarEntries = new ArrayList<>();
        ArrayList<PieEntry> PieEntries = new ArrayList<>();
        int[] PieEntries1 = new int[NumberOfMeals.size()];
        for (int j = 0; j < NumberOfMeals.size(); j++) {
            BarEntries.add(new BarEntry(j, oRatio.get(j)));
            values1.add(new Entry(j, NumberOfMeals.get(j)));
            PieEntries1[j] = NumberOfMeals.get(j);
            //System.out.println("index: " + j + ", oRatio" + oRatio.get(j));
        }

        int[] arrayPie = Top3(PieEntries1, PieEntries1.length);
        PieEntries.add(new PieEntry(arrayPie[0], ""));
        PieEntries.add(new PieEntry(arrayPie[1], ""));
        PieEntries.add(new PieEntry(arrayPie[2], ""));

        Collections.sort(DatetoIndex);
        listener.generateDataLine(values1, DatetoIndex);
        listener.generateDataBar(BarEntries, DatetoIndex);
        listener.generateDataPie(PieEntries);
        listener.AddCharts();
    }

    private void setupRatio() {
        for (int i = 0; i < DatetoIndex.size(); i++) {
            NumberOfMeals.add(0);
            oRatio.add(0f);
        }
    }


    int[] Top3(int[] arr, int arr_size) {
        int i, first, second, third;
        int[] array = new int[3];

        if (arr_size < 3) {
            System.out.print(" Invalid Input ");
            return arr;
        }
        third = first = second = Integer.MIN_VALUE;
        for (i = 0; i < arr_size; i++) {
			/* If current element is greater than
			first*/
            if (arr[i] > first) {
                third = second;
                second = first;
                first = arr[i];
            } else if (arr[i] > second) {
                third = second;
                second = arr[i];
            } else if (arr[i] > third)
                third = arr[i];
        }

        array[0] = first;
        array[1] = second;
        array[2] = third;
        return array;
    }

    public void getDataShowData() {
        meals.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    convertToValuesShowData(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.reportRetrievalFailure(databaseError.getMessage());
            }
        });
    }

    private void convertToValuesShowData(DataSnapshot dataSnapshot) {
        MealReference m;
        ArrayList<MealReference> meals = new ArrayList<MealReference>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
            m = snapshot.getValue(MealReference.class);
            meals.add(m);
        }
        listener.getShowdata(meals);
    }

}
