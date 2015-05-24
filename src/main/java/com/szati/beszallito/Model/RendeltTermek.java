package com.szati.beszallito.Model;

/**
 * Egy rendelésre feladott termék reprezentációja.
 * 
 * @author szati
 */
public class RendeltTermek {
    
    /**
     * A rendelésre feladott termék.
     */
    private final Termek termek;
    
    /**
     * A rendelésre feladott termékért fizett ár.
     */
    private int ar;
    
    /**
     * A rendelésre feladott termék darabszáma.
     */
    private int db;

    /**
     * A RendeltTermek konstruktora.
     * 
     * @param termek a rendelésre feladott termék
     * @param ar a rendelésre feladott termékért fizetett ár
     * @param db a rendelésre feladott termék darabszáma
     */
    public RendeltTermek(Termek termek, int ar, int db) {
        this.termek = termek;
        this.ar = ar;
        this.db = db;
    }    
    
    /**
     * A RendelTermek konstruktora.
     * 
     * @param termek a rendelésre feladott termék
     * @param db a rendelésre feladott termék darabszáma
     */
    public RendeltTermek(Termek termek, int db) {
        this.termek = termek;
        this.ar = 0;
        this.db = db;
    }

    /**
     * Beállít a rendelésre feladott terméknek egy új árat.
     * 
     * @param ar a rendelésre feladott termék új ára
     */
    public void setAr(int ar) {
        this.ar = ar;
    }

    /**
     * Beállít a rendelésre feladott terméknek egy új darabszámot.
     * 
     * @param db a rendelésre feladott termék új darabszáma
     */
    public void setDb(int db) {
        this.db = db;
    }

    /**
     * Visszaadja a rendelésre feladott terméket.
     * 
     * @return a rendelésre feladott termék
     */
    public Termek getTermek() {
        return termek;
    }

    /**
     * Visszaadja a rendelésre feladott termékért fizetett árat.
     * 
     * @return a rendelésre feladott termékért fizetett ár
     */
    public int getAr() {
        return ar;
    }

    /**
     * Visszaadja a rendelésre feladott termék darabszámát.
     * 
     * @return a rendelésre feladott termék darabszáma
     */
    public int getDb() {
        return db;
    }
}
