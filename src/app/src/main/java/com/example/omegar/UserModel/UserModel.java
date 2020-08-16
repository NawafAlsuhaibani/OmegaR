package com.example.omegar.UserModel;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import java.util.Observable;
import java.util.concurrent.Executor;

public class UserModel extends Observable implements Executor {
    private final FirebaseAuth mAuth;
    private final UserModelListener observer;

    public UserModel(FirebaseAuth mAuth, UserModelListener observer) {
        this.mAuth = mAuth;
        this.observer = observer;
    }

    public UserModel(UserModelListener observer) {
        this.mAuth = FirebaseAuth.getInstance();
        this.observer = observer;
    }

    // logs in only if successful authentication with Firebase
    public void logIn(final String email, final String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            observer.logInSuccess(email, password);
                        } else {
                            observer.logInFailure(task);
                        }
                    }
                });
    }

    // signs the user up and enters their data into the database
    public void signUpUser(final String email, final String password, final String name, final String weight, final String age, final String gender, final String medicalCondition) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            observer.signUpSuccess(email, password, name, weight, age, gender, medicalCondition);
                        } else
                            observer.signUpFailure(task);
                    }
                });
    }

    @Override
    public void execute(Runnable runnable) {
        new Thread(runnable).start();
    }
}