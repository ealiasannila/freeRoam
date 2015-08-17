/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tiedostonKasittely;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import reittienEtsinta.tietorakenteet.AluePolygoni;
import reittienEtsinta.tietorakenteet.Polygoni;

/**
 * Lukee GeoJson tiedoston ja muodostaa sen pohjalta polygoni taulukon
 *
 * @author elias
 */
public class GeoJsonLukija {

    int pisteita;
    double latmin;
    double latmax;
    double lonmin;
    double lonmax;

    public GeoJsonLukija() {
        this.pisteita = 0;
        this.latmax = Double.MIN_VALUE;
        this.lonmax = Double.MIN_VALUE;
        this.latmin = Double.MAX_VALUE;
        this.lonmin = Double.MAX_VALUE;

    }

    /**
     * Lukee GeoJson tiedoston ja muodostaa sen pohjalta polygoni taulukon
     * Polygonit on tallennettu joukkona koordinaattipisteita
     *
     * @param polku
     * @return
     */
    public Polygoni[] lueJson(String polku) {
        File file = new File(polku);

        Scanner lukija = null;
        try {
            lukija = new Scanner(file);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeoJsonLukija.class.getName()).log(Level.SEVERE, null, ex);
        }
        lukija.useDelimiter("\\Z");
        String data = lukija.next();

        JSONObject obj = new JSONObject(data);

        JSONArray arr = obj.getJSONArray("features");
        Polygoni[] polygonit = new Polygoni[arr.length()];

        for (int i = 0; i < arr.length(); i++) {

            Polygoni polygoni = null;
            if (arr.getJSONObject(i).getJSONObject("geometry").getString("type").equals("LineString")) {//viivamainen
                JSONArray pisteet = arr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                polygoni = this.luoPolygoni(pisteet, new Polygoni(pisteet.length()));
            } else if (arr.getJSONObject(i).getJSONObject("geometry").getString("type").equals("Polygon")) { //aluemainen
                JSONArray pisteet = arr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
                polygoni = this.luoPolygoni(pisteet, new AluePolygoni(pisteet.length()) );
            }
            if (this.latmax < polygoni.getLatmax()) {
                this.latmax = polygoni.getLatmax();
            }
            if (this.lonmax < polygoni.getLonmax()) {
                this.lonmax = polygoni.getLonmax();
            }
            if (this.latmin > polygoni.getLatmin()) {
                this.latmin = polygoni.getLatmin();
            }
            if (this.lonmin > polygoni.getLonmin()) {
                this.lonmin = polygoni.getLonmin();
            }
            polygonit[i] = polygoni;
        }
        return polygonit;
    }

    public double getLatmin() {
        return latmin;
    }

    public double getLatmax() {
        return latmax;
    }

    public double getLonmin() {
        return lonmin;
    }

    public double getLonmax() {
        return lonmax;
    }

    
    private Polygoni luoPolygoni(JSONArray pisteet, Polygoni uusi) {

        for (int i = 0; i < pisteet.length(); i++) {
            uusi.lisaaPiste(pisteet.getJSONArray(i).getDouble(1), pisteet.getJSONArray(i).getDouble(0), this.pisteita);
            this.pisteita++;
        }

        return uusi;
    }

    public int getPisteita() {
        return pisteita;
    }

}
