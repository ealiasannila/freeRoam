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
 *
 * @author elias
 */
public class GeoJsonKirjoittaja {

    /**
     * Kirjoittaa JSONObjectin annetussa polussa olevaan tiedostoon
     *
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

    private static JSONObject perusJson() {
        JSONObject perusjson = new JSONObject();
        perusjson.put("type", "FeatureCollection");

        JSONObject pisteetcrsproperties = new JSONObject();
        pisteetcrsproperties.put("name", "urn:ogc:def:crs:EPSG::3047");

        JSONObject pisteetcrs = new JSONObject();
        pisteetcrs.put("type", "name");
        pisteetcrs.put("properties", pisteetcrsproperties);

        perusjson.put("crs", pisteetcrs);
        return perusjson;
    }

    public static JSONObject munnaJsonPisteet(Reitti kirjoitettava) {

        JSONObject reittipisteet = perusJson();

        JSONArray pistefeatures = new JSONArray();

        for (int i = 0; i < kirjoitettava.getAika().length; i++) {
            double[] reittipiste = new double[]{kirjoitettava.getLon()[i], kirjoitettava.getLat()[i]};

            JSONObject pisteenGeometry = new JSONObject();
            pisteenGeometry.put("type", "point");
            pisteenGeometry.put("coordinates", reittipiste);

            JSONObject properties = new JSONObject();
            properties.put("timefromstart", kirjoitettava.getAika()[i]);
            properties.put("lat", kirjoitettava.getLat()[i]);
            properties.put("lon", kirjoitettava.getLon()[i]);

            JSONObject pointFeature = new JSONObject();
            pointFeature.put("properties", properties);
            pointFeature.put("geometry", pisteenGeometry);
            pointFeature.put("type", "Feature");

            pistefeatures.put(pointFeature);
        }

        reittipisteet.put("features", pistefeatures);
        return reittipisteet;
    }

    public static JSONObject muunnaJsonReitti(Reitti kirjoitettava) {
        JSONObject reitti = perusJson();

        JSONArray features = new JSONArray();
        JSONObject feature = new JSONObject();

        feature.put("type", "Feature");
        JSONObject properties = new JSONObject();
        feature.put("properties", properties);

        JSONObject geometry = new JSONObject();

        JSONArray coordinates = new JSONArray();

        for (int i = 0; i < kirjoitettava.getAika().length; i++) {

            double[] reittipiste = new double[]{kirjoitettava.getLon()[i], kirjoitettava.getLat()[i]};
            coordinates.put(new JSONArray(reittipiste));

        }

        geometry.put("coordinates", coordinates);

        geometry.put("type", "LineString");
        feature.put("geometry", geometry);
        features.put(feature);

        reitti.put("features", features);

        return reitti;

    }

}
