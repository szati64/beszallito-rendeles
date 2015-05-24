package com.szati.beszallito.Model;

/**
 * A kedvezmények reprezentációjára szolgáló osztály.
 * 
 * @author szati
 */
public class Kedvezmeny {
    
    /**
     * A kedvezmény egyedi azonosítója.
     */
    private final int id;
    
    /**
     * A kedvezmény leírása.
     */
    private final String leiras;
    
    /**
     * A kedvezmény aktivitása.
     */
    private final boolean aktiv;

    /**
     * Kedvezmények osztály konstruktora.
     *
     * @param id a kedvezmény egyedi azonosítója
     * @param leiras a kedvezmény szöveges leírása
     * @param aktiv a kedvezmény aktivitása
     */
    public Kedvezmeny(int id, String leiras, boolean aktiv) {
        this.id = id;
        this.leiras = leiras;
        this.aktiv = aktiv;
    }

    /**
     * Visszaadja a kedvezmény egyedi azonosítóját.
     * 
     * @return a kedvezmény egyedi azonosítója
     */
    public int getId() {
        return id;
    }

    /**
     * Visszaadja a kedvezmény leírását.
     * 
     * @return a kedvezmény leírása
     */
    public String getLeiras() {
        return leiras;
    }

    /**
     * Visszadja a kedvezmény aktivitását.
     * 
     * @return a kedvezmény aktivitása
     */
    public boolean isAktiv() {
        return aktiv;
    }
}
