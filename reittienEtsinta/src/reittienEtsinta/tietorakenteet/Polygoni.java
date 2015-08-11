/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;

/**
 *
 * @author elias
 */
public class Polygoni {

    private double[] lat;
    private double[] lon;
    private int[] id;

    //boundingbox
    private double latmin;
    private double latmax;
    private double lonmin;
    private double lonmax;

    private int pisteita;

    public Polygoni(int pisteidenMaara) {
        this.pisteita = 0;
        this.latmin = Double.MAX_VALUE;
        this.latmax = Double.MIN_VALUE;
        this.lonmin = Double.MAX_VALUE;
        this.lonmax = Double.MIN_VALUE;

        this.lat = new double[pisteidenMaara];
        this.lon = new double[pisteidenMaara];

    }

    public boolean pisteSisalla(double pLat, double pLon) {
        if (pLat < this.latmin || pLat > this.latmax || pLon < this.lonmin || pLon > this.lonmax) {
            return false;
        }

        int leikkaukset = 0;

        for (int i = 0; i < this.lat.length; i++) {

            int loppu = i + 1;
            if (i == this.lat.length - 1) { //viimeisestä pisteesta takaisin ekaan
                loppu = 0;
            }

            if ((this.lat[i] <= pLat && this.lat[loppu] > pLat) || (this.lat[i] > pLat && this.lat[loppu] <= pLat)) {
                double osuusViivasta = (pLat - this.lat[i]) / (this.lat[loppu] - this.lat[i]);
                if (pLon < this.lon[i] + osuusViivasta * (this.lon[loppu] - this.lon[i])) {
                    leikkaukset++;
                }

            }
        }

        return leikkaukset % 2 != 0;

    }

    public boolean viivaLeikkaaPolygonin(double lat1, double lon1, double lat2, double lon2) {
        if ((lat1 < this.latmin && lat2 < this.latmin) || (lat1 > this.latmax && lat2 > this.latmax)
                || (lon1 < this.lonmin && lon2 < this.lonmin) || (lon1 > this.lonmax && lon2 > this.lonmax)) {
            return false;
        }

        for (int i = 0; i < this.lat.length; i++) {
            int loppu = i + 1;
            if (i == this.lat.length - 1) { //viimeisestä pisteesta takaisin ekaan
                loppu = 0;
            }
            if (viivatLeikkaavat(lat1, lon1, lat2, lon2, this.lat[i], this.lat[loppu], this.lon[i], this.lon[loppu])) {
                return true;
            }
        }
        return false;

    }

    //ei toimi jos viivat samansuuntaiset
    private boolean viivatLeikkaavat(double latp1, double lonp1, double latp2, double lonp2, double latq1, double lonq1, double latq2, double lonq2) {
        return (myotapaiva(latp1, lonp1, latp2, lonp2, latq1, lonq1) != myotapaiva(latp1, lonp1, latp2, lonp2, latq2, lonq2))
                && (myotapaiva(latq1, lonq1, latq2, lonq2, latp1, lonp1) != myotapaiva(latq1, lonq1, latq2, lonq2, latp2, lonp2));
    }

    private boolean myotapaiva(double lat1, double lon1, double lat2, double lon2, double lat3, double lon3) {
        return kulmakerroin(lat1, lon1, lat2, lon2) > kulmakerroin(lat2, lon2, lat3, lon3);
    }

    private double kulmakerroin(double lat1, double lon1, double lat2, double lon2) {
        return (lat2 - lat1) / (lon2 - lon1);
    }

    public void lisaaPiste(double lat, double lon, int id) {
        if (lat < this.latmin) {
            this.latmin = lat;
        }
        if (lat > this.latmax) {
            this.latmax = lat;
        }
        if (lon < this.lonmin) {
            this.lonmin = lon;
        }
        if (lon > this.lonmax) {
            this.lonmax = lon;
        }

        this.lat[pisteita] = lat;
        this.lon[pisteita] = lon;
        this.id[pisteita] = id;
        
        this.pisteita++;
    }

    public double[] getLat() {
        return lat;
    }

    public double[] getLon() {
        return lon;
    }

    public int[] getId() {
        return id;
    }

    public double getBBlat() {
        return (this.latmax - this.latmin) / 2 + this.latmin;
    }

    public double getBBlon() {
        return (this.lonmax - this.lonmin) / 2 + this.lonmin;

    }

    public String toString() {
        return Arrays.toString(lat) + "\n" + Arrays.toString(lon);
    }

}
