/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tiedostonKasittely;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;

/**
 * Kirjoittaa JSONObjectin annetussa polussa olevaan tiedostoon
 * @author elias
 */
public class GeoJsonKirjoittaja {

    
    /**
     * Kirjoittaa JSONObjectin annetussa polussa olevaan tiedostoon
     * @param polku
     * @param data 
     */
    public static void kirjoita(String polku, JSONObject data) {
        PrintWriter kirjoittaja = null;
        try {
            kirjoittaja = new PrintWriter(polku, "UTF-8");
            kirjoittaja.print(data.toString());
            kirjoittaja.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeoJsonKirjoittaja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(GeoJsonKirjoittaja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            kirjoittaja.close();
        }
    }
}
