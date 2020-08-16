package com.example.omegar.AdminPortal;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.omegar.NonActivityClasses.MealReference;
import com.example.omegar.R;

import java.util.ArrayList;


public class MealListAdapter extends ArrayAdapter<MealReference> {

    private static final String TAG = "MealListAdapter";

    private Context mContext;
    private int mResource;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView name;
        TextView mealType;
        TextView mealDate;
        TextView Omega3;
        TextView Omega6;
        TextView Amount;
    }

    public MealListAdapter(Context context, int resource, ArrayList<MealReference> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String name = getItem(position).getName();
        String mealDate = getItem(position).getMealDate();
        String mealType = getItem(position).getMealType();
        float Omega3 = getItem(position).getOmega3Total();
        float Omega6 = getItem(position).getOmega6Total();
        double xamount = getItem(position).getXamount();


        MealReference meal = new MealReference(name, mealDate, mealType, xamount, Omega3, Omega6);


        final View result;

        //ViewHolder object
        ViewHolder holder;


        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.textView1);
            holder.mealType = (TextView) convertView.findViewById(R.id.textView2);
            holder.mealDate = (TextView) convertView.findViewById(R.id.textView3);
            holder.Omega3 = (TextView) convertView.findViewById(R.id.textView4);
            holder.Omega6 = (TextView) convertView.findViewById(R.id.textView5);
            holder.Amount = (TextView) convertView.findViewById(R.id.textView6);

            result = convertView;

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        Animation animation = AnimationUtils.loadAnimation(mContext,
                (position > lastPosition) ? R.anim.load_down_anim : R.anim.load_up_anim);
        result.startAnimation(animation);
        lastPosition = position;

        holder.name.setText(meal.getName());
        holder.mealType.setText(meal.getMealType());
        holder.mealDate.setText(meal.getMealDate());
        holder.Amount.setText("Amount: " + meal.getXamount() + "g");
        holder.Omega3.setText("Omega3: " + meal.getOmega3TotalShowData() + "g");
        holder.Omega6.setText("Omega6: " + meal.getOmega6TotalShowData() + "g");
        return convertView;
    }
}

























