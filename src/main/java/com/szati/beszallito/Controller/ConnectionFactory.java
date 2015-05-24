package com.szati.beszallito.Controller;

import java.sql.*;
import java.time.LocalDate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Az adatbázis kapcsolatot megvalósító osztály.
 * 
 * @author szati
 */
public class ConnectionFactory {
    
    /**
     * A loggolást végző osztály.
     */
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionFactory.class);
    
    /**
     * Az adatbázis elérésnek url-je.
     */
    private static String db_url;
    
    /**
     * Az adatbázishoz kapcsolódáshoz használt felhasználónév.
     */
    private static String user;

    /**
     * Az adatbázis kapcsolódáshoz használt jelszó.
     */
    private static String pass;

    /**
     * A kapcsolódáshoz szükséges adatok beállítását végzi.
     * 
     * @param url az adatbázis url-je
     * @param user az adatbázis kapcsolódáshoz szükséges felhasználónév
     * @param pass az adatbázis kapcsolódáshoz szükséges jelszó
     */
    public static void setConnection(String url, String user, String pass) {
        ConnectionFactory.db_url = url;
        ConnectionFactory.user = user;
        ConnectionFactory.pass = pass;
    }

    /**
     * Létrehoz egy kapcsolatot az adatbázissal.
     * 
     * @return a létrejövő kapcsolat
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(db_url, user, pass);
        } catch (SQLException e) {
            LOG.error(LocalDate.now() + ": " + "Hiba a kapcsolat létrehozás során!");
        }
        return connection;
    }
}
