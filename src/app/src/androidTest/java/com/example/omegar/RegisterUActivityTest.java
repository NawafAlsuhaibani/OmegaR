package com.example.omegar;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class RegisterUActivityTest {

    @Rule
    public ActivityTestRule<Register> RegisterTestRule = new ActivityTestRule<>(Register.class);
    private Register register = null;

    @Before
    public void setUp() {
        register = RegisterTestRule.getActivity();
    }


    @Test
    public void registerNameTest() {

        onView(withId(R.id.registerName)).perform(typeText("test1"));

        onView(withId(R.id.registerName)).check(ViewAssertions.matches(withText("test1")));
    }

    @Test
    public void registerEmailTest() {
        onView(withId(R.id.registerEmail)).perform(typeText("test2@espresso.test"));
    }

    @Test
    public void registerPwdTest() {
        onView(withId(R.id.registerPwd)).perform(typeText("test123123"), closeSoftKeyboard());
    }

    @Test
    public void registerPwdConfTest() {
        onView(withId(R.id.registerPwdConfirm)).perform(typeText("test123123"), closeSoftKeyboard());
    }

    @Test
    public void registerWeightTest() {
        onView(withId(R.id.registerWeight)).perform(replaceText("65"), closeSoftKeyboard());
    }

    @Test
    public void registerAgeTest() {
        onView(withId(R.id.registerAge)).perform(typeText("30"), closeSoftKeyboard());
    }

    @Test
    public void registerGenderTest() {
        onView(withText("Male")).perform(click());
    }

    @Test
    public void registerTermsTest() {
        onView(withId(R.id.checkedTextView)).perform(longClick());
    }

    @Test
    public void registerPerformTest() {
        onView(withId(R.id.register)).perform(click());
    }


}
