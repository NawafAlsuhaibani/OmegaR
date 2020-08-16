package com.example.omegar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import android.widget.TextView;

import android.widget.RadioButton;

import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.omegar.NonActivityClasses.UserProfile;
import com.example.omegar.UserModel.UserModel;
import com.example.omegar.UserModel.UserModelListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jsoup.helper.StringUtil;


public class Register extends AppCompatActivity implements UserModelListener {
    public validate validate;
    private boolean processingSignUp;
    private UserModel UserModel;
    boolean emailValid, nameValid, samePWD, noPWD, noPWDcofirm, checked, weightValid, ageValid, genderValid;
    EditText nameField, emailField, pwdField, pwdConfField, weightField, ageField, medicalConditionField;

    CheckBox termsBox;
    TextView terms;

    RadioButton radioButtonMale, radioButtonFemale;

    String Terms, gender, email, name, password, pwdConf, weight, age, medicalCondition;
    Button register;
    String[] errorMsg;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private FirebaseDatabase fBase;
    private DatabaseReference mDatabase;


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and UI accordingly.
        isloggedin();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register = findViewById(R.id.register);
        processingSignUp = false;
        variableInit();
        UserModel = new UserModel(mAuth, this);

        SpannableString termsspan = new SpannableString(Terms);
        ClickableSpan LinktoTerms = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent TermsIntent = new Intent(getBaseContext(), TermsAndConditions.class);
                startActivity(TermsIntent);
            }
        };
        termsspan.setSpan(LinktoTerms, 13, 33, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        terms.setText(termsspan);
        terms.setMovementMethod(LinkMovementMethod.getInstance());
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser();
            }
        });
    }

    private void signUpUser() {
        email = getEnteredEmail();
        password = getEnteredPassword();
        name = getEnteredname();
        gender = getEnteredGender();
        weight = getEnteredweight();
        age = getEnteredAge();
        medicalCondition = getEnteredMedicalCondition();
        pwdConf = pwdConfField.getText().toString().trim();
        checked = termsBox.isChecked();
        // if already processing, just return and let the other process finish
        if (processingSignUp) return;
        processingSignUp = true;
        // verifies that users don't sign up with blank fields
        if (email.length() == 0 || password.length() == 0) {
            Toast.makeText(getApplicationContext(),
                    "Email and password must both be non-empty.",
                    Toast.LENGTH_LONG).show();
            processingSignUp = false;
            register.setText("Register");
            return;
        }
        if (valid(email, name, password, pwdConf, checked, gender, weight, age)) {
            register.setText("PLEASE WAIT");
            UserModel.signUpUser(email, password, name, weight, age, gender, medicalCondition);
        } else {
            processingSignUp = false;
            register.setText("Register");
            return;
        }
    }


    private void isloggedin() {
        if (currentUser != null) {
            if (!currentUser.getUid().isEmpty()) {
                Intent Homepage = new Intent(getBaseContext(), Homepage.class);
                startActivity(Homepage);
                finish();
            }
        }
    }

    private void variableInit() {
        validate = new validate();
        fBase = FirebaseDatabase.getInstance();
        mDatabase = fBase.getReference();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        register = findViewById(R.id.register);
        termsBox = findViewById((R.id.checkedTextView));
        errorMsg = new String[9];
        errorMsg[0] = "Invalid Name\n";
        errorMsg[1] = "Invalid Email\n";
        errorMsg[2] = "Invalid Password\n";
        errorMsg[3] = "Passwords Do Not Match\n";
        errorMsg[4] = "Please Re-enter password\n";
        errorMsg[5] = "Please Agree to Terms and Conditions";
        errorMsg[6] = "Please enter numeric value for age";
        errorMsg[7] = "Please enter numeric value for weight";
        errorMsg[8] = "Check either male or female";
        nameField = findViewById(R.id.registerName);
        emailField = findViewById(R.id.registerEmail);
        pwdField = findViewById(R.id.registerPwd);
        pwdConfField = findViewById(R.id.registerPwdConfirm);
        ageField = findViewById(R.id.registerAge);
        weightField = findViewById(R.id.registerWeight);
        medicalConditionField = findViewById(R.id.registerMedicalCondition);

        terms = findViewById((R.id.termsText));

        radioButtonMale = findViewById(R.id.radioFemale);
        radioButtonFemale = findViewById(R.id.radioFemale);

        Terms = "I accept the terms and conditions";
    }

    public boolean valid(String email, String name, String pwd, String pwdConf, boolean checked, String gender, String weight, String age) {
        emailValid = validate.EmailValidator(email);
        nameValid = validate.NameValidator(name);
        samePWD = pwdConf.equals(pwd);
        noPWD = pwd.isEmpty();
        noPWDcofirm = pwdConf.isEmpty();
        weightValid = StringUtil.isNumeric(weight);
        ageValid = StringUtil.isNumeric(age);
        genderValid = gender.equals("0");

        if (!emailValid) {
            emailField.setError(errorMsg[1]);
            return false;
        }
        if (!nameValid) {
            nameField.setError(errorMsg[0]);
            return false;
        }
        if (noPWDcofirm) {
            pwdConfField.setError(errorMsg[4]);
            return false;
        }
        if (!samePWD) {
            pwdConfField.setError(errorMsg[3]);
            return false;
        }
        if (noPWD) {
            pwdField.setError(errorMsg[2]);
            return false;
        }
        if (!checked) {
            toast(errorMsg[5]);
            return false;
        }
        if (!ageValid) {
            ageField.setError(errorMsg[6]);
            return false;
        }
        if (!weightValid) {
            weightField.setError(errorMsg[7]);
            return false;
        }
        if (genderValid) {
            radioButtonMale.setError(errorMsg[8]);
            radioButtonFemale.setError(errorMsg[8]);
            return false;
        }
        return true;
    }

    private void toast(String err) {
        Toast.makeText(Register.this, err, Toast.LENGTH_LONG).show();

    }


    // gets text from the email field entered with the soft keyboard
    private String getEnteredEmail() {
        return emailField.getText().toString().trim();
    }

    // gets text from the password field entered with the soft keyboard
    private String getEnteredPassword() {
        return pwdField.getText().toString().trim();
    }

    // gets text from the age field entered with the soft keyboard
    private String getEnteredAge() {
        return ageField.getText().toString().trim();
    }

    // gets text from the name field entered with the soft keyboard
    private String getEnteredname() {
        return nameField.getText().toString().trim();
    }

    // gets text from the age field entered with the soft keyboard
    private String getEnteredweight() {
        return weightField.getText().toString().trim();
    }

    // gets text from the age field entered with the soft keyboard  pwdConf
    private String getEnteredGender() {
        if (radioButtonMale.isChecked())
            return "Male";
        else if (radioButtonFemale.isChecked())
            return "Female";
        else return "0";
    }

    // gets text from the age field entered with the soft keyboard
    private String getEnteredMedicalCondition() {
        return medicalConditionField.getText().toString().trim();
    }

    @Override
    public void logInSuccess(String email, String password) {

    }

    @Override
    public void logInFailure(Task exception) {

    }

    @Override
    public void signUpSuccess(String email, String password, String name, String weight, String age, String gender, String medicalCondition) {
        UserProfile newUser = new UserProfile(name, email, weight, age, gender, medicalCondition);
        mDatabase = fBase.getReference().child("Users").child(mAuth.getCurrentUser().getUid());
        mDatabase.setValue(newUser);
        Intent Homepage = new Intent(getBaseContext(), Homepage.class);
        startActivity(Homepage);
        processingSignUp = false;
    }

    @Override
    public void signUpFailure(final Task exception) {
        Thread thread = new Thread() {
            public void run() {
                runOnUiThread(new Runnable() {
                    public void run() {
                        String error = "";
                        if (exception.getException() instanceof FirebaseAuthWeakPasswordException) {
                            error = "Weak Password, please try again";
                        } else if (exception.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                            error = "Invalid email, please try again";
                        } else if (exception.getException() instanceof FirebaseAuthUserCollisionException) {
                            error = "User with this email already exist";
                        } else {
                            error = "Something happened, please try again";
                        }
                        Toast.makeText(getApplicationContext(), "Failed to sign up. Error: " + error, Toast.LENGTH_LONG).show();
                        register.setText("Register");
                    }
                });
            }

        };
        thread.start();
    }
}

