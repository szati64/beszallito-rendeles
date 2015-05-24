package com.szati.beszallito.Controller;

import java.awt.EventQueue;
import java.io.IOException;

import java.io.InputStream;
import org.apache.commons.io.IOUtils;
import com.szati.beszallito.View.Bejelentkezes;
import org.json.*;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Az alkalmazás fő osztálya.
 * 
 * @author szati
 */
public class Main {
    
    /**
     * A loggolást végző osztály.
     */
    private static final Logger LOG = LoggerFactory.getLogger(DAO.class);
    
    /**
     * Az alkalmazás main függvénye.
     * 
     * @param args parancssori argumentumok
     */
    public static void main(String[] args) {
        try {
            Controller controller = new Controller();
            InputStream inpStream = Main.class.getResourceAsStream("/database.json");
            JSONObject obj = new JSONObject(IOUtils.toString(inpStream));
            boolean csatlakozva = false;
            
            JSONArray databases = obj.getJSONArray("databases");
            for (int i = 0; i < databases.length() && !csatlakozva; i++) {
                JSONObject database = databases.getJSONObject(i);
                
                if (args.length == 0 || database.getString("name").equals(args[0])) {
                    ConnectionFactory.setConnection(
                        database.getString("url"),
                        database.getString("user"),
                        database.getString("pass"));
                    
                    LOG.info("Kapcsolódva a " + database.getString("name") + 
                            " adatbázishoz");
                    
                    csatlakozva = true;
                }
            }
            
            if (csatlakozva)
                EventQueue.invokeLater(() -> {
                    Bejelentkezes bej = new Bejelentkezes("Bejelentkezés", controller);
                });
            else
                throw new IOException();
            
        } catch (IOException ex) {
            LOG.error("Hiba az adatbázishoz kapcsolódás során!");
        }
    }
}
