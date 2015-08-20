/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kaatopaikka;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import reittienEtsinta.tietorakenteet.Reitti;

/**
 * Lukee gpx tedostosta koordinaatit ja muodostaa niiden perusteella uuden
 * reitin.
 *
 * @author elias
 */
public class GPXLukijaPolygoni {

    private Scanner gpxScanner;

    private double[] lon;
    private double[] lat;
    private Date[] ajat;

    private int pisteidenMaara;

    public GPXLukijaPolygoni(String gpxPolku) {
  
        this.lon = new double[3000]; //max pisteiden määrä gpx tiedostossa
        this.lat = new double[3000]; //max pisteiden määrä gpx tiedostossa
        this.ajat = new Date[3000]; //max pisteiden määrä gpx tiedostossa
        this.pisteidenMaara = 0;

        try {
            this.gpxScanner = new Scanner(new File(gpxPolku));
        } catch (FileNotFoundException ex) {
            System.out.println("Skannerin luonti ei onnistu");
        }
    }

    /**
     * lukee koordinaatit tiedostosta ja luo uuden reittiolion
     * @return 
     */
    public Reitti lueGpx() {
        this.lueKoordinaatit();
        return new Reitti(this.kasitteleKoord(this.lon), this.kasitteleKoord(this.lat), this.kasitteleAjat());
    }

    /**
     * lyhentää koordinaatti taulukon... ei kovin eleganttia
     * @param koord
     * @return 
     */
    
    private double[] kasitteleKoord(double[] koord){
        double[] ulos = new double[this.pisteidenMaara];
        
        for (int i = 0; i < ulos.length; i++) {
            ulos[i] = koord[i];
        }
        return ulos;
    }
    
    private void lueKoordinaatit() {

        while (this.gpxScanner.hasNext()) {
            if (this.gpxScanner.findInLine("lon=\"") != null) {

                String lon = this.gpxScanner.next();
                lon = lon.substring(0, lon.length() - 1);
                double lond;
                try {
                    lond = Double.parseDouble(lon);
                } catch (Exception e) {
                    lond = -1.0;
                }
                this.lon[this.pisteidenMaara] = lond;

                this.gpxScanner.findInLine("lat=\"");

                String lat = this.gpxScanner.next();
                lat = lat.substring(0, lat.length() - 2);

                double latd;
                try {
                    latd = Double.parseDouble(lat);
                } catch (Exception e) {
                    latd = -1.0;
                }
                this.lat[this.pisteidenMaara] = latd;

                this.gpxScanner.nextLine();
                this.gpxScanner.findInLine("<time>");
                String time = this.gpxScanner.next();
                time = time.substring(0, time.length() - 8);
                time = time.replace('T', '-');

                DateFormat format = new SimpleDateFormat("yyyy-MM-dd-hh:mm:ss", Locale.ENGLISH);
                Date timestamp = null;
                try {
                    timestamp = format.parse(time);
                } catch (ParseException ex) {
                }

                this.ajat[this.pisteidenMaara] = timestamp;

                this.pisteidenMaara++;

            }
            this.gpxScanner.nextLine();
        }
    }
    
    /**
     * muuttaa ajat date-> sekunttia lähdöstä
     * @return 
     */

    private int[] kasitteleAjat() {
        int[] ajatS = new int[this.pisteidenMaara]; //ajat sekunteina

        for (int i = 0; i < this.pisteidenMaara; i++) {
            ajatS[i] = (int) (this.ajat[i].getTime() - this.ajat[0].getTime()) / 1000;
        }

        return ajatS;
    }



}
