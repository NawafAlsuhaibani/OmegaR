package com.example.omegar;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.omegar.UserModel.UserModel;
import com.example.omegar.UserModel.UserModelListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;

public class login extends AppCompatActivity implements UserModelListener {

    private boolean processingLogin;
    private UserModel UserModel;
    private boolean showPopUp;
    public validate validate;
    boolean emailValid, pwdValid;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    Button login;

    EditText txtEmail, txtPass;

    String[] errorMsg;


    @Override
    public void onStart() {
        super.onStart();
        isloggedin();
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        processingLogin = false;
        variableInit();
        UserModel = new UserModel(mAuth, this);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = getEnteredEmail();
                String password = getEnteredPassword();
                logIn(email, password, true);

            }
        });
        //setting the sign up textview to be clickable and go to Register activity

        TextView signup = findViewById(R.id.signUp);
        String SignUp = "Sign Up";
        SpannableString signupspan = new SpannableString(SignUp);
        ClickableSpan LinktoSignUp = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                Intent registerIntent = new Intent(getBaseContext(), Register.class);
                startActivity(registerIntent);
            }
        };
        signupspan.setSpan(LinktoSignUp, 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup.setText(signupspan);
        signup.setMovementMethod(LinkMovementMethod.getInstance());
    }


    private void variableInit() {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        login = findViewById(R.id.loginbtn);
        txtEmail = findViewById(R.id.userEmail);
        txtPass = findViewById(R.id.userPassword);
        errorMsg = new String[2];
        errorMsg[0] = "Invalid Email\n";
        errorMsg[1] = "Invalid Password\n";
        validate = new validate();
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

    // gets text from the email field entered with the soft keyboard
    private String getEnteredEmail() {
        return txtEmail.getText().toString().trim();
    }

    // gets text from the password field entered with the soft keyboard
    private String getEnteredPassword() {
        return txtPass.getText().toString().trim();
    }

    // takes input from email and password text fields, checks that they are non-empty
    // and attempts to log the user in
    private void logIn(final String email, final String password, final boolean showPopUp) {
        if (processingLogin) return;
        this.processingLogin = true;
        this.showPopUp = showPopUp;
        emailValid = validate.EmailValidator(email);
        pwdValid = validate.PasswordValidator(password);
        if (email.length() == 0 || password.length() == 0 || (emailValid = false) || (pwdValid = false)) {
            if (!emailValid) {
                txtEmail.setError(errorMsg[0]);
            }
            if (!pwdValid) {
                txtPass.setError(errorMsg[1]);
            }
            Toast.makeText(getApplicationContext(), "Email and password must both be non-empty.", Toast.LENGTH_LONG).show();
            this.processingLogin = false;
            login.setText("LOGIN");
            return;
        }
        login.setText("PLEASE WAIT");
        UserModel.logIn(email, password);
    }

    // if login is successful,take user to Homepage
    @Override
    public void logInSuccess(String email, String password) {
        Intent Homepage = new Intent(getBaseContext(), Homepage.class);
        startActivity(Homepage);
        this.processingLogin = false;
    }

    // if failure, show an error message to user
    @Override
    public void logInFailure(final Task task) {
        if (showPopUp) {
            Thread thread = new Thread() {
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            String error = "";
                            String e = ((FirebaseAuthException) task.getException()).getErrorCode();
                            switch (e) {
                                case "ERROR_INVALID_CUSTOM_TOKEN":
                                    error = "The custom token format is incorrect. Please check the documentation";
                                    break;

                                case "ERROR_CUSTOM_TOKEN_MISMATCH":
                                    error = "The custom token corresponds to a different audience";
                                    break;

                                case "ERROR_INVALID_CREDENTIAL":
                                    error = "The supplied auth credential is malformed or has expired";
                                    break;

                                case "ERROR_INVALID_EMAIL":
                                    error = "The email address is badly formatted";
                                    break;

                                case "ERROR_WRONG_PASSWORD":
                                    error = "The password is invalid or the user does not have a password";
                                    break;

                                case "ERROR_USER_MISMATCH":
                                    error = "The supplied credentials do not correspond to the previously signed in user";
                                    break;

                                case "ERROR_REQUIRES_RECENT_LOGIN":
                                    error = "This operation is sensitive and requires recent authentication. Log in again before retrying this request";
                                    break;

                                case "ERROR_ACCOUNT_EXISTS_WITH_DIFFERENT_CREDENTIAL":
                                    error = "An account already exists with the same email address but different sign-in credentials. Sign in using a provider associated with this email address";
                                    break;

                                case "ERROR_EMAIL_ALREADY_IN_USE":
                                    error = "The email address is already in use by another account";
                                    break;

                                case "ERROR_CREDENTIAL_ALREADY_IN_USE":
                                    error = "This credential is already associated with a different user account";
                                    break;

                                case "ERROR_USER_DISABLED":
                                    error = "The user account has been disabled by an administrator";
                                    break;

                                case "ERROR_USER_TOKEN_EXPIRED":
                                    error = "he user\\'s credential is no longer valid. The user must sign in again";
                                    break;

                                case "ERROR_USER_NOT_FOUND":
                                    error = "There is no user record corresponding to this email";
                                    break;

                                case "ERROR_INVALID_USER_TOKEN":
                                    error = "The user\\'s credential is no longer valid. The user must sign in again";
                                    break;

                                case "ERROR_OPERATION_NOT_ALLOWED":
                                    error = "This operation is not allowed. You must enable this service in the console";
                                    break;

                                case "ERROR_WEAK_PASSWORD":
                                    error = "The given password is invalid";
                                    break;
                            }

                            Toast.makeText(getApplicationContext(), "Error: " + error, Toast.LENGTH_LONG).show();
                            login.setText("LOGIN");
                        }
                    });
                }

            };
            thread.start();
        }
        this.processingLogin = false;
    }


    @Override
    public void signUpSuccess(String email, String password, String name, String weight, String age, String gender, String medicalCondition) {

    }

    @Override
    public void signUpFailure(Task exception) {
    }


}