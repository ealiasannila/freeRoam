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
    public static boolean kirjoita(String polku, JSONObject data) {
        PrintWriter kirjoittaja = null;
        try {
            kirjoittaja = new PrintWriter(polku, "UTF-8");
            kirjoittaja.print(data.toString());
            kirjoittaja.close();
        } catch (FileNotFoundException ex) {
            return false;
        } catch (UnsupportedEncodingException ex) {
            return false;
        }
        return true;
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
            if (i == kirjoitettava.getAika().length - 1) {
                properties.put("length", 0);
                properties.put("time", 0);
                properties.put("speed", 0);

            } else {
                properties.put("length", kirjoitettava.matka(i, i + 1));
                properties.put("time", kirjoitettava.aika(i, i + 1));
                if (kirjoitettava.aika(i, i + 1) == 0) {
                    properties.put("speed", -1);

                } else {
                    double vauhti = (double)kirjoitettava.matka(i, i + 1) / (double)kirjoitettava.aika(i, i + 1);
                    properties.put("speed", vauhti);
                }

            }
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
