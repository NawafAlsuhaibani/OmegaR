package com.example.omegar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omegar.AdminPortal.AdminPortal;
import com.example.omegar.History.CustomeHistory;
import com.example.omegar.MealinputQuery.SearchViewActivity;
import com.example.omegar.NonActivityClasses.GlobalClass;
import com.example.omegar.NonActivityClasses.MealReference;
import com.example.omegar.report.MainActivityOption;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.navigation.NavigationView;
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
import java.util.ArrayList;
import java.util.Date;

public class Homepage extends AppCompatActivity {
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    private ImageButton burgerMenu;
    private ProgressBar o3progress, o6progress;
    private TextView omega3Weight;
    private TextView omega6Weight;
    private PieChart pieChart;
    int TodayEqualToday;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    boolean isAdmin;
    DateFormat df;
    String todayDate, query, uid, datefromDB;
    MealReference meal = new MealReference();
    ArrayList<Float> oRatio = new ArrayList<>();
    FirebaseDatabase fBase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase;
    Context c;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        final GlobalClass gloClass = (GlobalClass) getApplication();
        df = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = df.format(new Date());
        pieChart = findViewById(R.id.chart1);
        oRatio.add(0f);
        oRatio.add(0f);
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        uid = currentUser.getUid();
        c = getBaseContext();
        getData();

        o6progress = findViewById(R.id.o6progress);
        o3progress = findViewById(R.id.o3progess);


        omega3Weight = findViewById(R.id.Omega3WeightView);
        omega6Weight = findViewById(R.id.Omega6WeightView);

        o6progress.setMax(7);
        o3progress.setMax(7);


        //Progress bar illustration
        String o6 = String.format("%1$,.2f", (gloClass.getMeals().getO6()));
        String o3 = String.format("%1$,.2f", (gloClass.getMeals().getO3()));
        omega6Weight.setText("" + o6);
        omega3Weight.setText("" + o3);
        o6progress.setProgress((int) (Math.ceil(gloClass.getMeals().getO6())));
        o3progress.setProgress((int) (Math.ceil(gloClass.getMeals().getO3())));


        //Toast.makeText(Homepage.this, "# of Meals: " + gloClass.getMeals().getSize(),Toast.LENGTH_LONG).show(); //Display #of meals currently in arraylist.
        dl = findViewById(R.id.homepage);
        t = new ActionBarDrawerToggle(this, dl, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        //!!!!!!!!!!! Change the next 5 variables to final!
        /*Intent registerInfo = getIntent();
        //unloading all data from previous activity
        String name = registerInfo.getStringExtra("Name");
        String email = registerInfo.getStringExtra("Email");
        String phone = registerInfo.getStringExtra("Phone");
        String pwd = registerInfo.getStringExtra("Pwd");
*/

        dl.addDrawerListener(t);
        t.syncState();

        burgerMenu = findViewById(R.id.burgermenu);
        nv = findViewById(R.id.navigation);
        checkAdmin();
        burgerMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dl.openDrawer(nv);
            }
        });
        //ADD the line "drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);" if want to remove the default sliding function
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override

            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                //Fragment page = null;
                //Class fragmentClass = null;

                if (id == R.id.nav_mealHistory) {
                    Toast.makeText(Homepage.this, "meal History", Toast.LENGTH_SHORT).show();
                    Intent intentHistory = new Intent(getBaseContext(), MainActivityOption.class);
                    startActivity(intentHistory);
                } else if (id == R.id.nav_History) {
                    Toast.makeText(Homepage.this, "History", Toast.LENGTH_SHORT).show();
                    Intent intentmealHistory = new Intent(getBaseContext(), CustomeHistory.class);
                    startActivity(intentmealHistory);

                } else if (id == R.id.nav_CommonFoods) {
                    Toast.makeText(Homepage.this, "Common Foods", Toast.LENGTH_SHORT).show();
                    Intent intentCommonFoods = new Intent(getBaseContext(), CommonFoods.class);
                    startActivity(intentCommonFoods);

                } else if (id == R.id.nav_Education) {
                    Toast.makeText(Homepage.this, "Education", Toast.LENGTH_SHORT).show();
                    Intent educational = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.omegaratio.net/projects/"));
                    startActivity(educational);

                } else if (id == R.id.nav_TermsAndConditions) {
                    Toast.makeText(Homepage.this, "Terms And Conditions", Toast.LENGTH_SHORT).show();
                    Intent intentTerms = new Intent(getBaseContext(), TermsAndConditions.class);
                    startActivity(intentTerms);

                } else if (id == R.id.nav_dashboard && isAdmin) {
                    Toast.makeText(Homepage.this, "Admin Portal", Toast.LENGTH_SHORT).show();
                    Intent AdminPortal = new Intent(getBaseContext(), AdminPortal.class);
                    startActivity(AdminPortal);

                } else if (id == R.id.nav_logout) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this);
                    builder.setTitle("Log Out");
                    builder.setMessage("Are you sure to log out?");
                    // add a button
                    builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // reset the data when log out
                            //gloClass.reset();
                            FirebaseAuth.getInstance().signOut();
                            Intent intentLogout = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intentLogout);
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    // create and show the alert dialog
                    AlertDialog dialog = builder.create();
                    dialog.show();

                } else {
                    return true;
                }
