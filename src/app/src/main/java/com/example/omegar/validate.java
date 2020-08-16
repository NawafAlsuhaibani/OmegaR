package com.example.omegar;

import android.util.Patterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class validate {

    public boolean EmailValidator(String target) {
        String regex = "^(.+)@(.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.matches();
    }

    public boolean NameValidator(String _Name) {
        Pattern pattern;
        Matcher matcher;
        final String Name = "^[a-zA-z ]*$";
        pattern = Pattern.compile(Name);
        matcher = pattern.matcher(_Name);
        return matcher.matches();
    }

    public boolean PasswordValidator(String Password) {
        return Password.length() >= 5;
    }

}
