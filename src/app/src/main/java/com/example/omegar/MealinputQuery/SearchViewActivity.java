package com.example.omegar.MealinputQuery;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.example.omegar.MultipleFoodsPopup;
import com.example.omegar.NonActivityClasses.Meal;
import com.example.omegar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchViewActivity extends Activity implements SearchView.OnQueryTextListener,
        SearchView.OnCloseListener {

    private ListView mListView;
    private SearchView searchView;
    DatabaseAccess databaseAccess;
    String _id;
    String description, uid, mealType, datasource;
    EditText xamount;
    double[] omega;
    private FirebaseDatabase fBase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = fBase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);
        Intent intent = getIntent();
        mealType = intent.getStringExtra("mealType");
        datasource = intent.getStringExtra("datasource");

        Button backbtn = findViewById(R.id.back);
        xamount = findViewById(R.id.editText6);
        searchView = findViewById(R.id.search);
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);
        searchView.setOnCloseListener(this);
        databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        //new SetupDB().execute();
        mListView = findViewById(R.id.list);
        // Catch event on [x] button inside search view
        int searchCloseButtonId = searchView.getContext().getResources()
                .getIdentifier("android:id/search_close_btn", null, null);
        ImageView closeButton = this.searchView.findViewById(searchCloseButtonId);
        // Set on click listener
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(true);
                mListView.setAdapter(null);
                mListView.setVisibility(View.GONE);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchViewActivity.super.onBackPressed();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (databaseAccess != null) {
            databaseAccess.close();
        }
    }

    public boolean onQueryTextChange(String newText) {
        mListView.setVisibility(View.VISIBLE);
        populateTable(newText, this);
        return false;
    }

    public boolean onQueryTextSubmit(String query) {
        populateTable(query, this);
        return false;
    }

    public boolean onClose() {
        //showResults("");
        return false;
    }

    private void populateTable(final String query, final Context ctx) {
        runOnUiThread(new Runnable() {
            public void run() {
                //If there are stories, add them to the table
                databaseAccess.open();
                Cursor cursor1 = databaseAccess.searchMeal((query != null ? query : "@@@@"), datasource);

                if (cursor1 == null) {
                    return;
                } else {
                    // Specify the columns we want to display in the result
                    String[] from = new String[]{
                            "description", "data_source"};
                    int[] to = new int[]{R.id.description, R.id.DataSource};
                    SimpleCursorAdapter customers = new SimpleCursorAdapter(ctx, R.layout.searchresult, cursor1, from, to);
                    mListView.setAdapter(customers);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            // Get the cursor, positioned to the corresponding row in the result set
                            Cursor cursor = (Cursor) mListView.getItemAtPosition(position);
                            //Tutturosso Green 14.5oz. NSA Italian Diced Tomatoes
                            // Get
                            _id = cursor.getString(cursor.getColumnIndexOrThrow("_id"));
                            description = cursor.getString(cursor.getColumnIndexOrThrow("description"));
                            searchView.setIconified(false);
                            searchView.setQuery(description, false);
                            mListView.setVisibility(View.GONE);

                            new GetOmegaValues().execute();
                        }
                    });
                }

            }
        });
    }

    public void SubmitMeal(View view) {
        Meal meal = null;
        double amount;
        double n3amount = omega[0];
        double n6amount = omega[1];

        //Convert the user input into string
        String amountText = xamount.getText().toString();
        if ((!amountText.equals("")) && (amountText != null) && (!description.equals("")) && (description != null)) {
            amount = Double.parseDouble(amountText);
            uid = mAuth.getCurrentUser().getUid();
            meal = new Meal(description, uid, mealType, n3amount, n6amount, amount);
            mDatabase = fBase.getReference().child("Meals");
            mDatabase.child(uid + "_" + meal.getMealID()).setValue(meal);
            Intent multiMeals = new Intent(getBaseContext(), MultipleFoodsPopup.class);
            startActivity(multiMeals);
        }

    }


    public class GetOmegaValues extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            databaseAccess.open();
            double[] omega = databaseAccess.getOmega(_id);
            setdata(omega);
            return null;
        }

        @Override
        protected void onPostExecute(String cards) {
            super.onPostExecute(cards);
            return;

        }
    }

    private void setdata(double[] omega) {
        this.omega = omega;

    }

    public class SetupDB extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            //databaseAccess.save();
            return null;
        }

        @Override
        protected void onPostExecute(String cards) {
            super.onPostExecute(cards);

        }
    }

}