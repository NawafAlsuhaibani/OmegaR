package com.example.omegar;

import com.example.omegar.NonActivityClasses.CommonFoodsCards;
import com.google.android.gms.common.internal.service.Common;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.json.JSONArray;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class MealInput2UnitTest {

    MealInput2 mealinput = mock(MealInput2.class);

    @Test
    public void getfoodnameinput(){
        String foodname = "Chicken";
        int found = mealinput.getfoodnameinput(foodname);
        assertEquals(0,found);
    }

    @Test
    public void readJSON(){
        String filepath = "testable file path";
        int tracker = mealinput.getfoodnameinput(filepath);
        assertEquals(0,tracker);
    }

}