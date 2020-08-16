package com.example.omegar.report;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.omegar.R;
import com.example.omegar.mealReport;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class StackedBarChartFragment extends Fragment implements ReportGetServiceListener {
    private static final String STACK_1_LABEL = "OMEGA-3";
    private static final String STACK_2_LABEL = "OMEGA-6";
    private static final String STACK_3_LABEL = "TOTAL FAT";
    private static final String SET_LABEL = "";
    private BarChart chart;
    String Graphtype, startOfweek, startOfMonth, todayDate, datefrom, dateTo, TempDate;
    DateFormat df;
    Calendar c;
    ArrayList<String> DatetoIndex = new ArrayList<>();
    int daysdiff;
    int arraySize;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setup();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setup() {
        Graphtype = ((mealReport) getActivity()).getGRAPH();
        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        df = new SimpleDateFormat("yyyy-MM-dd");
        startOfweek = df.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.DATE);
        todayDate = df.format(new Date());
        LocalDate todaydate = LocalDate.now();
        startOfMonth = todaydate.withDayOfMonth(1).toString();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_stackedbarchart, container, false);
        chart = view.findViewById(R.id.fragment_stackedbarchart_chart);
        Graphtype = ((mealReport) getActivity()).getGRAPH();
        setup();
        setupDates();
        try {
            configureChartAppearance();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ReportGetService service = new ReportGetService(this, Graphtype, datefrom, dateTo, arraySize,DatetoIndex);
        service.getReportsStacked();
        return view;
    }

    private void setupDates() {
        switch (Graphtype) {
            case "0":
                arraySize = 3;
                datefrom = todayDate;
                break;
            case "1":
                arraySize = 7;
                datefrom = startOfweek;
                break;
            case "2":
                arraySize = 30;
                datefrom = startOfMonth;
                break;
            case "3":
                daysdiff = 0;
                datefrom = ((mealReport) getActivity()).getFrom();
                dateTo = ((mealReport) getActivity()).getTo();
                TempDate = datefrom;
                try {
                    daysdiff = getdaysdiff(datefrom, dateTo);
                    arraySize = (daysdiff - 1);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private int getdaysdiff(String from, String to) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dateBefore = format.parse(from);
        Date dateAfter = format.parse(to);
        long difference = dateAfter.getTime() - dateBefore.getTime();
        float daysBetween = (difference / (1000 * 60 * 60 * 24));
        return (int) daysBetween + 1;

    }

    private void configureChartAppearance() throws ParseException {
        ArrayList<String> xVals = ConfigureXVals();
        chart.setDrawGridBackground(false);
        chart.setDrawValueAboveBar(false);
        chart.getDescription().setEnabled(false);
        XAxis xAxis = chart.getXAxis();
        xAxis.setGranularity(0f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setDrawGridLines(true);
        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawGridLines(true);
    }

    private ArrayList<String> ConfigureXVals() throws ParseException {
        ArrayList<String> xVals = new ArrayList<String>();
        switch (Graphtype) {
            case "0":
                xVals.add("Breakfast");
                xVals.add("Lunch");
                xVals.add("Dinner");
                break;
            case "1":
                xVals.add("SUN");
                xVals.add("MON");
                xVals.add("TUE");
                xVals.add("WED");
                xVals.add("THU");
                xVals.add("FRI");
                xVals.add("SAT");
                break;
            case "2":
                for (int i = 0; i < 31; i++) {
                    xVals.add(i + "");
                }
                break;
            case "3":
                for (int i = 0; i < daysdiff; i++) {
                    xVals.add(TempDate.substring(5));
                    DatetoIndex.add(TempDate.substring(5));
                    TempDate = adddate(TempDate);
                }
                break;
        }
        return xVals;
    }

    private String adddate(String date) throws ParseException {
        Calendar c = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        c.setTime(df.parse(date));
        c.add(Calendar.DATE, 1);  // number of days to add
        date = df.format(c.getTime());  // dt is now the new date
        return date;
    }

    private void prepareChartData(BarData data) {
        chart.getAxisRight().setEnabled(false);
        chart.setPinchZoom(false);
        data.setValueTextSize(9f);
        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public void reportRetrievalSuccess(ArrayList<BarEntry> values) {
        BarDataSet set1 = new BarDataSet(values, SET_LABEL);
        set1.setColors(ColorTemplate.MATERIAL_COLORS[0], ColorTemplate.MATERIAL_COLORS[1], ColorTemplate.MATERIAL_COLORS[2]);
        set1.setStackLabels(new String[]{
                STACK_1_LABEL,
                STACK_2_LABEL,
                STACK_3_LABEL
        });
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        prepareChartData(data);
    }

    @Override
    public void reportRetrievalFailure(String Exception) {

    }
}