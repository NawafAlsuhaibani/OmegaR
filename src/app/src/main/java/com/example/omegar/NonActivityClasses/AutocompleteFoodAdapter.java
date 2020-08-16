package com.example.omegar.NonActivityClasses;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.omegar.R;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class AutocompleteFoodAdapter extends ArrayAdapter<food> {
    //ArrayAdapter to display suggestions for autocomplete view for inputting meal
    private List<food> foodListInFull;
    private ArrayList<food> suggestions;

    public AutocompleteFoodAdapter(@NonNull Context context, @NonNull List<food> foodList) {
        super(context, 0, foodList);
        foodListInFull = new ArrayList<food>(foodList);
    }
    public Filter getFilter(){
        return foodFilter;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(
                    R.layout.autocomplete_view, parent, false
            );
        }

        TextView textViewName = convertView.findViewById(R.id.autoCompleteSuggestions);


        food foodItem = getItem(position);

        if (foodItem != null) {
            textViewName.setText(foodItem.getFood_name());
        }

        return convertView;
    }


    private Filter foodFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            suggestions = new ArrayList<>();
            HashSet<String> set = new HashSet<>();
            if(constraint == null || constraint.length()==0){
                suggestions.addAll(foodListInFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                int count = 0;
                for(food i : foodListInFull){           //compare the user input with the food description from CNF.
                    if(filterPattern.equals(i.getFood_description().toLowerCase().substring(0,i.getFood_description().length()<filterPattern.length()?i.getFood_description().length():filterPattern.length()).trim())){
                        if(set.add(i.getFood_name())){
                            suggestions.add(i);
                            count++;}
                    }
                    if(count > 5)
                        break;
                }
            }
            results.values = suggestions;
            results.count = suggestions.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            addAll((List)results.values);
            notifyDataSetChanged();
        }

        @Override
        public CharSequence convertResultToString(Object resultValue) {
            return ((food)resultValue).getFood_name();
        }
    };

    public ArrayList<food> getSuggestions() {return this.suggestions;}
}
