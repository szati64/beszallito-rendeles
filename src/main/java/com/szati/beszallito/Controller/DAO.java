package com.szati.beszallito.Controller;

import com.szati.beszallito.Model.Termek;
import com.szati.beszallito.Model.RendeltTermek;
import com.szati.beszallito.Model.Rendeles;
import com.szati.beszallito.Model.Kedvezmeny;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Az alkalmazás adatbázis-műveleteinek végrehajtásához használt osztály.
 * 
 * @author szati
 */
public class DAO {
    
    /**
     * A loggolást végző osztály.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DAO.class);
    
    /**
     * A bejelentkezett felhasználó egyedi azonosítója.
     */
    private static int felhasznaloId = -1;

    /**
     * Visszaadja a bejelentkezett felhasználó egyedi azonosítóját.
     * 
     * @return a bejelenetkezett felhasználó egyedi azonosítója
     */
    public static int getFelhasznaloId() {
        return felhasznaloId;
    }
    
    /**
     * Visszaadja a bejelentkezett felhasználó azonosítóját.
     * 
     * @param nev a felhasználó neve
     * @param jelszo a felhasználó jelszava
     */
    public static void logIn(String nev, String jelszo) {    
        String sql = "SELECT id FROM felhasznalok WHERE"
                    + " felhasznalonev = ? AND jelszo=?";
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, nev);
            preparedStatement.setString(2, jelszo);
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    felhasznaloId =  rs.getInt(1);
                    LOG.info(nev + " bejelentkezett");
                    break;
                }
            }
        } catch (SQLException e) {
            LOG.error("Hiba a bejelentkezés során!");
        }
    }
    
    /**
     * Visszaad egy {@link java.util.Map}-et, amely tartalmazza az adatbázisban
     * eltárolt kategóriákat azonosító kulcs, leírás érték formában.
     * 
     * @return tartalmazza az adatbázisban
     * eltárolt kategóriákat azonosító kulcs, leírás érték formában
     */
    public static Map<Integer, String> getKategoriak() {
        Map<Integer, String> kategoriak = new HashMap<>();
        String sql = "SELECT * FROM kategoriak";        
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next())
                kategoriak.put(rs.getInt(1), rs.getString(2));
        } catch (SQLException e) {
            LOG.error("Hiba a kategóriák lekérdezése során!");
        }
        
        return kategoriak;
    }
    
    /**
     * Visszaadja egy {@link java.util.List}-et, amely az adatbázisban eltárolt
     * kedvezményeket tartalmazza.
     * 
     * @return {@link java.util.List} mely az adatbázisban eltárolt
     * kedvezményeket tartalmazza
     */
    public static List<Kedvezmeny> getKedvezmenyek() {
        List<Kedvezmeny> kedvezmenyek = new ArrayList<>();
        String sql = "SELECT * FROM kedvezmenyek";      
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                Kedvezmeny k = new Kedvezmeny(rs.getInt(1), rs.getString(2),
                        rs.getBoolean(3));
                kedvezmenyek.add(k);
            }
        } catch (SQLException e) {
            LOG.error("Hiba a kedvezmények lekérdezése során!");
        }
        
        return kedvezmenyek;
    }
    
    /**
     * Visszaad egy {@link java.util.Map}-et, amely tartalmazza az adatbázisban
     * eltárolt márkákat azonosító kulcs, leírás érték formában.
     * 
     * @return tartalmazza az adatbázisban
     * eltárolt márkákat azonosító kulcs, leírás érték formában
     */
    public static Map<Integer, String> getMarkak() {
        Map<Integer, String> markak = new HashMap<>();
        String sql = "SELECT * FROM markak";
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next())
                markak.put(rs.getInt(1), rs.getString(2));
        } catch (SQLException e) {
            LOG.error("Hiba a márkák lekérdezése során!");
        }
        
        return markak;
    }
    
    /**
     * Visszaadja a megadott azonosítójú rendelésre vonatkozó kedvezmények listáját
     * az adatbázisból.
     * 
     * @param rendelesId a rendelés azonosítója
     * @return a rendelésre vonatkozó kedvezmények listája
     */
    public static List<SimpleEntry<Integer, Integer>> getRendelesKedvezmenyek(int rendelesId) {
        List<SimpleEntry<Integer, Integer>> kedvezmenyek = new ArrayList<>();
        String sql = "SELECT * FROM rendeltkedvezmeny WHERE"
                + " rendelesId = ?";        
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(rendelesId));
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    SimpleEntry<Integer, Integer> t = new SimpleEntry<>(
                        rs.getInt(2), rs.getInt(3));
                    kedvezmenyek.add(t);
                }
            }
        } catch (SQLException e) {
            LOG.error("Hiba a " + rendelesId
                + " azonosítójú rendelés kedvezményeinek lekérdezése során!");
        }
        
        return kedvezmenyek;
    }  
    
    /**
     * Visszaadja a bejelentkezett felhasználónak leadott rendeléseit.
     * 
     * @return a bejelentkezett felhasználó leadott rendelései
     */
    public static List<Rendeles> getRendelesek() {
        List<Rendeles> rendelesek = new ArrayList<>();
        String sql = "SELECT * FROM rendelesek WHERE"
                    + " rendeloId = ?";        
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(felhasznaloId));
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    Rendeles r = new Rendeles(rs.getInt(1),
                        rs.getTimestamp(3).toLocalDateTime().toLocalDate(),
                        rs.getInt(4), null, null);
                    rendelesek.add(r);
                }
            }
        } catch (SQLException e) {
            LOG.error("Hiba a felhasználó rendeléseinek lekérdezése során!");
        }
        
        return rendelesek;
    } 
    
    /**
     * Visszaadja a rendelésben leadott termékeket, a paraméterben megadott
     * rendelés azonosítónak megfelelően.
     * 
     * @param controller az alkalmazás kontrollere
     * @param rendelesId a rendelés azonosítója
     * @return a rendelésben leadott termékek
     */
    public static List<RendeltTermek> getRendeltTermekek(Controller controller,
            int rendelesId) {
        List<RendeltTermek> rendeltTermekek = new ArrayList<>();
        String sql = "SELECT * FROM rendelttermekek WHERE"
                    + " rendelesId = ?";
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, Integer.toString(rendelesId));
            try (ResultSet rs = preparedStatement.executeQuery()) {
                while(rs.next()) {
                    RendeltTermek r = new RendeltTermek(
                        controller.getModel().getTermekById(rs.getInt(2)),
                        rs.getInt(3), rs.getInt(4));
                    rendeltTermekek.add(r);
                }
            }
        } catch (SQLException e) {
            LOG.error("Hiba a " + rendelesId
                    + " azonosítójú  rendelés termékeinek lekérdezése során!");
        }
        
        return rendeltTermekek;
    }

    /**
     * Visszaadja az adatbázisban eltárolt termékek listáját a megadott
     * feltételeknek megfelelően.
     * 
     * @param feltetlek a lekérdezés eredményének szűkítéséhez szükséges feltételek
     * @return az adatbázisban eltárolt termékek listája
     */
    private static List<Termek> getTermekek(String feltetlek) {
        List<Termek> termekek = new ArrayList<>();
        String sql = "SELECT * FROM termekek";
        
        if (!feltetlek.isEmpty())
            sql += " WHERE " + feltetlek;
        
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery()) {
            while(rs.next()) {
                Termek termek = new Termek(
                        rs.getInt(1), rs.getString(2), rs.getInt(3),
                        rs.getInt(4), rs.getInt(5), rs.getInt(6),
                        rs.getInt(7), rs.getBoolean(8), rs.getBoolean(9));
                termekek.add(termek);
            }
        } catch (SQLException e) {
            if (feltetlek.isEmpty())
                LOG.error("Hiba a termékek lekérdezései során!");
            else
                LOG.error("Hiba a termékek lekérdezései"
                        + " során a következő feltételekkel: " + feltetlek);
        }
        
        return termekek;
    }
    
    /**
     * Visszaadja az adatbázisban eltárolt termékek listáját.
     * 
     * @param csakElerhetok igazságértéktől függően csak az elérhető termékeket
     * kéri le
     * @return az adatbázisban eltárol termékek listája
     */
    public static List<Termek> getTermekek(boolean csakElerhetok) {
        return getTermekek(csakElerhetok ? "elerheto = 1" : "");
    }
    
    /**
     * Visszaadja az adatbázisban eltárolt termékek listáját, valamint az
     * eredmény lista szűkítéséhez megszorításokat adhatunk meg.
     * 
     * @param nev a termék neve
     * @param markaId a termék márka azonosítója
     * @param kategoriaId a termék kategória azonosítója
     * @param uj a termék új-e
     * @param akcios a termék akciós-e
     * @param elerheto a termék elérhető-e
     * @return az adatbázisban eltárolt termékek listája megszorításokkal
     */
    public static List<Termek> getTermekekFeltetelel(String nev, int markaId,
            int kategoriaId, boolean uj, boolean akcios, boolean elerheto) {
        List<String> s = new ArrayList<>();
        if (nev.length() != 0)
            s.add("nev LIKE '%" + nev + "%'");
        if (markaId != -1)
            s.add("markaId = " + markaId);
        if (kategoriaId != -1)
            s.add("kategoriaId = " + kategoriaId);
        if (uj)
            s.add("uj = 1");
        if (akcios)
            s.add("akcios = 1");
        if (elerheto)
            s.add("elerheto = 1");
        
        return getTermekek(s.isEmpty() ? "" : String.join(" AND ", s));
    }    
    
    /**
     * Elküldi az összeállított {@link com.szati.beszallito.Model.Rendeles}-t.
     * 
     * @param rendeles a rendelésre elküldenő {@link com.szati.beszallito.Model.Rendeles}
     * @return a rendelés elküldésének sikeressége
     */
    public static boolean rendelesElkuldese(Rendeles rendeles) {        
        try(Connection conn = ConnectionFactory.getConnection();
            Statement statement = conn.createStatement()) { 
            int rendelesId;
            conn.setAutoCommit(false);
            statement.executeUpdate(
                    String.format("INSERT INTO rendelesek VALUES(DEFAULT, %d,"
                    + "DEFAULT, %d)", felhasznaloId,
                    rendeles.getAr()), Statement.RETURN_GENERATED_KEYS);
            
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                rendelesId = rs.getInt(1);
            
                for (RendeltTermek rt : rendeles.getRendeltTermekek()) {
                    statement.executeUpdate(
                    String.format("INSERT INTO rendelttermekek VALUES("
                            + "%d, %d, %d, %d)", rendelesId, rt.getTermek().getId(),
                            rt.getAr(), rt.getDb()));
                }
                
                for (SimpleEntry<Integer, Integer> k : rendeles.getKedvezmenyek()) {
                    statement.executeUpdate(
                    String.format("INSERT INTO rendeltkedvezmeny VALUES("
                            + "%d, %d, %d)", rendelesId,
                            k.getKey(), k.getValue()));
                }
                
                conn.commit();
                LOG.info(rendelesId +
                        " azonosítójú rendelés elküldve!");
                
                return true;
            }
        }   
        catch (SQLException e) {
            LOG.error("Hiba a rendelés feladása során: " + e);
        }
        return false;
    }
}
