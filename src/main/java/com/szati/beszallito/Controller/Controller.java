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
     * A kosárban lévő termékek.
     */
    private final List<RendeltTermek> mostaniRendeles;
    
    /**
     * A Controller osztály konstruktora.
     */
    public Controller() {
        mostaniRendeles = new ArrayList<>();
        model = new Model();
    }
    
    /**
     * Megadja hány darab adott kategóriájú termék van a kosárban lévő termékekben.
     * 
     * @param rendeltList a kosárban lévő termékek listája
     * @param kategoriaId kategória azonosító
     * @return hány darab termék van a kosárban lévő termékek listában az adott kategóriájúból
     */
    public int getDarabByKategoriaIdFromRendeltek(List<RendeltTermek> rendeltList,
            int kategoriaId) {
        int db = 0;
        for (RendeltTermek rt : rendeltList) 
            if (rt.getTermek().getKategoriaId() == kategoriaId)
                db++;
        return db;
    }
    
    /**
     * Megadja hány darab adott márkájú termék van a kosárban lévő termékekben.
     * 
     * @param rendeltList a kosárban lévő termékek listája
     * @param markaId márka azonosító
     * @return hány darab termék van a rendelt termékek listában az adott márkájúból
     */
    public int getDarabByMarkaIdFromRendeltek(List<RendeltTermek> rendeltList,
            int markaId) {
        int db = 0;
        for (RendeltTermek rt : rendeltList) 
            if (rt.getTermek().getMarkaId()== markaId)
                db++;
        return db;
    }
    
    /**
     * Frissíti az árakat a kosárban lévő termékek listájában.
     * 
     * @param mostaniRendeles  a rendelt termékek listája
     */
    public void updateArakInRendeltTermek(List<RendeltTermek> mostaniRendeles) {
        mostaniRendeles.stream().forEach((rt) -> {
            Termek t = rt.getTermek();
            if (t.isAkcios())
                rt.setAr(t.getAkciosAr());
            else
                rt.setAr(t.getAr());
        });
    }
    
    /**
     * Visszaadja a kosárban lévő termékek listájára vonatkozó érvényes kedvezményeket.
     * A lista elemei olyan {@link java.util.AbstractMap.SimpleEntry} típusúak,
     * aminek a kulcsa a kedvezmény azonosítója, az értéke pedig a kedvezmény miatt
     * elengedett ár.
     * 
     * @param mostaniRendeles a kosárban lévő termékek listája
     * @return a rendelt termékek listájára vonatkozó érvényes kedvezmények
     */
    public List<SimpleEntry<Integer, Integer>> getKedvezmenyekForRendeltTermek(List<RendeltTermek> mostaniRendeles) {
        List<SimpleEntry<Integer, Integer>> kedvezmenyek = new ArrayList<>();
        if (model.getKedvezmenyById(3) != null) {
            int szum = mostaniRendeles
                    .stream()
                    .filter(x -> x.getTermek().isUj())
                    .mapToInt(x -> x.getAr() * x.getDb()).sum();

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
                int szum = mostaniRendeles
                    .stream()
                    .filter(x -> x.getTermek().getKategoriaId() == italId)
                    .mapToInt(x -> x.getAr() * x.getDb()).sum();
                
                
                szum *= 0.15;
                model.addUjKedvezmeny(kedvezmenyek, 4, szum);
            }
        }
         
        if (model.getKedvezmenyById(5) != null && LocalDate.now().getDayOfMonth() <= 5) {
            int szum = mostaniRendeles
                .stream()
                .mapToInt(x -> x.getAr() * x.getDb()).sum();

            szum *= 0.10;
            model.addUjKedvezmeny(kedvezmenyek, 5, szum);
        }
        
        if (model.getKedvezmenyById(6) != null &&
                getDarabByMarkaIdFromRendeltek(mostaniRendeles, model.getMarkaIdByNev("Lipton")) >= 3) {
            int szum = mostaniRendeles
                .stream()
                .filter(x -> x.getTermek().getMarkaId() == model.getMarkaIdByNev("Lipton"))
                .mapToInt(x -> x.getAr() * x.getDb()).sum();
            
            szum *= 0.10;
            model.addUjKedvezmeny(kedvezmenyek, 6, szum);
        }

        if (model.getKedvezmenyById(7) != null &&
                getDarabByMarkaIdFromRendeltek(mostaniRendeles, model.getMarkaIdByNev("Ave")) >= 3) {
            int szum = mostaniRendeles
                .stream()
                .filter(x -> x.getTermek().getMarkaId() == model.getMarkaIdByNev("Ave"))
                .mapToInt(x -> x.getAr() * x.getDb()).sum();
            
            szum *= 0.10;
            model.addUjKedvezmeny(kedvezmenyek, 7, szum);
        }
        
        return kedvezmenyek;
    }
    
    /**
     * A kosárban lévő termékek listájából egy {@link com.szati.beszallito.Model.Rendeles}
     * objektumot készít, mely már tartalmazza a rendelésre vonatkozó kedvezményeket,
     * és a pontos árakat. Felhasználja az {@code updateArakInRendeltTermek} és
     * {@code getKedvezmenyekForRendeltTermek} függvényeket.
     * 
     * @return egy {@link com.szati.beszallito.Model.Rendeles} objektum,
     * mely tartalmazza a rendelésre vonatkozó kedvezményeket,
     * és a pontos árakat
     */
    public Rendeles getRendelesFromMostaniRendeles() {
        updateArakInRendeltTermek(mostaniRendeles);
        List<SimpleEntry<Integer, Integer>> kedvezmenyek = getKedvezmenyekForRendeltTermek(mostaniRendeles);
        
        
        int osszeg =  mostaniRendeles.stream().mapToInt(e -> e.getAr() * e.getDb()).sum() - 
        kedvezmenyek.stream().mapToInt(e -> e.getValue()).sum();
        
        return new Rendeles(-1, null, osszeg, mostaniRendeles, kedvezmenyek);
    }

    /**
     * A kosárban lévő termékek listájához hozzáad egy új terméket.
     * 
     * @param id a termék azonosítója
     * @param db mennyiség
     */
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
    
    /**
     * Visszaadja hogy a megadott termékből mennyi van a kosárban.
     * 
     * @param id termék azonosítója
     * @return a termékből a kosárban lévő mennyiség
     */
    public int getDarab(int id) {
        for (RendeltTermek mostaniRendele : mostaniRendeles)
            if (mostaniRendele.getTermek().getId() == id)
                return mostaniRendele.getDb();
        return 0;
    }
    
    /**
     * Visszaadja a kosárban lévő termékeket.
     * 
     * @return a kosárban lévő termékek
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

