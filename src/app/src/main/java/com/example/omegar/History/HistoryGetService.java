package com.example.omegar.History;

import com.example.omegar.NonActivityClasses.MealReference;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class HistoryGetService {
    private Query query;
    private HistoryGetServiceListener listener;
    String date, to, uid, MealDate, UIDfromDB;
    int LastWeekToToday;

    public HistoryGetService(HistoryGetServiceListener listener, String date, String to) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        this.listener = listener;
        this.date = date;
        this.to = to;
        setup();
    }

    public HistoryGetService(Query query, HistoryGetServiceListener listener) {
        this.query = query;
        this.listener = listener;
    }

    public HistoryGetService() {

    }

    public void getReports() {
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listener.historyRetrievalSuccess(dataSnapshot, convertToReportReferences(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.historyRetrievalFailure(databaseError.getMessage());
            }
        });
    }

    private MealReference[] convertToReportReferences(DataSnapshot querySnapshot) {
        MealReference[] tempResult = new MealReference[(int) querySnapshot.getChildrenCount()];
        MealReference[] result;
        int Tempindex = 0;
        for (DataSnapshot snapshot : querySnapshot.getChildren()) {
            MealDate = snapshot.child("mealDate").getValue(String.class);
            UIDfromDB = snapshot.child("uID").getValue(String.class);
            LastWeekToToday = MealDate.compareTo(to); // -1
            if ((UIDfromDB.equals(uid)) && (LastWeekToToday < 1)) {
                tempResult[Tempindex] = new MealReference(snapshot);
                Tempindex++;
            }
        }
        result = new MealReference[Tempindex];
        System.arraycopy(tempResult, 0, result, 0, result.length);
        return result;
    }

    private void setup() {
        //Initialize your Firebase app
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        // Reference to your entire Firebase database
        DatabaseReference meals = database.getReference("Meals");
        // Query to get the data
        query = meals.orderByKey().startAt(uid + "_" + date);
    }

    public void getReportsTest() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("Meals").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    listener.historyRetrievalSuccess(dataSnapshot, convertToReportReferences(dataSnapshot));
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                listener.historyRetrievalFailure(databaseError.getMessage());
            }
        });
    }
}
