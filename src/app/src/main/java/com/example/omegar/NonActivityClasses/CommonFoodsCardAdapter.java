package com.example.omegar.NonActivityClasses;


import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.omegar.CommonFoodsPopUp;
import com.example.omegar.MealInput;
import com.example.omegar.MealInput2;
import com.example.omegar.R;

import java.util.ArrayList;

//
public class CommonFoodsCardAdapter extends RecyclerView.Adapter<CommonFoodsCardAdapter.ArticleHolder> {

    private Context context;
    private ArrayList<CommonFoodsCards> cardArray;

    public CommonFoodsCardAdapter(Context context, ArrayList<CommonFoodsCards> arrayList) {
        this.context = context;
        this.cardArray = arrayList;
    }

    @NonNull
    @Override
    public ArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.common_foods_card_view, parent, false);
        return new ArticleHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleHolder holder, int position) {
        CommonFoodsCards edCard = cardArray.get(position);
        holder.setDetails(edCard);
    }

    @Override
    public int getItemCount() {
        return cardArray.size();
    }

    class ArticleHolder extends RecyclerView.ViewHolder {

        private TextView FoodName, OmegaValue, DataSource;

        ArticleHolder(final View itemView) {
            super(itemView);
            FoodName = itemView.findViewById(R.id.FoodName);
            OmegaValue = itemView.findViewById(R.id.OmegaValue);
            DataSource = itemView.findViewById(R.id.DataSource);

        }

        void setDetails(final CommonFoodsCards edCard) {
            FoodName.setText(edCard.getFoodName());

            OmegaValue.setText(edCard.getOmega() + "g");

            DataSource.setText(edCard.getDataSource());


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DisplayMetrics dm = new DisplayMetrics();
                    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

                    View popupView = LayoutInflater.from(context).inflate(R.layout.activity_common_foods_pop_up, null);
                    final PopupWindow popupWindow = new PopupWindow(popupView, 500, WindowManager.LayoutParams.WRAP_CONTENT);
                    popupWindow.setFocusable(true);

                    Button breakfast = popupView.findViewById(R.id.breakfast2);
                    Button lunch = popupView.findViewById(R.id.lunch2);
                    Button dinner = popupView.findViewById(R.id.dinner2);

                    breakfast.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent meals = new Intent(context.getApplicationContext(), MealInput2.class);
                            meals.putExtra("food_name", edCard.getFoodName());

                            String mealType = "Breakfast";
                            meals.putExtra("mealType", mealType);

                            context.startActivity(meals);
                        }
                    });

                    lunch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent meals = new Intent(context.getApplicationContext(), MealInput2.class);
                            meals.putExtra("food_name", edCard.getFoodName());

                            String mealType = "Lunch";
                            meals.putExtra("mealType", mealType);

                            context.startActivity(meals);
                        }
                    });

                    dinner.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent meals = new Intent(context.getApplicationContext(), MealInput2.class);
                            meals.putExtra("food_name", edCard.getFoodName());

                            String mealType = "Dinner";

                            meals.putExtra("mealType", mealType);

                            context.startActivity(meals);
                        }
                    });

                    popupWindow.showAtLocation(v, Gravity.CENTER, 0, 0);
                }
            });
        }

        public void showPopup(View view) {
            DisplayMetrics dm = new DisplayMetrics();
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

            View popupView = LayoutInflater.from(context).inflate(R.layout.activity_common_foods_pop_up, null);
            final PopupWindow popupWindow = new PopupWindow(popupView, 500, WindowManager.LayoutParams.WRAP_CONTENT);

            Button breakfast = popupView.findViewById(R.id.breakfast2);
            Button lunch = popupView.findViewById(R.id.lunch2);
            Button dinner = popupView.findViewById(R.id.dinner2);

            breakfast.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        }
    }
}
