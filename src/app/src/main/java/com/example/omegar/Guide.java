package com.example.omegar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.google.firebase.auth.FirebaseAuth;
import com.stx.xhb.androidx.XBanner;
import com.stx.xhb.androidx.entity.LocalImageInfo;

import java.util.ArrayList;
import java.util.List;

public class Guide extends AppCompatActivity {


    private XBanner mXBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);

        mXBanner = findViewById(R.id.xbanner);

        final List<LocalImageInfo> localImageInfoList=new ArrayList<>();
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_1));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_2));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_3));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_4));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_5));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_6));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_7));
        localImageInfoList.add(new LocalImageInfo(R.drawable.gif_8));
        mXBanner.setBannerData(localImageInfoList);

        mXBanner. loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                Glide.with(Guide.this)
                        .load(((LocalImageInfo) model).getXBannerUrl())
                        .into(new GlideDrawableImageViewTarget((ImageView) view,Integer.MAX_VALUE));
            }
        });

        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Guide.this);
                builder.setTitle("Tutorial");
                builder.setMessage("Show tutorial next time?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreferences("isShow",MODE_PRIVATE).edit().putBoolean("isShow",true).commit();
                        startActivity(new Intent(Guide.this,login.class));
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getSharedPreferences("isShow",MODE_PRIVATE).edit().putBoolean("isShow",false).commit();
                        startActivity(new Intent(Guide.this,login.class));
                        finish();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


        final TextView title = findViewById(R.id.title);
        final TextView content = findViewById(R.id.content);

        mXBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position){
                    case 0:
                        title.setText("Register: ");
                        content.setText("Click the button “Sign Up”.  Fill in the username, email, password, weight, age and check the “terms and conditions,” then click the button “Register”.");
                        break;
                    case 1:
                        title.setText("Login: ");
                        content.setText("Type in the user email and password and click the “Login”.");
                        break;
                    case 2:
                        title.setText("View & edit Profile: ");
                        content.setText("Click the personnel icon button and see the account details. Click “update” then click save to change and save user profile.");
                        break;
                    case 3:
                        title.setText("Add Meal & Ration Calculation: ");
                        content.setText("Click the “Add Meal” and choose one then input the food that user consumed and click the “Input Meal”. In the popup window, user can click “Add Another Meal” button or Click “Back” button to go back home page.");
                        break;
                    case 4:
                        title.setText("Report Graph: ");
                        content.setText("Click the burger menu button on the main activity and click the “Meal Report”. user can select which period (daily, weekly, monthly or customized time period) of omega intake they want to review. Click the “Stacked Bar” button to show the stacked bar chart.");
                        break;
                    case 5:
                        title.setText("Meal History: ");
                        content.setText("Click the burger menu button and click the “Meal History”. Click correlated time period button of meal history user want to review. User is able to click each section button to unfold the details about omega 3 and omega 6 and weight of intake.");
                        break;
                    case 6:
                        title.setText("Common Foods: ");
                        content.setText("Click the “Common Foods” button in the menu and click intake foods and click the correlated meal in popup window. And enter the weight of food. Then click “Input Meal”.");
                        break;
                    case 7:
                        title.setText("Education & Terms & Log out: ");
                        content.setText("Click the “Education” button  to view educational sources. Click “Term & conditions” to view terms and conditions. Click “Log out” button to log out current user.");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}
