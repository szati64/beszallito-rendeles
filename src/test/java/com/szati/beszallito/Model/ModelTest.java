package com.szati.beszallito.Model;

import com.szati.beszallito.Model.Termek;
import com.szati.beszallito.Model.Model;
import com.szati.beszallito.Model.Kedvezmeny;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

public class ModelTest {

    List<Termek> termekek;
    List<Kedvezmeny> kedvezmenyek;
    Map<Integer, String> kategoriak;
    Map<Integer, String> markak;
    
    Termek termek1;
    Kedvezmeny kedv1;
    Kedvezmeny kedv2;
    
    @Before
    public void setUp() {
        termek1 = new Termek(1, "TEST", 5, 100, 70, 1, 2, true, true);
        termekek = new ArrayList<>();
        termekek.add(termek1);
        
        kedvezmenyek = new ArrayList<>(); 
        kedv1 = new Kedvezmeny(1, "TEST KEDVEZMENY", true);
        kedv2 = new Kedvezmeny(2, "TEST KEDVEZMENY2", false);
        
        kedvezmenyek.add(kedv2);
        kedvezmenyek.add(kedv1);
        
        kategoriak = new HashMap<>();        
        markak = new HashMap<>();
    }
    
    @Test
    public void testGetTermekById1() {
        int termekId = 1;
        Model instance = new Model();
        instance.setTermekek(termekek);
        
        Termek expResult = termek1;
        Termek result = instance.getTermekById(termekId);
        assertEquals(expResult, result);
        assertEquals(null, instance.getKedvezmenyById(0));
    }

    @Test
    public void testGetTermekById2() {
        int termekId = 0;
        Model instance = new Model();
        instance.setTermekek(termekek);
        
        Termek expResult = null;
        Termek result = instance.getTermekById(termekId);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKedvezmenyById1() {
        int kedvezmenyId = 1;
        Model instance = new Model();
        instance.setKedvezmenyek(kedvezmenyek);
        
        Kedvezmeny expResult = kedv1;
        Kedvezmeny result = instance.getKedvezmenyById(kedvezmenyId);
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKedvezmenyById2() {
        int kedvezmenyId = 0;
        Model instance = new Model();
        instance.setKedvezmenyek(kedvezmenyek);
        
        Kedvezmeny expResult = null;
        Kedvezmeny result = instance.getKedvezmenyById(kedvezmenyId);
        assertEquals(expResult, result);
    }

    @Test
    public void testAddUjKedvezmeny1() {
        List<AbstractMap.SimpleEntry<Integer, Integer>> kedvezmenyekList = 
                new ArrayList<>();
        int kedvezmenyId = 1;
        int kedvezmenyAr = 10;
        Model instance = new Model();
        instance.addUjKedvezmeny(kedvezmenyekList, kedvezmenyId, kedvezmenyAr);
        
        assertEquals(kedvezmenyekList.size(), 1);
    }
    
    @Test
    public void testAddUjKedvezmeny2() {
        List<AbstractMap.SimpleEntry<Integer, Integer>> kedvezmenyekList = 
                new ArrayList<>();
        int kedvezmenyId = 1;
        int kedvezmenyAr = 10;
        Model instance = new Model();
        instance.addUjKedvezmeny(kedvezmenyekList, kedvezmenyId, kedvezmenyAr);
        instance.addUjKedvezmeny(kedvezmenyekList, kedvezmenyId, kedvezmenyAr);
        
        assertEquals((int)kedvezmenyekList.get(0).getValue(), 20);
    }

    @Test
    public void testSetTermekek() {
        Model instance = new Model();
        instance.setTermekek(termekek);
        
        assertEquals(termekek, instance.getTermekek());
    }

    @Test
    public void testSetMarkak() {
        Model instance = new Model();
        instance.setMarkak(markak);
        
        assertEquals(markak, instance.getMarkak());
    }

    @Test
    public void testSetKategoriak() {
        Model instance = new Model();
        instance.setKategoriak(kategoriak);
        
        assertEquals(kategoriak, instance.getKategoriak());
    }

    @Test
    public void testSetKedvezmenyek() {
        Model instance = new Model();
        instance.setKedvezmenyek(kedvezmenyek);
        
        assertEquals(kedvezmenyek, instance.getKedvezmenyek());
    }

    @Test
    public void testGetTermekek() {
        Model instance = new Model();
        instance.setTermekek(termekek);
        
        assertEquals(termekek, instance.getTermekek());
    }

    @Test
    public void testGetMarkak() {
        Model instance = new Model();
        instance.setMarkak(markak);
        
        assertEquals(markak, instance.getMarkak());
    }

    @Test
    public void testGetKategoriak() {
        Model instance = new Model();
        instance.setKategoriak(kategoriak);
        
        assertEquals(kategoriak, instance.getKategoriak());
    }

    @Test
    public void testGetKedvezmenyek() {
        Model instance = new Model();
        instance.setKedvezmenyek(kedvezmenyek);
        
        assertEquals(kedvezmenyek, instance.getKedvezmenyek());
    }

    @Test
    public void testGetKategoriaIdByNev() {
        Model instance = new Model();
        instance.setKategoriak(kategoriak);
        
        int expResult = -1;
        int result = instance.getKategoriaIdByNev("nem teszt");
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetMarkaIdByNev() {
        Model instance = new Model();
        instance.setMarkak(markak);
        
        int expResult = -1;
        int result = instance.getMarkaIdByNev("nem teszt");
        assertEquals(expResult, result);
    }
}
