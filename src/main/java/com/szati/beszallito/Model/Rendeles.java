package com.szati.beszallito.Model;

import java.util.List;
import java.time.LocalDate;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;

/**
 * Egy rendelést reprezentáló osztály.
 * 
 * @author szati
 */
public class Rendeles {
    
    /**
     * A rendelés egyedi azonosítója.
     */
    private final int id;
    
    /**
     * A rendelés leadásának dátuma.
     */
    private final LocalDate datum;
    
    /**
     * A rendelés fizetendő ára.
     */
    private int ar;
    
    /**
     * Egy {@link java.util.List}, mely tartalmazza a megrendelt termékeket.
     */
    private final List<RendeltTermek> rendeltTermekek;
    
    /**
     * Egy {@link java.util.List}, mely tartalmazza a rendelésre érvényes kedvezményeket
     * a kedvezmény azonosítója és a kedvezmény miatt elengedett ár.
     */
    private final List<SimpleEntry<Integer, Integer>> kedvezmenyek;
    
    /**
     * Rendeles osztály konstruktora.
     * 
     * @param id a rendelés egyedi azonosítója
     * @param datum a rendelés dátuma
     * @param ar a rendelés ára
     * @param rendeltTermekek rendelt termékek listája
     * @param kedvezmenyek a rendelésre érvényes kedvezmények
     */
    public Rendeles(int id, LocalDate datum, int ar,
            List<RendeltTermek> rendeltTermekek, List<SimpleEntry<Integer, Integer>> kedvezmenyek) {
        this.id = id;
        this.datum = datum;
        this.ar = ar;
        this.rendeltTermekek = rendeltTermekek != null ? rendeltTermekek : new ArrayList<>();        
        this.kedvezmenyek = kedvezmenyek != null ? kedvezmenyek : new ArrayList<>();
    }
    
    /**
     * Visszaadja a rendelés egyedi azonosítóját.
     * 
     * @return a rendelés egyedi azonosítója
     */
    public int getId() {
        return id;
    }

    /**
     * Visszadja a rendelés dátumát.
     * 
     * @return a rendelés dátuma
     */
    public LocalDate getDatum() {
        return datum;
    }

    /**
     * Visszaadja a rendelés árát.
     * 
     * @return a rendelés ára
     */
    public int getAr() {
        return ar;
    }
    
    /**
     * Visszaadja a megrendelt termékek listáját.
     * 
     * @return megrendelt termékek listája
     */
    public List<RendeltTermek> getRendeltTermekek() {
        return rendeltTermekek;
    }

    /**
     * Visszaadja a rendelésre érvényes kedvezmények listáját.
     * 
     * @return a rendelésre érvényes kedvezmények listája
     */
    public List<SimpleEntry<Integer, Integer>> getKedvezmenyek() {
        return kedvezmenyek;
    }
}
