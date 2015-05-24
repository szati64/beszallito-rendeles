package com.szati.beszallito.Model;

import com.szati.beszallito.Model.Termek;
import com.szati.beszallito.Model.RendeltTermek;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class RendeltTermekTest {
    
    Termek testTermek;
    
    @Before
    public void setUp() {
        testTermek = new Termek(1, "TEST", 10, 30, 40, 2, 3, true, true);
    }
    
    @Test
    public void testSetAr() {
        int ar = 99;
        RendeltTermek instance = new RendeltTermek(testTermek, 200, 10);
        instance.setAr(ar);
        assertEquals(instance.getAr(), ar);
    }

    @Test
    public void testSetDb() {
        int db = 20;
        RendeltTermek instance = new RendeltTermek(testTermek, 200, 10);
        instance.setDb(db);
        assertEquals(instance.getDb(), db);
    }

    @Test
    public void testGetTermek() {
        RendeltTermek instance = new RendeltTermek(testTermek, 200, 10);
        Termek expResult = testTermek;
        Termek result = instance.getTermek();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetTermek2() {
        RendeltTermek instance = new RendeltTermek(testTermek, 5);
        Termek expResult = testTermek;
        Termek result = instance.getTermek();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAr() {
        RendeltTermek instance = new RendeltTermek(testTermek, 200, 10);
        int expResult = 200;
        int result = instance.getAr();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAr2() {
        RendeltTermek instance = new RendeltTermek(testTermek, 5);
        int expResult = 0;
        int result = instance.getAr();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetDb() {
        RendeltTermek instance = new RendeltTermek(testTermek, 200, 10);
        int expResult = 10;
        int result = instance.getDb();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetDb2() {
        RendeltTermek instance = new RendeltTermek(testTermek, 5);
        int expResult = 5;
        int result = instance.getDb();
        assertEquals(expResult, result);
    }
    
}
