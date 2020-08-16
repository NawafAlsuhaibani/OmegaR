package com.example.omegar.History;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omegar.NonActivityClasses.MealReference;
import com.example.omegar.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class History extends AppCompatActivity implements HistoryGetServiceListener {

    private RecyclerView recycler_view;
    String startOfweek, startOfMonth, todayDate, Graphtype, date, to;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        init();
        setHistoryType(savedInstanceState);
        HistoryGetService service = new HistoryGetService(this, date, to);
        service.getReports();
        //Define recyclerview
        defineRecyclerview();

    }

    private void defineRecyclerview() {
        recycler_view = findViewById(R.id.recycler_Expand);
        recycler_view.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setHistoryType(Bundle savedInstanceState) {
        Bundle extras = getIntent().getExtras();
        if (savedInstanceState == null) {
            if (extras == null) {
                Graphtype = null;
            } else {
                Graphtype = extras.getString("GRAPH");
            }
        } else {
            Graphtype = (String) savedInstanceState.getSerializable("GRAPH");
        }
        switch (Graphtype) {
            case "0":
                date = todayDate;
                to = todayDate;
                break;
            case "1":
                date = startOfweek;
                to = todayDate;
                break;
            case "2":
                date = startOfMonth;
                to = todayDate;
                break;
            case "3":
                date = extras.getString("from");
                to = extras.getString("to");
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void init() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startOfweek = dateFormat.format(calendar.getTime());
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.DATE);
        todayDate = dateFormat.format(new Date());
        LocalDate LocalDateNow = LocalDate.now();
        startOfMonth = LocalDateNow.withDayOfMonth(1).toString();
    }

    @Override
    public void historyRetrievalSuccess(DataSnapshot DataSnapshot, MealReference[] MealReference) {
        final List<ParentList> Parent = new ArrayList<>();
        for (MealReference mealReference : MealReference) {
            final List<ChildList> Child = new ArrayList<>();
            Child.add(new ChildList("amount: " + mealReference.getXamount() + "g"));
            Child.add(new ChildList("Meal Type: " + mealReference.getMealType()));
            Child.add(new ChildList("Meal Date: " + mealReference.getMealDate()));
            Child.add(new ChildList("Omega 6: " + mealReference.Omega6Total() + "g"));
            Child.add(new ChildList("Omega 3: " + mealReference.Omega3Total() + "g"));
            Parent.add(new ParentList(mealReference.getName(), Child));
            DocExpandableRecyclerAdapter adapter = new DocExpandableRecyclerAdapter(Parent);
            recycler_view.setAdapter(adapter);
        }
    }

    @Override
    public void historyRetrievalFailure(String Exception) {

    }


    public class DocExpandableRecyclerAdapter extends ExpandableRecyclerViewAdapter<MyParentViewHolder, MyChildViewHolder> {


        public DocExpandableRecyclerAdapter(List<ParentList> groups) {
            super(groups);
        }

        @Override
        public MyParentViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_parent, parent, false);
            return new MyParentViewHolder(view);
        }

        @Override
        public MyChildViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_child, parent, false);
            return new MyChildViewHolder(view);
        }

        @Override
        public void onBindChildViewHolder(MyChildViewHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {

            final ChildList childItem = ((ParentList) group).getItems().get(childIndex);
            holder.onBind(childItem.getTitle());
            final String TitleChild = group.getTitle();
            holder.listChild.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast toast = Toast.makeText(getApplicationContext(), TitleChild, Toast.LENGTH_SHORT);
                    toast.show();
                }

            });

        }

        @Override
        public void onBindGroupViewHolder(MyParentViewHolder holder, int flatPosition, final ExpandableGroup group) {
            holder.setParentTitle(group);
            if (group.getItems() == null) {
                holder.listGroup.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast toast = Toast.makeText(getApplicationContext(), group.toString(), Toast.LENGTH_SHORT);
                        toast.show();
                    }
                });

            }
        }
    }
}