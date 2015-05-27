package com.szati.beszallito.Controller;

import com.szati.beszallito.Model.Kedvezmeny;
import com.szati.beszallito.Model.Rendeles;
import com.szati.beszallito.Model.RendeltTermek;
import com.szati.beszallito.Model.Termek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;
import static org.junit.Assert.*;

public class ControllerTest {

    @Test
    public void testGetDarabByKategoriaIdFromRendeltek() {
        int kategoriaId = 1;
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        testTermekek.add(new Termek(2, "TEST2", 10, 30, 25, 2, 1, false, true));
        testTermekek.add(new Termek(3, "TEST3", 10, 30, 25, 2, 1, false, false));
        
        instance.getModel().setTermekek(testTermekek);
        List<RendeltTermek> rendeltList = instance.getMostaniRendeles();
        instance.setDarab(1, 3);
        instance.setDarab(2, 2);
        instance.setDarab(3, 1);
        
        int expResult = 3;
        int result = instance.getDarabByKategoriaIdFromRendeltek(rendeltList, kategoriaId);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetDarabByMarkaIdFromRendeltek() {
        int markaId = 2;
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        testTermekek.add(new Termek(2, "TEST2", 10, 30, 25, 2, 1, false, true));
        testTermekek.add(new Termek(3, "TEST3", 10, 30, 25, 2, 1, false, false));
        testTermekek.add(new Termek(4, "TEST4", 10, 30, 25, 2, 1, false, false));
        
        instance.getModel().setTermekek(testTermekek);
        
        List<RendeltTermek> rendeltList = instance.getMostaniRendeles();
        instance.setDarab(1, 3);
        instance.setDarab(2, 2);
        instance.setDarab(3, 1);
        instance.setDarab(4, 4);
        
        int expResult = 4;
        int result = instance.getDarabByMarkaIdFromRendeltek(rendeltList, markaId);
        assertEquals(expResult, result);
    }

    @Test
    public void testUpdateArakInRendeltTermek1() {
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        
        instance.getModel().setTermekek(testTermekek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        int darab = 10;
        instance.setDarab(1, darab);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        assertEquals(testTermekek.get(0).getAr(), mostaniRendeles.get(0).getAr());
    }
    
    @Test
    public void testUpdateArakInRendeltTermek2() {
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(2, "TEST2", 10, 30, 25, 2, 1, false, true));
        
        instance.getModel().setTermekek(testTermekek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        int darab = 10;
        instance.setDarab(2, 10);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        assertEquals(testTermekek.get(0).getAkciosAr(), mostaniRendeles.get(0).getAr());
    }
    
    @Test
    public void testGetKedvezmenyekForRendeltTermek1() {
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        instance.getModel().setTermekek(testTermekek);
        
        List<Kedvezmeny> testKedvezmenyek = new ArrayList<>();
        testKedvezmenyek.add(new Kedvezmeny(3, "ÚJAK TEST", true));
        instance.getModel().setKedvezmenyek(testKedvezmenyek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        
        int darab = 10;
        instance.setDarab(1, darab);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        int expResult = testTermekek.get(0).getAr() * darab;
        expResult *= 0.1;
        int result = instance.getKedvezmenyekForRendeltTermek(mostaniRendeles)
                .get(0).getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKedvezmenyekForRendeltTermek2() {
        Controller instance = new Controller();
        
        Map<Integer, String> testKategoriak = new HashMap<>();
        testKategoriak.put(1, "energiaital");
        instance.getModel().setKategoriak(testKategoriak);
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        instance.getModel().setTermekek(testTermekek);
        
        List<Kedvezmeny> testKedvezmenyek = new ArrayList<>();
        testKedvezmenyek.add(new Kedvezmeny(4, "ENERGIAITALOK TEST", true));
        instance.getModel().setKedvezmenyek(testKedvezmenyek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        
        int darab = 100;
        instance.setDarab(1, darab);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        int expResult = testTermekek.get(0).getAr() * darab;
        expResult *= 0.15;
        int result = instance.getKedvezmenyekForRendeltTermek(mostaniRendeles)
                .get(0).getValue();
        assertEquals(expResult, result);
    }
        
    @Test
    public void testGetKedvezmenyekForRendeltTermek3() {
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        instance.getModel().setTermekek(testTermekek);
        
        List<Kedvezmeny> testKedvezmenyek = new ArrayList<>();
        testKedvezmenyek.add(new Kedvezmeny(5, "ELSŐ5 TEST", true));
        instance.getModel().setKedvezmenyek(testKedvezmenyek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        
        int darab = 10;
        instance.setDarab(1, darab);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        if (LocalDate.now().getDayOfMonth() <= 5) {
            int expResult = testTermekek.get(0).getAr() * darab;
            expResult *= 0.10;
            int result = instance.getKedvezmenyekForRendeltTermek(mostaniRendeles)
                    .get(0).getValue();
            assertEquals(expResult, result);
        } else {
            assertEquals(0, instance.getKedvezmenyekForRendeltTermek(mostaniRendeles).size());
        }
    }
    
    @Test
    public void testGetKedvezmenyekForRendeltTermek4() {
        Controller instance = new Controller();
        
        Map<Integer, String> testMarkak = new HashMap<>();
        testMarkak.put(1, "Lipton");
        instance.getModel().setMarkak(testMarkak);
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 1, 1, true, false));
        testTermekek.add(new Termek(2, "TEST2", 10, 30, 25, 1, 1, true, false));
        testTermekek.add(new Termek(3, "TEST3", 10, 30, 25, 1, 1, true, false));
        testTermekek.add(new Termek(4, "TEST4", 10, 30, 25, 2, 1, true, false));
        instance.getModel().setTermekek(testTermekek);
        
        List<Kedvezmeny> testKedvezmenyek = new ArrayList<>();
        testKedvezmenyek.add(new Kedvezmeny(6, "LIPTON TEST", true));
        instance.getModel().setKedvezmenyek(testKedvezmenyek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        
        int darab = 10;
        instance.setDarab(1, darab);
        instance.setDarab(2, darab);
        instance.setDarab(3, darab);
        instance.setDarab(4, darab);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        int expResult = testTermekek.get(0).getAr() * darab * 3;
        expResult *= 0.10;
        int result = instance.getKedvezmenyekForRendeltTermek(mostaniRendeles)
                .get(0).getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetKedvezmenyekForRendeltTermek5() {
        Controller instance = new Controller();
        
        Map<Integer, String> testMarkak = new HashMap<>();
        testMarkak.put(1, "Ave");
        instance.getModel().setMarkak(testMarkak);
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 1, 1, true, false));
        testTermekek.add(new Termek(2, "TEST2", 10, 30, 25, 1, 1, true, false));
        testTermekek.add(new Termek(3, "TEST3", 10, 30, 25, 1, 1, true, false));
        testTermekek.add(new Termek(4, "TEST4", 10, 30, 25, 2, 1, true, false));
        instance.getModel().setTermekek(testTermekek);
        
        List<Kedvezmeny> testKedvezmenyek = new ArrayList<>();
        testKedvezmenyek.add(new Kedvezmeny(7, "AVE TEST", true));
        instance.getModel().setKedvezmenyek(testKedvezmenyek);
        
        List<RendeltTermek> mostaniRendeles = instance.getMostaniRendeles();
        
        int darab = 10;
        instance.setDarab(1, darab);
        instance.setDarab(2, darab);
        instance.setDarab(3, darab);
        instance.setDarab(4, darab);
        instance.updateArakInRendeltTermek(mostaniRendeles);
        
        int expResult = testTermekek.get(0).getAr() * darab * 3;
        expResult *= 0.10;
        int result = instance.getKedvezmenyekForRendeltTermek(mostaniRendeles)
                .get(0).getValue();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetRendelesFromMostaniRendeles() {
        Controller instance = new Controller();
        
        Rendeles result = instance.getRendelesFromMostaniRendeles();
        assertNotNull(result);
    }
    
    @Test
    public void testSetDarab() {
        int id = 1;
        int db = 10;
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        
        instance.getModel().setTermekek(testTermekek);
        instance.setDarab(id, db);
        assertEquals(db, instance.getDarab(id));
    }
    
    @Test
    public void testSetDarab2() {
        int id = 1;
        int db1 = 10;
        int db2 = 12;
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        
        instance.getModel().setTermekek(testTermekek);
        instance.setDarab(id, db1);
        instance.setDarab(id, db2);
        assertEquals(db2, instance.getDarab(id));
    }
    
    @Test
    public void testSetDarab3() {
        int id = 1;
        int db = 0;
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));
        
        instance.getModel().setTermekek(testTermekek);
        instance.setDarab(id, 4);
        instance.setDarab(id, db);
        assertEquals(db, instance.getDarab(id));
    }

    @Test
    public void testGetDarab() {
        int id = 1;
        Controller instance = new Controller();
        
        List<Termek> testTermekek = new ArrayList<>();
        testTermekek.add(new Termek(1, "TEST1", 10, 30, 25, 2, 1, true, false));        
        
        instance.getModel().setTermekek(testTermekek);
        instance.setDarab(1, 10);
        int expResult = 10;
        int result = instance.getDarab(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testGetMostaniRendeles() {
        Controller instance = new Controller();
        assertNotNull(instance.getMostaniRendeles());
    }

    @Test
    public void testGetModel() {
        Controller instance = new Controller();
        assertNotNull(instance.getModel());
    }
    
}
