package com.example.omegar.report;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.omegar.NonActivityClasses.MealReference;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReportGetService {
    private Query query;
    private ReportGetServiceListener listener;
    String uid, todayDate, startOfweek, Graphtype, datefrom, dateTo, startOfMonth;
    int LastWeekToToday, TodayEqualToday, arraySize;
    ArrayList<Float> o3, o6, ototal;
    ArrayList<String> DatetoIndex;
    ArrayList<BarEntry> values;
    ArrayList<Float> oRatio;
    DateFormat df;
    Calendar c;

    public ReportGetService() {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ReportGetService(ReportGetServiceListener listener, String Graphtype, String datefrom, String dateTo, int arraySize, ArrayList<String> DatetoIndex) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        this.listener = listener;
        this.Graphtype = Graphtype;
        this.datefrom = datefrom;
        this.dateTo = dateTo;
        this.arraySize = arraySize;
        this.DatetoIndex = DatetoIndex;
        setup();

    }


    public void getReportsStacked() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    convertToReportStacked(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.reportRetrievalFailure(databaseError.getMessage());
            }
        });
    }

    public void getReportsVertical() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    convertToReportVertical(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.reportRetrievalFailure(databaseError.getMessage());
            }
        });
    }

    private void convertToReportStacked(DataSnapshot querySnapshot) {
        MealReference meal = new MealReference();
        for (DataSnapshot snapshot : querySnapshot.getChildren()) {
            String datefromDB = snapshot.child("mealDate").getValue(String.class);
            String uidfromDB = snapshot.child("uID").getValue(String.class);
            if (uidfromDB.equals(uid)) {
                TodayEqualToday = todayDate.compareTo(datefromDB); // 0
                LastWeekToToday = startOfweek.compareTo(datefromDB); // -1
                meal = snapshot.getValue(MealReference.class);
                if (Graphtype.equals("0") && TodayEqualToday == 0) {
                    int mealtype1 = getMealType(meal.getMealType());
                    float tempo3 = o3.get(mealtype1);
                    float tempo6 = o6.get(mealtype1);
                    float tempototal = ototal.get(mealtype1);
                    o3.set(mealtype1, tempo3 + meal.getOmega3Total());
                    o6.set(mealtype1, tempo6 + meal.getOmega6Total());
                    ototal.set(mealtype1, tempototal + meal.sumofOmega());
                } else if (Graphtype.equals("1") && (LastWeekToToday < 1)) {
                    int date2 = getDateDay(meal.getMealDate());
                    float tempo3 = o3.get(date2);
                    float tempo6 = o6.get(date2);
                    float tempototal = ototal.get(date2);
                    o3.set(date2, tempo3 + meal.getOmega3Total());
                    o6.set(date2, tempo6 + meal.getOmega6Total());
                    ototal.set(date2, tempototal + meal.sumofOmega());
                } else if (Graphtype.equals("2")) {
                    int date2 = getDate(meal.getMealDate());
                    float tempo3 = o3.get(date2);
                    float tempo6 = o6.get(date2);
                    float tempototal = ototal.get(date2);
                    o3.set(date2, tempo3 + meal.getOmega3Total());
                    o6.set(date2, tempo6 + meal.getOmega6Total());
                    ototal.set(date2, tempototal + meal.sumofOmega());
                } else if (Graphtype.equals("3")) {
                    int middleTOfrom = datefromDB.compareTo(datefrom);
                    int middleTOto = datefromDB.compareTo(dateTo);
                    boolean firstCond = (middleTOfrom < 0) && (middleTOto < 0);
                    boolean secondCond = (middleTOfrom > 0) && (middleTOto > 0); // WE WANT BOTH FALSE
                    boolean finalDes = !firstCond && !secondCond;
                    if (finalDes) {
                        String datefromCustom = meal.getMealDate();
                        int IndexofDate = DatetoIndex.indexOf(datefromCustom.substring(5));
                        float tempo3 = o3.get(IndexofDate);
                        float tempo6 = o6.get(IndexofDate);
                        float tempototal = ototal.get(IndexofDate);
                        o3.set(IndexofDate, tempo3 + meal.getOmega3Total());
                        o6.set(IndexofDate, tempo6 + meal.getOmega6Total());
                        ototal.set(IndexofDate, tempototal + meal.sumofOmega());
                    }

                }
            }
        }
        setValueStacked();
    }

    private void convertToReportVertical(DataSnapshot querySnapshot) {
        MealReference meal = new MealReference();
        for (DataSnapshot snapshot : querySnapshot.getChildren()) {
            String date = snapshot.child("mealDate").getValue(String.class);
            String uidfromDB = snapshot.child("uID").getValue(String.class);
            if (uidfromDB.equals(uid)) {
                TodayEqualToday = todayDate.compareTo(date); // 0
                LastWeekToToday = startOfweek.compareTo(date); // -1
                meal = snapshot.getValue(MealReference.class);
                if (Graphtype.equals("0") && TodayEqualToday == 0) {
                    int mealtype1 = getMealType(meal.getMealType());
                    float tempRatio = oRatio.get(mealtype1);
                    oRatio.set(mealtype1, tempRatio + meal.getOmegaRatio());
                } else if (Graphtype.equals("1") && (LastWeekToToday < 1)) {
                    int date2 = getDateDay(meal.getMealDate());
                    float tempRatio = oRatio.get(date2);
                    oRatio.set(date2, tempRatio + meal.getOmegaRatio());
                } else if (Graphtype.equals("2")) {
                    int date2 = getDate(meal.getMealDate());
                    float tempRatio = oRatio.get(date2);
                    oRatio.set(date2, tempRatio + meal.getOmegaRatio());
                } else if (Graphtype.equals("3")) {
                    int middleTOfrom = date.compareTo(datefrom);
                    int middleTOto = date.compareTo(dateTo);
                    boolean firstCond = (middleTOfrom < 0) && (middleTOto < 0);
                    boolean secondCond = (middleTOfrom > 0) && (middleTOto > 0); // WE WANT BOTH FALSE
                    boolean finalDes = !firstCond && !secondCond;
                    if (finalDes) {
                        String datefromCustom = meal.getMealDate();
                        System.out.println("datefromCustom" + datefromCustom);
                        int IndexofDate = DatetoIndex.indexOf(datefromCustom.substring(5));
                        System.out.println("IndexofDate" + IndexofDate);
                        System.out.println("DatetoIndex" + DatetoIndex.size());
                        float tempRatio = oRatio.get(IndexofDate);
                        oRatio.set(IndexofDate, tempRatio + meal.getOmegaRatio());
                    }

                }
            }
        }
        setValueVertical();
    }

    private void setValueVertical() {
        if (!Graphtype.equals("3")) {
            for (int counter = 1; counter < oRatio.size(); counter++) {
                values.add(new BarEntry(counter - 1, oRatio.get(counter)));
            }
        } else {
            for (int counter = 0; counter < oRatio.size(); counter++) {
                values.add(new BarEntry(counter, oRatio.get(counter)));
            }
        }
        listener.reportRetrievalSuccess(values);
    }

    private void setValueStacked() {
        if (!Graphtype.equals("3")) {
            for (int counter = 1; counter < o3.size(); counter++) {
                values.add(new BarEntry(counter - 1, new float[]{o3.get(counter), o6.get(counter), ototal.get(counter)}));
            }
        } else {
            for (int counter = 0; counter < o3.size(); counter++) {
                values.add(new BarEntry(counter, new float[]{o3.get(counter), o6.get(counter), ototal.get(counter)}));
            }
        }
        listener.reportRetrievalSuccess(values);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setup() {
        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        df = new SimpleDateFormat("yyyy-MM-dd");
        startOfweek = df.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.DATE);
        todayDate = df.format(new Date());
        LocalDate todaydate = LocalDate.now();
        startOfMonth = todaydate.withDayOfMonth(1).toString();
        values = new ArrayList<>();
        o3 = new ArrayList<>();
        o6 = new ArrayList<>();
        ototal = new ArrayList<>();
        oRatio = new ArrayList<>();
        //Initialize your Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Reference to your entire Firebase database
        DatabaseReference meals = database.getReference("Meals");
        // Query to get the data
        query = meals.orderByKey().startAt(uid + "_" + datefrom);
        setuparraylist(arraySize);

    }

    private void setuparraylist(int days) {
        for (int i = 0; i <= days; i++) {
            o3.add(0f);
            o6.add(0f);
            ototal.add(0f);
            oRatio.add(0f);
        }
    }

    private int getDate(String mealDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(mealDate);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_MONTH);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getDateDay(String mealDate) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date date = format.parse(mealDate);
            Calendar cal = GregorianCalendar.getInstance();
            cal.setTime(date);
            return cal.get(Calendar.DAY_OF_WEEK);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int getMealType(String mealType) {
        switch (mealType) {
            case "Breakfast":
                return 1;
            case "Lunch":
                return 2;
            case "Dinner":
                return 3;
        }
        return 0;
    }

    public void getReportsTest() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("Meals").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listener.reportRetrievalSuccess(values);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
