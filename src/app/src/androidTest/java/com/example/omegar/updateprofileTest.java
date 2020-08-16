package com.example.omegar;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class updateprofileTest {

    @Rule
    public ActivityTestRule<updateprofile> updateprofileActivityTestRule = new ActivityTestRule<updateprofile>(updateprofile.class);

    private updateprofile uActivity = null;

//    Instrumentation.ActivityMonitor uMonitor = getInstrumentation().addMonitor(Profile.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {

        uActivity = updateprofileActivityTestRule.getActivity();
    }

    @Test
    public void user_can_update_name() {
        onView(withId(R.id.profileName)).perform(clearText());
        onView(withId(R.id.profileName)).perform(typeText("Jason"));
    }

    @Test
    public void user_can_update_email() {
        onView(withId(R.id.profileEmail)).perform(clearText());
        onView(withId(R.id.profileEmail)).perform(typeText("ubc@gmail.com"));
    }

    @Test
    public void user_can_update_age() {
        onView(withId(R.id.profileAge)).perform(clearText());
        onView(withId(R.id.profileAge)).perform(typeText("20"));
    }

    @Test
    public void user_can_update_weight() {
        onView(withId(R.id.profileWeight)).perform(clearText());
        onView(withId(R.id.profileWeight)).perform(typeText("20"));
    }

    @Test
    public void user_can_update_gender() {
        onView(withId(R.id.profileGender)).perform(clearText());
        onView(withId(R.id.profileGender)).perform(typeText("male"));
    }

    @Test
    public void user_can_update_medicalcondition() {
        onView(withId(R.id.profileMedicalCondition)).perform(clearText());
        onView(withId(R.id.profileMedicalCondition)).perform(typeText("none"));
    }

    @Test
    public void backButton_clickable(){
        onView(withId(R.id.back)).perform((click()));
    }


    @After
    public void tearDown() throws Exception {

        uActivity = null;
    }
}