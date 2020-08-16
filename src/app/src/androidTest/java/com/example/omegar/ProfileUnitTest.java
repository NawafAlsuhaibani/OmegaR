package com.example.omegar;

import android.app.Activity;
import android.app.Instrumentation;

import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.*;

public class ProfileUnitTest {

    @Rule
    public ActivityTestRule<Profile> profileActivityTestRule = new ActivityTestRule<Profile>(Profile.class);

    private Profile pActivity = null;

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(updateprofile.class.getName(),null,false);


    @Before
    public void setUp() throws Exception {

        pActivity = profileActivityTestRule.getActivity();
    }

    @Test
    public void updateButton_leads_to_UpdateProfileActivity(){

        assertNotNull(pActivity.findViewById(R.id.update));

        onView(withId(R.id.update)).perform(click());

        Activity secondActivity = getInstrumentation().waitForMonitorWithTimeout(monitor,5000);

        assertNotNull(secondActivity);

        secondActivity.finish();
    }

    @Test
    public void backButton_clickable(){
        onView(withId(R.id.back)).perform((click()));
    }


    @After
    public void tearDown() throws Exception {
    }
}