package com.example.omegar;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.omegar.UserModel.UserModel;
import com.example.omegar.UserModel.UserModelListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

public class LoginRegisterUserTest implements UserModelListener {

    private static final int SUCCESS = 1;
    private static final int FAILURE = -1;
    private static final int UNDEF = 0;

    private Task<AuthResult> successTask;
    private Task<AuthResult> failureTask;
    private Exception failureTask1;

    @Mock
    private FirebaseAuth mAuth;

    private UserModel UserModel;

    private int logInResult;
    private int signUpResult;

    // Firebase unit test setup
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        successTask = new Task<AuthResult>() {
            @Override
            public boolean isComplete() {
                return true;
            }

            @Override
            public boolean isSuccessful() {
                return true;
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Nullable
            @Override
            public AuthResult getResult() {
                return null;
            }

            @Nullable
            @Override
            public <X extends Throwable> AuthResult getResult(@NonNull Class<X> aClass) throws X {
                return null;
            }

            @Nullable
            @Override
            public Exception getException() {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<AuthResult> onCompleteListener) {
                onCompleteListener.onComplete(successTask);
                return successTask;
            }
        };
        failureTask = new Task<AuthResult>() {
            @Override
            public boolean isComplete() {
                return true;
            }

            @Override
            public boolean isSuccessful() {
                return false;
            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Nullable
            @Override
            public AuthResult getResult() {
                return null;
            }

            @Nullable
            @Override
            public <X extends Throwable> AuthResult getResult(@NonNull Class<X> aClass) throws X {
                return null;
            }

            @Nullable
            @Override
            public Exception getException() {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(@NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super AuthResult> onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task<AuthResult> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<AuthResult> onCompleteListener) {
                onCompleteListener.onComplete(failureTask);
                return failureTask;
            }
        };

        UserModel = new UserModel(mAuth, this);

        logInResult = UNDEF;
        signUpResult = UNDEF;
    }

    /*
    Testing: When correct email/password combo is entered to login, a user is logged in
    Pass Criteria: login task returns success
     */
    @Test
    public void logInSuccess_test() {
        String email = "cool@cool.com";
        String password = "123456";

        Mockito.when(mAuth.signInWithEmailAndPassword(email, password)).thenReturn(successTask);

        UserModel.logIn(email, password);

        assert (logInResult == SUCCESS);
    }

    /*
    Testing: When incorrect email/password combo is entered to login, a user is not logged in
    Pass Criteria: login task returns failure
     */
    @Test
    public void logInFailure_test() {
        String email = "cool@cool.com";
        String password = "1";
        Mockito.when(mAuth.signInWithEmailAndPassword(email, password)).thenReturn(failureTask);
        UserModel.logIn(email, password);
        assert (logInResult == FAILURE);
    }

    /*
    Testing: When correct email/password combo is entered to sign up, a user is logged in
    Pass Criteria: signup task returns success
     */
    @Test
    public void signUpSuccess_test() {
        String email = "cool2@cool.com";
        String password = "123456";
        String name = "coolName";
        String weight = "";
        String age = "";
        String gender = "";
        String medicalCondition = "";

        Mockito.when(mAuth.createUserWithEmailAndPassword(email, password)).thenReturn(successTask);

        UserModel.signUpUser(email, password, name, weight, age, gender, medicalCondition);

        assert (signUpResult == SUCCESS);
    }

    /*
    Testing: When incorrect email/password combo is entered to sign up, a user is not signed in
    Pass Criteria: signup task returns failure
     */
    @Test
    public void signUpFailure_test() {
        String email = "cool2@cool.com";
        String password = "1";
        String name = "";
        String weight = "";
        String age = "";
        String gender = "";
        String medicalCondition = "";

        Mockito.when(mAuth.createUserWithEmailAndPassword(email, password)).thenReturn(failureTask);

        UserModel.signUpUser(email, password, name, weight, age, gender, medicalCondition);

        assert (signUpResult == FAILURE);
    }

    @Override
    public void logInSuccess(String email, String password) {
        logInResult = SUCCESS;
    }

    @Override
    public void logInFailure(Task exception) {
        logInResult = FAILURE;
    }

    @Override
    public void signUpSuccess(String email, String password, String name, String weight, String age, String gender, String medicalCondition) {
        signUpResult = SUCCESS;

    }

    @Override
    public void signUpFailure(Task exception) {
        signUpResult = FAILURE;
    }
}
