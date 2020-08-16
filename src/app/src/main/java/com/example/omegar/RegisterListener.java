package com.example.omegar;

public interface RegisterListener {
    void RegisterSuccess(String email, String password);
    void RegisterFailure(Exception exception, String email, String password);
}
