/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tiedostonKasittely;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import reittienEtsinta.tietorakenteet.AluePolygoni;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Lista;
import reittienEtsinta.tietorakenteet.Reitti;

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
  

    private JSONObject lataaJsonObject(File tiedosto) {

        Scanner lukija = null;
        try {
            lukija = new Scanner(tiedosto);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeoJsonLukija.class.getName()).log(Level.SEVERE, null, ex);
        }
        lukija.useDelimiter("\\Z");
        String data = lukija.next();

        return new JSONObject(data);
    }

    public Reitti lueReitti(String polku){
        return this.lueReitti(new File(polku));
    }
    
    public Reitti lueReitti(File tiedosto) {
        JSONObject obj = this.lataaJsonObject(tiedosto);
        JSONArray arr = obj.getJSONArray("features");

        double[] lat = new double[arr.length()];
        double[] lon = new double[arr.length()];
        int[] aika = new int[arr.length()];

        for (int i = 0; i < arr.length(); i++) {
            JSONArray piste = arr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
            lat[i] = piste.getDouble(1);
            lon[i] = piste.getDouble(0);

            String aikaString = arr.getJSONObject(i).getJSONObject("properties").getString("time");

            DateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.ENGLISH);
            Date timestamp = null;
            try {
                timestamp = format.parse(aikaString);
            } catch (ParseException ex) {
                System.out.println("format ex");
            }
            int sekunnit = (int) timestamp.getTime() / 1000;
            aika[i] = sekunnit;

        }
        Reitti reitti = new Reitti(lon, lat, aika);
        return reitti;
    }

    public void luePolygonit(String polku, int maasto, Lista lista){
        File tiedosto = new File(polku);

        this.luePolygonit(tiedosto, maasto, lista);
    }
    
    public void luePolygonit(File tiedosto, int maasto, Lista lista) {
        JSONObject obj = this.lataaJsonObject(tiedosto);

        JSONArray arr = obj.getJSONArray("features");

        for (int i = 0; i < arr.length(); i++) {

            Polygoni polygoni = null;
            if (arr.getJSONObject(i).getJSONObject("geometry").getString("type").equals("LineString")) {//viivamainen
                JSONArray pisteet = arr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates");
                polygoni = this.luoPolygoni(maasto, pisteet, new Polygoni(pisteet.length()));
            } else if (arr.getJSONObject(i).getJSONObject("geometry").getString("type").equals("Polygon")) { //aluemainen
                JSONArray pisteet = arr.getJSONObject(i).getJSONObject("geometry").getJSONArray("coordinates").getJSONArray(0);
                polygoni = this.luoPolygoni(maasto, pisteet, new AluePolygoni(pisteet.length()));
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
            lista.lisaa(polygoni);
        }
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

    private Polygoni luoPolygoni(int maasto, JSONArray pisteet, Polygoni uusi) {

        for (int i = 0; i < pisteet.length(); i++) {
            uusi.lisaaPiste(pisteet.getJSONArray(i).getDouble(1), pisteet.getJSONArray(i).getDouble(0), this.pisteita);
            this.pisteita++;
        }
        uusi.setMaasto(maasto);
        return uusi;
    }

    public int getPisteita() {
        return pisteita;
    }

}
