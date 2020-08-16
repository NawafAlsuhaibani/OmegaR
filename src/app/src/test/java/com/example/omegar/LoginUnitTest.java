package com.example.omegar;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.concurrent.Executor;

public class LoginUnitTest implements LogInListener {
    Task successTask;
    Task failureTask;
    @Mock
    FirebaseAuth mAuth;
    login login;
    int logInResult;
    static final int SUCCESS = 1;
    static final int FAILURE = -1;

    @Before
    public final void setUp() {
        MockitoAnnotations.initMocks(this);
        this.successTask = (Task) (new Task() {

            @NonNull
            public Task addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener onCompleteListener) {
                onCompleteListener.onComplete(successTask);
                return successTask;
            }

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
            public Object getResult() {
                return null;
            }

            @Nullable
            @Override
            public Exception getException() {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @Nullable
            @Override
            public Object getResult(@NonNull Class aClass) throws Throwable {
                return null;
            }
        });

        this.failureTask = (Task) (new Task() {
            @NonNull
            public Task addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener onCompleteListener) {
                onCompleteListener.onComplete(failureTask);
                return failureTask;
            }


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
            public Object getResult() {
                return null;
            }

            @Nullable
            @Override
            public Exception getException() {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener onSuccessListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @NonNull
            @Override
            public Task addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                return null;
            }

            @Nullable
            @Override
            public Object getResult(@NonNull Class aClass) throws Throwable {
                return null;
            }
        });
        login = new login();
    }

    @Test
    public final void logInSuccess_test() {
        String email = "cool@cool.com";
        String password = "123123";
        Mockito.when(mAuth.signInWithEmailAndPassword(email, password)).thenReturn(successTask);

        logInSuccess(email,password);
        assert (logInResult == SUCCESS);
    }
    @Test
    public final void logInFailure_test() {
        String email = "cool";
        String password = "";
        Mockito.when(mAuth.signInWithEmailAndPassword(email, password)).thenReturn(failureTask);
        logInFailure(failureTask.getException(),email,password);
        assert (logInResult == FAILURE);
    }

    @Override
    public void logInSuccess(String email, String password) {
        logInResult = SUCCESS;

    }

    @Override
    public void logInFailure(Exception exception, String email, String password) {
        logInResult = FAILURE;
    }
}