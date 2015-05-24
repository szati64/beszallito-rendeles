package com.szati.beszallito.Model;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * A model kisebb részeit összefogó osztály.
 * 
 * @author szati
 */
public class Model {
    
    /**
     * Termékek egy listája.
     */
    private List<Termek> termekek;
    
    /**
     * A termékek márkáit leíró map, melynek kulcsa a márka egyedi
     * azonosítója, értéke pedig a márka neve.
     */
    private Map<Integer, String> markak;
    
    /**
     * A termékek kategóriáit leíró map, melynek kulcsa a kategória
     * egyedi azonosítója, értéke pedig a kategória neve.
     */
    private Map<Integer, String> kategoriak;
    
    /**
     * Kedvezmények listája.
     */
    private List<Kedvezmeny> kedvezmenyek;
    
    /**
     * A Model osztály konstruktora.
     */
    public Model() {
        termekek = new ArrayList<>();
        markak = new HashMap<>();
        kategoriak = new HashMap<>();
        kedvezmenyek = new ArrayList<>();
    }
    
    /**
     * Visszaadja azt a terméket, amelynek az egyedi azonosítója megegyezik a
     * paraméterként átadott azonosítóval.
     * 
     * @param termekId egy termék azonosító
     * @return az a termék, amelynek az azonosítója megegyezik a
     * paraméterként átadott azonosítóval
     */
    public Termek getTermekById(int termekId) {
        for (Termek t : termekek)
            if (t.getId() == termekId)
                return t;
        return null;
    }
    
    /**
     * Visszaadja azt a kedvezményt, amelynek az azonosítója megegyezik a
     * paraméterként átadott azonosítóval.
     * 
     * @param kedvezmenyId egy kedvezmény azonosító
     * @return az a kedvezmény, amelynek az azonosítója megegyezik a paraméterként
     * átadott azonosítóval
     */
    public Kedvezmeny getKedvezmenyById(int kedvezmenyId) {
        for (Kedvezmeny k : kedvezmenyek)
            if (k.getId() == kedvezmenyId)
                return k;
        return null;
    }
    
    /**
     * Hozzáad egy új kedvezményt egy megadott kedvezménylistához.
     * 
     * @param kedvezmenyek kedvezmények listája, melyhez hozzáadunk egy új kedvezményt
     * @param kedvezmenyId a hozzáadandó kedvezmény egyedi azonosítója
     * @param kedvezmenyAr a kedvezmény miatt elengedett ár
     */
    public void addUjKedvezmeny(List<SimpleEntry<Integer, Integer>> kedvezmenyek,
            int kedvezmenyId, int kedvezmenyAr) {        
        boolean frissitve = false;
        
        for (SimpleEntry<Integer, Integer> k : kedvezmenyek)
            if (k.getKey() == kedvezmenyId) {
                k.setValue(k.getValue()+ kedvezmenyAr);
                frissitve = true;
            }
        
        if (!frissitve)
            kedvezmenyek.add(new SimpleEntry<>(kedvezmenyId, kedvezmenyAr));
    }

    /**
     * Beállítja a termékek egy új listáját.
     * 
     * @param termekek a termékek egy listája
     */
    public void setTermekek(List<Termek> termekek) {
        this.termekek = termekek;
    }
    
    /**
     * Beállítja az elérhető márkákat leíró map-et.
     * 
     * @param markak elérhető márkákat leíró map
     */
    public void setMarkak(Map<Integer, String> markak) {
        this.markak = markak;
    }
    
    /**
     * Beállítja az elérhető kategóriákat leíró map-et.
     * 
     * @param kategoriak elérhető kategóriákat leíró map
     */
    public void setKategoriak(Map<Integer, String> kategoriak) {
        this.kategoriak = kategoriak;
    }

    /**
     * Beállít egy új kedvezmény listát.
     * 
     * @param kedvezmenyek egy kedvezmény lista
     */
    public void setKedvezmenyek(List<Kedvezmeny> kedvezmenyek) {
        this.kedvezmenyek = kedvezmenyek;
    }
    
    /**
     * Visszaadja a termékek listáját.
     * 
     * @return a termékek listája
     */
    public List<Termek> getTermekek() {
        return termekek;
    }
    
    /**
     * Visszaadja a márkákat leíró map-et.
     * 
     * @return a márkákat leíró map
     */
    public Map<Integer, String> getMarkak() {
        return markak;
    }

    /**
     * Visszaadja a márka azonosítóját a neve alapján.
     * 
     * @param nev a márka neve
     * @return a márka azonosítója
     */
    public int getMarkaIdByNev(String nev) {
        for (Integer id : markak.keySet())
            if (markak.get(id).equals(nev))
                return id;
        return -1;
    }
    
    /**
     * Visszaadja a kategóriákat leíró map-et.
     * 
     * @return  a kategóriákat leíró map
     */
    public Map<Integer, String> getKategoriak() {
        return kategoriak;
    }

    /**
     * Visszaadja a márka azonosítóját a neve alapján.
     * 
     * @param nev a márka neve
     * @return a márka azonosítója
     */
    public int getKategoriaIdByNev(String nev) {
        for (Integer id : kategoriak.keySet())
            if (kategoriak.get(id).equals(nev))
                return id;
        return -1;
    }
    
    /**
     * Visszaadja a kedvezmények listáját.
     * 
     * @return  a kedvezmények listája
     */
    public List<Kedvezmeny> getKedvezmenyek() {
        return kedvezmenyek;
    }    
    
}
