package com.example.omegar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Profile extends AppCompatActivity {
    private FirebaseDatabase fBase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = fBase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private TextView userEmail;
    private TextView userAge;
    private TextView userWeight;
    private TextView userGender;
    private TextView userName;
    private TextView userMedicalCondition;

    //Global Variables
    String email, name, weight, gender, age, medicalCondition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Button backbtn = findViewById(R.id.back);
        Button updatebtn = findViewById(R.id.update);



        userName = findViewById(R.id.profileName);
        userEmail = findViewById(R.id.profileEmail);//using profilePhone textView to display password for now.
        userAge = findViewById(R.id.profileAge);
        userWeight = findViewById(R.id.profileWeight);
        userMedicalCondition = findViewById(R.id.profileMedicalCondition);
        userGender = findViewById(R.id.profileGender);

        // download user profile from DB
        mDatabase = fBase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    //retrieve values from User profile
                    email = dataSnapshot.child("email").getValue(String.class);
                    name = dataSnapshot.child("name").getValue(String.class);
                    weight = dataSnapshot.child("weight").getValue(String.class);
                    gender = dataSnapshot.child("gender").getValue(String.class);
                    age = dataSnapshot.child("age").getValue(String.class);
                    medicalCondition = dataSnapshot.child("medicalCondition").getValue(String.class);
                    //update ui values
                    userEmail.setText(email);
                    userName.setText(name);
                    userAge.setText(age);
                    userWeight.setText(weight);
                    userGender.setText(gender);
                    userMedicalCondition.setText(medicalCondition);
                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Profile.super.onBackPressed();
            }
        });

        updatebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Profile.this,updateprofile.class));
            }
        });
    }


}
