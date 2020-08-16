package com.example.omegar.History;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.omegar.R;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import com.example.omegar.mealReport;

import java.util.Calendar;

public class pickDateHistory extends AppCompatActivity {
    Button btndatefrom, btndateto, GetGraph;
    DatePickerDialog datePickerDialog;
    String from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_date_history);
        btndatefrom = findViewById(R.id.date);
        btndateto = findViewById(R.id.date2);
        GetGraph = findViewById(R.id.GetGraph);
    }

    public void selectdateFrom(View v) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(pickDateHistory.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        btndatefrom.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        String month = "-" + (monthOfYear + 1);
                        String day = "-" + dayOfMonth;
                        if (dayOfMonth < 10)
                            day = "-0" + dayOfMonth;
                        if ((monthOfYear + 1) < 10)
                            month = "-0" + (monthOfYear + 1);
                        from = year + month + day;

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void selectdateTo(View v) {
        // calender class's instance and get current date , month and year from calender
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR); // current year
        int mMonth = c.get(Calendar.MONTH); // current month
        int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
        // date picker dialog
        datePickerDialog = new DatePickerDialog(pickDateHistory.this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // set day of month , month and year value in the edit text
                        btndateto.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                        String month = "-" + (monthOfYear + 1);
                        String day = "-" + dayOfMonth;
                        if (dayOfMonth < 10)
                            day = "-0" + dayOfMonth;
                        if ((monthOfYear + 1) < 10)
                            month = "-0" + (monthOfYear + 1);
                        to = year + month + day;


                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();

    }

    public void getgraph(View v) {
        Intent i = null;
        /*Intent i = new Intent(this, mealReport.class);
        Bundle bundle = new Bundle();
        bundle.putString("from", from);
        bundle.putString("to", to);
        i.putExtras(bundle);
        startActivity(i);*/
        i = new Intent(this, History.class);
        i.putExtra("GRAPH", "3");
        i.putExtra("from", from);
        i.putExtra("to", to);
        if (i != null) startActivity(i);
    }
}