package com.example.omegar.History;

import com.example.omegar.NonActivityClasses.MealReference;
import com.google.firebase.database.DataSnapshot;

public interface HistoryGetServiceListener {
    void historyRetrievalSuccess(DataSnapshot DataSnapshot, MealReference[] MealReference);

    void historyRetrievalFailure(String Exception);
}
