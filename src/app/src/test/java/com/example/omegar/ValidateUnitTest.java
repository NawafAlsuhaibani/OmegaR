package com.example.omegar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class ValidateUnitTest {
    validate validate = new validate();
    boolean Valid;

    @Test
    public void validateEmail() {
        String Wrongemail = "@ffe.com";
        String Trueemail = "nawafw@fef.com";
        Valid = validate.EmailValidator(Wrongemail);
        assertThat(Valid, is(false));
        Valid = validate.EmailValidator(Trueemail);
        assertThat(Valid, is(true));
    }

    @Test
    public void validateName() {
        String WrongName = "123";
        String TrueName = "nawafw";
        Valid = validate.NameValidator(WrongName);
        assertThat(Valid, is(false));
        Valid = validate.NameValidator(TrueName);
        assertThat(Valid, is(true));
    }

    @Test
    public void validatePass() {
        String WrongPass = "123";
        String TruePass = "12345";
        Valid = validate.PasswordValidator(WrongPass);
        assertThat(Valid, is(false));
        Valid = validate.PasswordValidator(TruePass);
        assertThat(Valid, is(true));
    }

}