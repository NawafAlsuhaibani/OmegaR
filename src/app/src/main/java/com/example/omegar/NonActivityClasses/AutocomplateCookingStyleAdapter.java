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

import static java.util.Collections.addAll;

public class AutocomplateCookingStyleAdapter extends ArrayAdapter<food>{
    //ArrayAdapter to display suggestions for autocomplete view for inputting meal
    private List<food> cookingStyleInFull;

    public AutocomplateCookingStyleAdapter(@NonNull Context context, @NonNull List<food> foodList) {
        super(context, 0, foodList);
        cookingStyleInFull = new ArrayList<food>();
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
            textViewName.setText(foodItem.getFood_CookingStyle());
        }

        return convertView;
    }


    private Filter foodFilter = new Filter(){

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();
            ArrayList<food> suggestions = new ArrayList<>();
            HashSet<String> set = new HashSet<>();
            if(constraint == null || constraint.length()==0){
                suggestions.addAll(cookingStyleInFull);
            }else{
                String filterPattern = constraint.toString().toLowerCase().trim();

                int count = 0;
                for(food i : cookingStyleInFull){
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

}
