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

public class VerticalBarChartFragment extends Fragment implements ReportGetServiceListener {
    private static final String SET_LABEL = "Omega Ratio";
    private BarChart chart;
    String Graphtype, startOfweek, startOfMonth, todayDate, datefrom, dateTo, TempDate;
    int daysdiff, arraySize;
    DateFormat df;
    Calendar c;
    ArrayList<String> DatetoIndex = new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_verticalbarchart, container, false);
        chart = view.findViewById(R.id.fragment_verticalbarchart_chart);
        Graphtype = ((mealReport) getActivity()).getGRAPH();
        setup();
        setupDates();
        try {
            configureChartAppearance();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        ReportGetService service = new ReportGetService(this, Graphtype, datefrom, dateTo, arraySize,DatetoIndex);
        service.getReportsVertical();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setup() {
        c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        df = new SimpleDateFormat("yyyy-MM-dd");
        todayDate = df.format(new Date());
        startOfweek = df.format(c.getTime());
        c.set(Calendar.DAY_OF_WEEK, Calendar.DATE);
        LocalDate todaydate = LocalDate.now();
        startOfMonth = todaydate.withDayOfMonth(1).toString();
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
                System.out.println(datefrom+"datefrom");
                dateTo = ((mealReport) getActivity()).getTo();
                System.out.println(dateTo+"dateTo");
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
        XAxis xAxis = chart.getXAxis();
        ArrayList<String> xVals = ConfigureXVals(xAxis);
        chart.getDescription().setEnabled(false);
        chart.setDrawValueAboveBar(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xVals));
        YAxis axisLeft = chart.getAxisLeft();
        axisLeft.setAxisMinimum(0f);
        YAxis axisRight = chart.getAxisRight();
        axisRight.setGranularity(10f);
        axisRight.setAxisMinimum(0f);
        XAxis xLabels = chart.getXAxis();
        xLabels.setPosition(XAxis.XAxisPosition.BOTTOM);
        axisRight.setEnabled(false);
    }

    private ArrayList<String> ConfigureXVals(XAxis xAxis) throws ParseException {
        ArrayList<String> xVals = new ArrayList<String>();
        switch (Graphtype) {
            case "0":
                xVals.add("Breakfast");
                xVals.add("Lunch");
                xVals.add("Dinner");
                xAxis.setLabelCount(3);
                break;
            case "1":
                xVals.add("SUN");
                xVals.add("MON");
                xVals.add("TUE");
                xVals.add("WED");
                xVals.add("THU");
                xVals.add("FRI");
                xVals.add("SAT");
                xAxis.setLabelCount(7);
                break;
            case "2":
                for (int i = 1; i < 31; i++) {
                    xVals.add(i + "");
                    xAxis.setLabelCount(30);
                }
                break;
            case "3":
                for (int i = 1; i < daysdiff + 1; i++) {
                    xVals.add(TempDate.substring(5));
                    DatetoIndex.add(TempDate.substring(5));
                    TempDate = adddate(TempDate);
                }
                xAxis.setLabelCount(daysdiff);
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
        chart.getLegend().setEnabled(false);
        chart.getAxisRight().setEnabled(false);
        data.setValueTextSize(12f);
        chart.setData(data);
        chart.invalidate();
    }

    @Override
    public void reportRetrievalFailure(String Exception) {

    }

    @Override
    public void reportRetrievalSuccess(ArrayList<BarEntry> values) {
        BarDataSet set1 = new BarDataSet(values, SET_LABEL);
        set1.setColors(ColorTemplate.MATERIAL_COLORS[0],
                ColorTemplate.MATERIAL_COLORS[1], ColorTemplate.MATERIAL_COLORS[2], ColorTemplate.PASTEL_COLORS[0],
                ColorTemplate.PASTEL_COLORS[1], ColorTemplate.PASTEL_COLORS[2], ColorTemplate.PASTEL_COLORS[3]);
        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        prepareChartData(data);
    }
}