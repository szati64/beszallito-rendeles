package com.szati.beszallito.Model;

import com.szati.beszallito.Model.Termek;
import com.szati.beszallito.Model.RendeltTermek;
import com.szati.beszallito.Model.Rendeles;
import java.time.LocalDate;
import java.time.Month;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class RendelesTest {

    Rendeles instance;
    Rendeles instance2;
    List<RendeltTermek> rt;
    List<SimpleEntry<Integer, Integer>> kedv;
    LocalDate testDate;
    Termek testTermek;
    
    @Before
    public void setUp() {
        testTermek = new Termek(1, "TEST", 10, 30, 40, 2, 3, true, true);
        rt = new ArrayList<>();
        rt.add(new RendeltTermek(testTermek, 20, 10));
        
        kedv = new ArrayList<>();
        kedv.add(new SimpleEntry<>(1, 1));
        
        testDate = LocalDate.of(2015, Month.JANUARY, 15);
        
        instance = new Rendeles(1, testDate,
                15000, rt, kedv);
        
        instance2 = new Rendeles(2, testDate, 40000, null, null);
    }
    
    @Test
    public void testGetId() {
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDatum() {
        LocalDate expResult = testDate;
        LocalDate result = instance.getDatum();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAr() {
        int expResult = 15000;
        int result = instance.getAr();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetRendeltTermekek() {
        List<RendeltTermek> expResult = rt;
        List<RendeltTermek> result = instance.getRendeltTermekek();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetRendeltTermekek2() {
        assertNotNull(instance2.getRendeltTermekek());
    }

    @Test
    public void testGetKedvezmenyek() {
        List<SimpleEntry<Integer, Integer>> expResult = kedv;
        List<SimpleEntry<Integer, Integer>> result = instance.getKedvezmenyek();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKedvezmenyek2() {
        assertNotNull(instance2.getKedvezmenyek());
    }
    
}