//                switch (id) {
//                    case R.id.nav_mealHistory:
//                        Toast.makeText(Homepage.this, "meal History", Toast.LENGTH_SHORT).show();
//                        Intent intentHistory = new Intent(getBaseContext(), MainActivityOption.class);
//                        startActivity(intentHistory);
//                        //fragmentClass = MealHistory.class;
//                        break;
//                    case R.id.nav_History:
//                        Toast.makeText(Homepage.this, "History", Toast.LENGTH_SHORT).show();
//                        Intent intentmealHistory = new Intent(getBaseContext(), CustomeHistory.class);
//                        startActivity(intentmealHistory);
//                        //fragmentClass = MealHistory.class;
//                        break;
//                    case R.id.nav_CommonFoods:
//                        Toast.makeText(Homepage.this, "Common Foods", Toast.LENGTH_SHORT).show();
//                        Intent intentCommonFoods = new Intent(getBaseContext(), CommonFoods.class);
//                        startActivity(intentCommonFoods);
//                        //fragmentClass = MealHistory.class;
//                        break;
//
//                    case R.id.nav_Education:
//                        Toast.makeText(Homepage.this, "Education", Toast.LENGTH_SHORT).show();
//                        Intent educational = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.omegaratio.net/projects/"));
//                        startActivity(educational);
//                        break;
//                    case R.id.nav_TermsAndConditions:
//                        Toast.makeText(Homepage.this, "Terms And Conditions", Toast.LENGTH_SHORT).show();
//                        Intent intentTerms = new Intent(getBaseContext(), TermsAndConditions.class);
//                        startActivity(intentTerms);
//                        break;
//                    case R.id.nav_dashboard:
//                        Toast.makeText(Homepage.this, "Admin Portal", Toast.LENGTH_SHORT).show();
//                        Intent AdminPortal = new Intent(getBaseContext(), AdminPortal.class);
//                        startActivity(AdminPortal);
//                        break;
//                    case R.id.nav_logout:
//                        AlertDialog.Builder builder = new AlertDialog.Builder(Homepage.this);
//                        builder.setTitle("Log Out");
//                        builder.setMessage("Are you sure to log out?");
//                        // add a button
//                        builder.setPositiveButton("Log out", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                // reset the data when log out
//                                //gloClass.reset();
//                                FirebaseAuth.getInstance().signOut();
//                                Intent intentLogout = new Intent(getBaseContext(), MainActivity.class);
//                                startActivity(intentLogout);
//                            }
//                        });
//                        builder.setNegativeButton("Cancel", null);
//                        // create and show the alert dialog
//                        AlertDialog dialog = builder.create();
//                        dialog.show();
//                        break;
//                    default:
//                        return true;
//                }

                //this was used for fragments. Ignore
                try {
                    //page = (Fragment) fragmentClass.newInstance();
                } catch (Exception e) {
                    e.printStackTrace();
                }


                //FragmentManager fragmentManager = getSupportFragmentManager();
                //fragmentManager.beginTransaction().replace(R.id.flContent, page).commit();
                dl.closeDrawer(nv);
                return true;

            }
        });
        final ImageButton profile = findViewById(R.id.profileGender);


        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentProfile = new Intent(getBaseContext(), Profile.class);


                startActivity(intentProfile);
            }
        });

        final Button inputMealBtn = findViewById(R.id.input_meal_button);
        inputMealBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentMealInput = new Intent(getBaseContext(), mealType.class);
                startActivity(intentMealInput);
            }
        });

      /*  View view = findViewById(R.id.view);


        TranslateAnimation translateAni = new TranslateAnimation(
                Animation.RELATIVE_TO_PARENT, -0.5f, Animation.RELATIVE_TO_PARENT,
                0.5f, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 0);


        translateAni.setDuration(1000);


        translateAni.setRepeatCount(1);


        translateAni.setRepeatMode(Animation.REVERSE);


        view.startAnimation(translateAni);*/


    }

    private void checkAdmin() {
        // download user profile from DB
        mDatabase = fBase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    if (dataSnapshot.child("Admin").exists()) {
                        boolean isAdmin = dataSnapshot.child("Admin").getValue(Boolean.class);
                        setvisibleAdmin(isAdmin);
                    }
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void setvisibleAdmin(boolean Admin) {
        if (Admin) {
            nv.getMenu().findItem(R.id.nav_dashboard).setVisible(true);
            isAdmin = true;
        }
    }

    private void getData() {

        mDatabase = fBase.getReference("Meals");
        query = uid + "_" + todayDate;
        Query query = mDatabase.orderByKey().startAt(this.query);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        String date = snapshot.child("mealDate").getValue(String.class);
                        String uidfromDB = snapshot.child("uID").getValue(String.class);
                        if (uidfromDB.equals(uid)) {
                            datefromDB = date;
                            TodayEqualToday = todayDate.compareTo(datefromDB); // 0
                            meal = snapshot.getValue(MealReference.class);
                            if (TodayEqualToday == 0) {
                                float tempRatio3 = oRatio.get(0);
                                oRatio.set(0, tempRatio3 + meal.getOmega3Total());
                                float tempRatio6 = oRatio.get(1);
                                oRatio.set(1, tempRatio6 + meal.getOmega6Total());
                            }
                        }
                    }
                    drawData();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void drawData() {
        float o3 = oRatio.get(0);
        float o6 = oRatio.get(1);
        String So6 = String.format("%1$,.3f", (o6));
        String So3 = String.format("%1$,.3f", (o3));
        o6 = Float.valueOf(So6);
        o3 = Float.valueOf(So3);
        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(o3, "Omega 3"));
        entries.add(new PieEntry(o6, "Omega 6"));
        PieDataSet pieDataSet = new PieDataSet(entries, "");
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieDataSet.setValueTextSize(18f);
//        pieDataSet.setDrawValues(false);
//        Legend leg = pieChart.getLegend();
//        leg.setEnabled(true);
        pieChart.setDescription(null);
        PieData pieData = new PieData(pieDataSet);
        String ratioLabel = calculate(o3, o6);
        pieChart.setCenterText(ratioLabel);
        pieChart.setCenterTextSize(35f);
        pieChart.setDrawEntryLabels(false);
        pieChart.setData(pieData);
        pieChart.invalidate();

        //Progress bar illustration
        omega6Weight.setText(So6);
        omega3Weight.setText(So3);
        o6progress.setProgress((int) (Math.ceil(o6)));
        o3progress.setProgress((int) (Math.ceil(o3)));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {

    }

    public String calculate(float omega3, float omega6) {
        double om3 = omega3;
        double om6 = omega6;
        double complexRatio;
        int simpleRatio = 0;
        StringBuilder builder = new StringBuilder();

        int roundedO6 = (int) Math.round(om6);
        int roundedO3 = (int) Math.round(om3);
        if (roundedO6 > roundedO3) {
            try {
                complexRatio = om6 / om3;
                simpleRatio = (int) Math.round(complexRatio);
                builder.append(simpleRatio + " : 1");
            } catch (ArithmeticException e) {
                builder.append(simpleRatio + ": 0");
            }
        } else {
            try {
                complexRatio = om3 / om6;
                simpleRatio = (int) Math.ceil(complexRatio);
                builder.append("1: " + simpleRatio);
            } catch (ArithmeticException e) {
                builder.append("0 : " + simpleRatio);
            }
        }

        return builder.toString();
    }

}