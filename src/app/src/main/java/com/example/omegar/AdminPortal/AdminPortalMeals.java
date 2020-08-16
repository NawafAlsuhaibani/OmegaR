package com.example.omegar.AdminPortal;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;

import com.example.omegar.AdminPortal.listviewitems.BarChartItem;
import com.example.omegar.AdminPortal.listviewitems.ChartItem;
import com.example.omegar.AdminPortal.listviewitems.LineChartItem;
import com.example.omegar.NonActivityClasses.MealReference;
import com.example.omegar.R;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class AdminPortalMeals extends DemoBase implements AdminGetServiceListener {
    ArrayList<ChartItem> list = new ArrayList<>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_listview_chart);
        lv = findViewById(R.id.listView1);
        AdminGetService ADG = new AdminGetService(this);
        ADG.getData();

    }

    @Override
    public void generateDataLine(ArrayList<Entry> values, ArrayList<String> values1) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < values.size(); i++) {
            sb.append(i + "= " + values.get(i) + "\n");
        }
        //System.out.println(sb.toString());
        LineDataSet d1 = new LineDataSet(values, "Number of meals per each hour of day");
        d1.setLineWidth(2.5f);
        d1.setCircleRadius(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(true);
        d1.setValueTextSize(9f);
        ArrayList<ILineDataSet> sets = new ArrayList<>();
        sets.add(d1);
        LineData LineData = new LineData(sets);
        list.add(new LineChartItem(LineData, getApplicationContext(), values1));
    }

    @Override
    public void generateDataPie(ArrayList<PieEntry> values) {
//        PieDataSet d = new PieDataSet(values, "");
//        d.setSliceSpace(2f);
//        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
//        PieData PieData = new PieData(d);
//        list.add(new PieChartItem(PieData, getApplicationContext()));
    }

    @Override
    public void generateDataBar(ArrayList<BarEntry> values, ArrayList<String> values1) {
        BarDataSet d = new BarDataSet(values, "Omega Ratio per each hour of day");
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);
        d.setValueTextSize(9f);
        BarData cd = new BarData(d);
        cd.setBarWidth(0.9f);
        list.add(new BarChartItem(cd, getApplicationContext(), values1));

    }

    @Override
    public void reportRetrievalFailure(String Exception) {

    }

    @Override
    public void AddCharts() {
        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);

    }

    @Override
    public void getShowdata(ArrayList<MealReference> MR) {

    }

    /**
     * adapter that supports 3 different item types
     */
    private class ChartDataAdapter extends ArrayAdapter<ChartItem> {

        ChartDataAdapter(Context context, List<ChartItem> objects) {
            super(context, 0, objects);
        }

        @NonNull
        @Override
        public View getView(int position, View convertView, @NonNull ViewGroup parent) {
            //noinspection ConstantConditions
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            ChartItem ci = getItem(position);
            return ci != null ? ci.getItemType() : 0;
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }

}
