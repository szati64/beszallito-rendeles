package com.szati.beszallito.Controller;

import com.szati.beszallito.Model.Termek;
import com.szati.beszallito.Model.RendeltTermek;
import com.szati.beszallito.Model.Model;
import com.szati.beszallito.Model.Rendeles;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

/**
 * Az alkalmazás kontrollere, mely tartalmazza az üzleti logikát, ehhez felhasználja
 * a Model csomagban reprezentált osztályakat.
 * 
 * @author szati
 */
public class Controller {
    
    /**
     * Az adatmodell.
     * 
     * @see com.szati.beszallito.Model.Model
     */
    private static Model model;
    
    /**
     * Egy {@link java.util.List} mely tartalmazza a jelenlegi rendelésben szereplő
     * termékeket.
     */
    private final List<RendeltTermek> mostaniRendeles;
    
    /**
     * A Controller osztály konstruktora.
     */
    public Controller() {
        mostaniRendeles = new ArrayList<>();
        model = new Model();
    }
    
    public int getDarabByKategoriaIdFromRendeltek(List<RendeltTermek> rendeltList,
            int kategoriaId) {
        int db = 0;
        for (RendeltTermek rt : rendeltList) 
            if (rt.getTermek().getKategoriaId() == kategoriaId)
                db++;
        return db;
    }
    
    public int getDarabByMarkaIdFromRendeltek(List<RendeltTermek> rendeltList,
            int markaId) {
        int db = 0;
        for (RendeltTermek rt : rendeltList) 
            if (rt.getTermek().getMarkaId()== markaId)
                db++;
        return db;
    }
    
    public void updateArakInRendeltTermek(List<RendeltTermek> mostaniRendeles) {
        mostaniRendeles.stream().forEach((rt) -> {
            Termek t = rt.getTermek();
            if (t.isAkcios())
                rt.setAr(t.getAkciosAr());
            else
                rt.setAr(t.getAr());
        });
    }
    
    public List<SimpleEntry<Integer, Integer>> getKedvezmenyekForRendeltTermek(List<RendeltTermek> mostaniRendeles) {
        List<SimpleEntry<Integer, Integer>> kedvezmenyek = new ArrayList<>();
        if (model.getKedvezmenyById(3) != null) {
            int szum = 0;
            
            for (RendeltTermek rt : mostaniRendeles)
                if (rt.getTermek().isUj())
                    szum += rt.getAr() * rt.getDb();
            
            if (szum != 0) {
                szum *= 0.1;
                model.addUjKedvezmeny(kedvezmenyek, 3, szum);
            }
        }
        
         if (model.getKedvezmenyById(4) != null && model.getKategoriaIdByNev("energiaital") != -1) {
            int italId = model.getKategoriaIdByNev("energiaital");
            int zsugorokSzama = 0;
            
            for (RendeltTermek rt : mostaniRendeles)
                if (rt.getTermek().getKategoriaId() == italId)
                    zsugorokSzama += rt.getDb() / rt.getTermek().getZsugor();
            
            if (zsugorokSzama >= 10) {
                int szum = 0;
                for (RendeltTermek rt : mostaniRendeles)
                    if (rt.getTermek().getKategoriaId() == italId)
                        szum += rt.getDb() * rt.getAr();
                
                szum *= 0.15;
                model.addUjKedvezmeny(kedvezmenyek, 4, szum);
            }
        }
         
        if (model.getKedvezmenyById(5) != null && LocalDate.now().getDayOfMonth() <= 5) {
            int szum = 0;
            for (RendeltTermek rt : mostaniRendeles)
                    szum += rt.getDb() * rt.getAr();

            szum *= 0.10;
            model.addUjKedvezmeny(kedvezmenyek, 5, szum);
        }
        
        if (model.getKedvezmenyById(6) != null &&
                getDarabByMarkaIdFromRendeltek(mostaniRendeles, model.getMarkaIdByNev("Lipton")) >= 3) {
            int szum = 0;
            for (RendeltTermek rt : mostaniRendeles)
                szum += rt.getDb() * rt.getAr();
            
            szum *= 0.10;
            model.addUjKedvezmeny(kedvezmenyek, 6, szum);
        }

        if (model.getKedvezmenyById(7) != null &&
                getDarabByMarkaIdFromRendeltek(mostaniRendeles, model.getMarkaIdByNev("Ave")) >= 3) {
            
            
            int szum = 0;
            for (RendeltTermek rt : mostaniRendeles)
                szum += rt.getDb() * rt.getAr();
            
            szum *= 0.10;
            model.addUjKedvezmeny(kedvezmenyek, 7, szum);
        }
        
        return kedvezmenyek;
    }
    
    public Rendeles getRendelesFromMostaniRendeles() {

        updateArakInRendeltTermek(mostaniRendeles);
        List<SimpleEntry<Integer, Integer>> kedvezmenyek = getKedvezmenyekForRendeltTermek(mostaniRendeles);
        
        
        int osszeg =  mostaniRendeles.stream().mapToInt(e -> e.getAr() * e.getDb()).sum() - 
        kedvezmenyek.stream().mapToInt(e -> e.getValue()).sum();
        
        return new Rendeles(-1, null, osszeg, mostaniRendeles, kedvezmenyek);
    }

    public void setDarab(int id, int db) {
        RendeltTermek rt;
        
        if (db == 0) {
            int i;
            for (i = 0; i < mostaniRendeles.size(); i++)
                if (mostaniRendeles.get(i).getTermek().getId() == id)
                    break;
            if (i != mostaniRendeles.size())
                mostaniRendeles.remove(i);
            return;
        }
        
        for (RendeltTermek rterm : mostaniRendeles)
            if (rterm.getTermek().getId() == id) {
                rterm.setDb(db);
                return;
            }
        
        rt = new RendeltTermek(model.getTermekById(id), db);                
        mostaniRendeles.add(rt);
    }
    
    public int getDarab(int id) {
        for (RendeltTermek mostaniRendele : mostaniRendeles)
            if (mostaniRendele.getTermek().getId() == id)
                return mostaniRendele.getDb();
        return 0;
    }
    
    /**
     * Visszaadja a mostani rendeléset.
     * 
     * @return a mostani rendelés
     */
    public List<RendeltTermek> getMostaniRendeles() {
        return mostaniRendeles;
    }

    /**
     * Visszaadja az adatmodellt.
     * 
     * @return az adatmodell
     * @see com.szati.beszallito.Model.Model
     */
    public Model getModel() {
        return model;
    }
    
}

