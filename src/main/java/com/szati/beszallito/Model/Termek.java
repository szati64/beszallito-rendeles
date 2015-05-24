package com.szati.beszallito.Model;

/**
 * Egy termék reprezentációja.
 * 
 * @author szati
 */
public class Termek {
    
    /**
     * A termék egyedi azonosítója.
     */
    private final int id;
    
    /**
     * A termék neve.
     */
    private final String nev;
    
    /**
     * Az adott termékből egy zsugorban hány darab van.
     */
    private final int zsugor;
    
    /**
     * Az adott termék új.
     */
    private final boolean uj;
    
    /**
     * Az adott termék akciós.
     */
    private final boolean akcios;
    
    /**
     * A termék ára.
     */
    private final int ar;
    
    /**
     * A termék akciós ára.
     */
    private final int akciosAr;
    
    /**
     * A termék márka azonosítója.
     */
    private final int markaId;
    
    /**
     * A termék kategória azonosítója.
     */
    private final int kategoriaId;

    /**
     * A termék kontruktora.
     * 
     * @param id a termék egyedi azonosítója
     * @param nev a termék neve
     * @param zsugor az adott termékből egy zsugorban hány darab van
     * @param ar a termék ára
     * @param akciosAr a termék akciós ára
     * @param markaId a termék márka azonosítója
     * @param kategoriaId a termék kategória azonosítója
     * @param uj a termék új
     * @param akcios a termék akciós
     */
    public Termek(int id, String nev, int zsugor,
            int ar, int akciosAr, int markaId, int kategoriaId, boolean uj, boolean akcios) {
        this.id = id;
        this.nev = nev;
        this.zsugor = zsugor;
        this.ar = ar;
        this.akciosAr = akciosAr;
        this.markaId = markaId;
        this.kategoriaId = kategoriaId;
        this.uj = uj;
        this.akcios = akcios;
    }
    
    /**
     * Visszaadja a termék egyedi azonosítóját.
     * 
     * @return visszaadja a termék egyedi azonosítóját
     */
    public int getId() {
        return id;
    }

    /**
     * Visszaadja a termék nevét.
     * 
     * @return visszaadja a termék nevét
     */
    public String getNev() {
        return nev;
    }

    /**
     * Visszaadja hogy hány darab van a termékből egy zsugorban.
     * 
     * @return visszaadja hogy hány darab van a termékből egy zsugorban
     */
    public int getZsugor() {
        return zsugor;
    }

    /**
     * Visszaadja a termék árát.
     * 
     * @return visszaadja a termék árát
     */
    public int getAr() {
        return ar;
    }
    
    /**
     * Visszaadja a termék raklapos árát.
     * 
     * @return visszaadja a termék raklapos árát
     */
    public int getAkciosAr() {
        return akciosAr;
    }

    /**
     * Visszaadja a termék márka azonosítóját.
     * 
     * @return visszaadja a termék márka azonosítóját
     */
    public int getMarkaId() {
        return markaId;
    }

    /**
     * Visszaadja a termék kategória azonosítóját.
     * 
     * @return visszaadja a termék kategória azonosítóját
     */
    public int getKategoriaId() {
        return kategoriaId;
    }

    /**
     * Visszaadja hogy új termék-e.
     * 
     * @return visszaadja hogy új termék-e
     */
    public boolean isUj() {
        return uj;
    }

    /**
     * Visszaadja hogy akciós termék-e.
     * 
     * @return visszadja hogy akciós termék-e
     */
    public boolean isAkcios() {
        return akcios;
    }
}