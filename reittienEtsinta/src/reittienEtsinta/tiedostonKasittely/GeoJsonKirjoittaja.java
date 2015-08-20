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
import org.json.JSONArray;
import org.json.JSONObject;
import reittienEtsinta.tietorakenteet.Reitti;

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
    
    public static JSONObject muunnaJson(Reitti kirjoitettava){
        JSONObject reitti = new JSONObject();
        reitti.put("type", "FeatureCollection");

        JSONObject properties = new JSONObject();
        properties.put("name", "urn:ogc:def:crs:EPSG::3047");

        JSONObject crs = new JSONObject();
        crs.put("type", "name");
        crs.put("properties", properties);

        reitti.put("crs", crs);

        JSONArray features = new JSONArray();

        JSONObject feature = new JSONObject();
        feature.put("type", "Feature");
        feature.put("properties", "{ }");

        JSONObject geometry = new JSONObject();
        geometry.put("type", "LineString");

        JSONArray coordinates = new JSONArray();

        for (int i = 0; i < kirjoitettava.getAika().length; i++) {
            
            double[] reittipiste = new double[]{kirjoitettava.getLon()[i], kirjoitettava.getLat()[i]};
            coordinates.put(new JSONArray(reittipiste));

        }

        

        geometry.put("coordinates", coordinates);
        feature.put("geometry", geometry);
        features.put(feature);

        reitti.put("features", features);

        return reitti;

    }
    
}
