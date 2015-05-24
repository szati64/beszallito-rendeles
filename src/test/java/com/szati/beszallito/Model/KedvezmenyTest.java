package com.szati.beszallito.Model;

import com.szati.beszallito.Model.Kedvezmeny;
import org.junit.Test;
import static org.junit.Assert.*;

public class KedvezmenyTest {

    @Test
    public void testGetId() {
        Kedvezmeny instance = new Kedvezmeny(1, "test", true);
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetLeiras() {
        Kedvezmeny instance = new Kedvezmeny(1, "test", true);
        String expResult = "test";
        String result = instance.getLeiras();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsAktiv() {
        Kedvezmeny instance = new Kedvezmeny(1, "test", true);
        boolean expResult = true;
        boolean result = instance.isAktiv();
        assertEquals(expResult, result);
    }
    
}
