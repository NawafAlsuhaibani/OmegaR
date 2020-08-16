package com.example.omegar.UserModel;

import com.google.android.gms.tasks.Task;

public interface UserModelListener {
    void logInSuccess(String email, String password);

    void logInFailure(Task exception);

    void signUpSuccess(String email, String password, String name, String weight, String age, String gender, String medicalCondition);

    void signUpFailure(Task exception);
}
