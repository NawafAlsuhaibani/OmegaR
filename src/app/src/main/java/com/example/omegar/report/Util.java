package com.example.omegar.report;

import java.util.Random;

public class Util {
    public float randomFloatBetween(float min, float max) {
        Random r = new Random();
        float random = min + r.nextFloat() * (max - min);
        return random;
    }
}
