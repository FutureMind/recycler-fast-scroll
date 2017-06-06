package com.futuremind.recyclerviewfastscroll;

import org.junit.Test;

import static com.futuremind.recyclerviewfastscroll.Utils.getValueInRange;
import static org.junit.Assert.assertTrue;

public class UtilsTest {

    @Test
    public void getValueInValidRange() {
        assertTrue(getValueInRange(0f, 99f, 100f) == 99f);
        assertTrue(getValueInRange(0f, 99f, -1f) == 0f);
        assertTrue(getValueInRange(-1f, 99f, 0f) == 0f);
        assertTrue(getValueInRange(-99f, -10f, 1f) == -10f);
        assertTrue(getValueInRange(-99f, -10f, -101f) == -99f);
    }

    @Test
    public void getValueInInvalidRange() {
        assertTrue(getValueInRange(0f, -99f, 1f) == 0f);
        assertTrue(getValueInRange(0f, -99f, -1f) == 0f);
        assertTrue(getValueInRange(99f, 0f, 1f) == 99f);
        assertTrue(getValueInRange(99f, 0f, 100f) == 99f);
    }
}
