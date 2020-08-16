package com.example.omegar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.omegar.NonActivityClasses.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class updateprofile extends AppCompatActivity {

    private FirebaseDatabase fBase = FirebaseDatabase.getInstance();
    private DatabaseReference mDatabase = fBase.getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private EditText newUserName, newUserEmail,newUserAge,newUserWeight,newUserGender,newUserMedicalCondition;
    private Button save;

    //Global Variables
    String email, name, weight, gender, age, medicalCondition;
    int ageNum, weightNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateprofile);

        Button backbtn = findViewById(R.id.back);
        newUserName = findViewById(R.id.profileName);
        newUserEmail = findViewById(R.id.profileEmail);
        newUserAge = findViewById(R.id.profileAge);
        newUserWeight = findViewById(R.id.profileWeight);
        newUserGender = findViewById(R.id.profileGender);
        newUserMedicalCondition = findViewById(R.id.profileMedicalCondition);
        save = findViewById(R.id.save);

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
                    ageNum = checkValue(age);
                    weightNum = checkValue(weight);
                    medicalCondition = dataSnapshot.child("medicalCondition").getValue(String.class);
                    //update ui values
                    newUserEmail.setText(email);
                    newUserName.setText(name);
                    if(ageNum!=0){
                        newUserAge.setText(String.valueOf(ageNum));
                    } else newUserAge.setText(age);
                    if(weightNum!=0){
                        newUserWeight.setText(String.valueOf(weightNum));
                    } else newUserWeight.setText(weight);
                    newUserGender.setText(gender);
                    newUserMedicalCondition.setText(medicalCondition);
                }
            }

            //returns integer value from string, set string to 0 when it is null or empty
            public int checkValue(String x){
                if (x.equals(""))
                    return 0;
                else
                    return Integer.parseInt(x);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateprofile.super.onBackPressed();
            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = newUserName.getText().toString();
                String email = newUserEmail.getText().toString();
                String weight = newUserWeight.getText().toString();
                String age = newUserAge.getText().toString();
                String gender = newUserGender.getText().toString();
                String medicalCondition = newUserMedicalCondition.getText().toString();

                UserProfile userProfile = new UserProfile(name,email,weight,age,gender,medicalCondition);

                mDatabase.setValue(userProfile);

                finish();

            }
        });

    }
}