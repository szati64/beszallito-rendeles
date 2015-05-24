package com.szati.beszallito.Model;

import com.szati.beszallito.Model.Termek;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author master
 */
public class TermekTest {
    
    Termek instance;
    
    public TermekTest() {
        instance = new Termek(1, "TEST", 10, 40, 30, 1, 2, true, false);
    }

    @Test
    public void testGetId() {
        int expResult = 1;
        int result = instance.getId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetNev() {
        String expResult = "TEST";
        String result = instance.getNev();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetZsugor() {
        int expResult = 10;
        int result = instance.getZsugor();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAr() {
        int expResult = 40;
        int result = instance.getAr();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetAkciosAr() {
        int expResult = 30;
        int result = instance.getAkciosAr();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMarkaId() {
        int expResult = 1;
        int result = instance.getMarkaId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetKategoriaId() {
        int expResult = 2;
        int result = instance.getKategoriaId();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsUj() {
        boolean expResult = true;
        boolean result = instance.isUj();
        assertEquals(expResult, result);
    }

    @Test
    public void testIsAkcios() {
        boolean expResult = false;
        boolean result = instance.isAkcios();
        assertEquals(expResult, result);
    }
    
}
