
package com.example.omegar.report;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.omegar.R;
import com.example.omegar.mealReport;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class MainActivityOption extends AppCompatActivity implements OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main_report);

        setTitle("OmegaR");

        // initialize the utilities
        Utils.init(this);

        ArrayList<ContentItem> objects = new ArrayList<>();

        ////
        objects.add(0, new ContentItem("Omega Reports"));
        objects.add(1, new ContentItem("Daily", "View your daily Intake of Omega"));
        objects.add(2, new ContentItem("Weekly", "See how much Omega you consumed during the week"));
        objects.add(3, new ContentItem("Monthly", "Last month intake of Omega"));
        objects.add(4, new ContentItem("Custom", "Choose specif dates"));

        MyAdapter adapter = new MyAdapter(this, objects);

        ListView lv = findViewById(R.id.listView1);
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> av, View v, int pos, long arg3) {

        Intent i = null;

        switch (pos) {
            case 1:
                i = new Intent(this, mealReport.class);
                i.putExtra("GRAPH", "0");
                break;
            case 2:
                i = new Intent(this, mealReport.class);
                i.putExtra("GRAPH", "1");
                break;
            case 3:
                i = new Intent(this, mealReport.class);
                i.putExtra("GRAPH", "2");
                break;
            case 4:
                i = new Intent(this, CustomeDatesGraph.class);
                break;


        }

        if (i != null) startActivity(i);

        // overridePendingTransition(R.anim.move_right_in_activity, R.anim.move_left_out_activity);
    }
}
