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
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import raster.Reitti;

/**
 * Lukee gpx tedostosta koordinaatit ja muodostaa niiden perusteella uuden
 * reitin.
 *
 * @author elias
 */
public class GPXLukija {

    private double kuvaVasenAlaX; //kuvan lat/lon koordinaatit
    private double kuvaVasenAlaY;

    private double kuvaOikeaYlaX;
    private double kuvaOikeaYlaY;

    private int kuvaKoko; //kuvan sivun pituus

    private Scanner gpxScanner;

    private double[] xKoord;
    private double[] yKoord;
    private Date[] ajat;

    private int pisteidenMaara;

    public GPXLukija(double kuvaVasenAlaX, double kuvaVasenAlaY, double kuvaOikeaYlaX, double kuvaOikeaYlaY, int kuvaKoko, String gpxPolku) {
        this.kuvaVasenAlaX = kuvaVasenAlaX;
        this.kuvaVasenAlaY = kuvaVasenAlaY;
        this.kuvaOikeaYlaX = kuvaOikeaYlaX;
        this.kuvaOikeaYlaY = kuvaOikeaYlaY;
        this.kuvaKoko = kuvaKoko;

        this.xKoord = new double[3000]; //max pisteiden määrä gpx tiedostossa
        this.yKoord = new double[3000]; //max pisteiden määrä gpx tiedostossa
        this.ajat = new Date[3000]; //max pisteiden määrä gpx tiedostossa
        this.pisteidenMaara = 0;

        try {
            this.gpxScanner = new Scanner(new File(gpxPolku));
        } catch (FileNotFoundException ex) {
            System.out.println("Skannerin luonti ei onnistu");
        }
    }

    public Reitti lueGpx() {
        this.lueKoordinaatit();
        return new Reitti(this.kasitteleX(), this.kasitteleY(), this.kasitteleAjat());
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
                this.xKoord[this.pisteidenMaara] = lond;

                this.gpxScanner.findInLine("lat=\"");

                String lat = this.gpxScanner.next();
                lat = lat.substring(0, lat.length() - 2);

                double latd;
                try {
                    latd = Double.parseDouble(lat);
                } catch (Exception e) {
                    latd = -1.0;
                }
                this.yKoord[this.pisteidenMaara] = latd;

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

    private int[] kasitteleAjat() {
        int[] ajatS = new int[this.pisteidenMaara]; //ajat sekunteina

        for (int i = 0; i < this.pisteidenMaara; i++) {
            ajatS[i] = (int) (this.ajat[i].getTime() - this.ajat[0].getTime()) / 1000;
        }

        return ajatS;
    }

    private int laskeXPiste(double lond) {

        return (int) (this.kuvaKoko / (this.kuvaOikeaYlaX - this.kuvaVasenAlaX) * (lond - kuvaVasenAlaX));

    }

    private int laskeYPiste(double latd) {

        return this.kuvaKoko - (int) (this.kuvaKoko / (this.kuvaOikeaYlaY - this.kuvaVasenAlaY) * (latd - kuvaVasenAlaY));

    }

    private int[] kasitteleX() {
        int[] xPisteet = new int[this.pisteidenMaara];

        for (int i = 0; i < this.pisteidenMaara; i++) {
            xPisteet[i] = this.laskeXPiste(this.xKoord[i]);
        }
        return xPisteet;
    }

    private int[] kasitteleY() {
        int[] yPisteet = new int[this.pisteidenMaara];

        for (int i = 0; i < this.pisteidenMaara; i++) {
            yPisteet[i] = this.laskeYPiste(this.yKoord[i]);
        }
        return yPisteet;
    }

}
