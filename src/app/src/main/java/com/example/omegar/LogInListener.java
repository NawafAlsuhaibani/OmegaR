package com.example.omegar;

public interface LogInListener {
    void logInSuccess(String email, String password);
    void logInFailure(Exception exception, String email, String password);
}
