/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;
import reittienEtsinta.Apumetodit;

/**
 * Polygoni tietorakenne, joka tarjoaa metodit joilla voidaan testata leikkaako
 * viiva polygonia, tai kuinka lähellä piste on polygonin reunoja.
 *
 * @author elias
 */
public class Polygoni {

    protected double[] lat;
    protected double[] lon;
    protected int[] id;
    protected int maasto;

    public int getMaasto() {
        return maasto;
    }

    public void setMaasto(int maasto) {
        this.maasto = maasto;
    }

    //boundingbox
    protected double latmin;
    protected double latmax;
    protected double lonmin;
    protected double lonmax;

    private int pisteita;

    public Polygoni(int pisteidenMaara) {
        this.pisteita = 0;
        this.latmin = Double.MAX_VALUE;
        this.latmax = Double.MIN_VALUE;
        this.lonmin = Double.MAX_VALUE;
        this.lonmax = Double.MIN_VALUE;

        this.lat = new double[pisteidenMaara];
        this.lon = new double[pisteidenMaara];
        this.id = new int[pisteidenMaara];

    }

    public int getPisteita() {
        return pisteita;
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

    private boolean janeEiBBnSisalla(double lat1, double lon1, double lat2, double lon2) {
        return ((lat1 <= this.latmin && lat2 <= this.latmin) || (lat1 >= this.latmax && lat2 >= this.latmax)
                || (lon1 <= this.lonmin && lon2 <= this.lonmin) || (lon1 >= this.lonmax && lon2 >= this.lonmax));
    }

    /**
     * testaa viivojen pisteistä muodostettujen kolmioiden kiertosuuntiin
     * perustuen leikkaako viiva polygonin
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return
     */
    public boolean janaLeikkaaPolygonin(double lat1, double lon1, double lat2, double lon2) {

        if (this.janeEiBBnSisalla(lat1, lon1, lat2, lon2)){
            return false;
        }
        for (int i = 0; i < this.lat.length - 1; i++) {
            if (janatLeikkaavat(lat1, lon1, lat2, lon2, this.lat[i], this.lon[i], this.lat[i + 1], this.lon[i + 1])) {
                return true;
            }
        }
        return false;

    }

    /**
     * testaa kohtaavatko kaksi viivaa päässä, jos kohtaavat emme halua tulkita
     * sitä leikkaukseksi
     *
     * @param latp1
     * @param lonp1
     * @param latp2
     * @param lonp2
     * @param latq1
     * @param lonq1
     * @param latq2
     * @param lonq2
     * @return
     */
    private boolean janatKohtaavatPaassa(double latp1, double lonp1, double latp2, double lonp2, double latq1, double lonq1, double latq2, double lonq2) {
        return Apumetodit.pisteSama(latp1, lonp1, latq1, lonq1) || Apumetodit.pisteSama(latp1, lonp1, latq2, lonq2)
                || Apumetodit.pisteSama(latp2, lonp2, latq2, lonq2) || Apumetodit.pisteSama(latp2, lonp2, latq1, lonq1);
    }

    /**
     * testaa leikkaavatko kaksi janaa
     *
     * @param latp1
     * @param lonp1
     * @param latp2
     * @param lonp2
     * @param latq1
     * @param lonq1
     * @param latq2
     * @param lonq2
     * @return
     */
    protected boolean janatLeikkaavat(double latp1, double lonp1, double latp2, double lonp2, double latq1, double lonq1, double latq2, double lonq2) {
        if (this.janatKohtaavatPaassa(latp1, lonp1, latp2, lonp2, latq1, lonq1, latq2, lonq2)) {
            return false;
        }
        int p1p2q1 = kiertosuunta(latp1, lonp1, latp2, lonp2, latq1, lonq1);
        int p1p2q2 = kiertosuunta(latp1, lonp1, latp2, lonp2, latq2, lonq2);
        int q1q2p1 = kiertosuunta(latq1, lonq1, latq2, lonq2, latp1, lonp1);
        int q1q2p2 = kiertosuunta(latq1, lonq1, latq2, lonq2, latp2, lonp2);

        return (p1p2q1 != p1p2q2 && q1q2p1 != q1q2p2);

    }

    /**
     * kertoo onko kolmen pisteen kiertosuunta myötä (-1) vai vastapäivään(1),
     * vai ovatko ne samalla viivalla(0)
     *
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @param lat3
     * @param lon3
     * @return
     */
    private int kiertosuunta(double lat1, double lon1, double lat2, double lon2, double lat3, double lon3) {
        double erotus = (lat2 - lat1) * (lon3 - lon2) - (lat3 - lat2) * (lon2 - lon1);
        if (erotus < 0) {
            return -1;
        }
        if (erotus > 0) {
            return 1;
        } else {
            return 0;
        }

    }

    /**
     * Lisää plygoniin pisteen, ja päivittää sen boundingboxin arvoja
     *
     * @param lat
     * @param lon
     * @param id
     */
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

    /**
     * antaa pisteen etäisyyden polygonin kaarista. Ei testaa etäisyyttä
     * ensimmäisen ja viimeisen pisteeen väliseen kaareen
     *
     * @param lat
     * @param lon
     * @return
     */
    public double pisteenEtaisyys(double lat, double lon) {
        double etaisyys = Double.MAX_VALUE;
        for (int i = 0; i < this.lat.length - 1; i++) {
            double ehdokas = this.pisteJanasta(lat, lon, this.lat[i], this.lon[i], this.lat[i + 1], this.lon[i + 1]);
            if (etaisyys > ehdokas) {
                etaisyys = ehdokas;
            }
        }
        return etaisyys;
    }

    /**
     * antaa pisteen etäsiyyden janasta
     *
     * @param latp
     * @param lonp
     * @param latj1
     * @param lonj1
     * @param latj2
     * @param lonj2
     * @return
     */
    protected double pisteJanasta(double latp, double lonp, double latj1, double lonj1, double latj2, double lonj2) {
        double jananpituusToiseen = Apumetodit.pisteidenEtaisyysToiseen(latj1, lonj1, latj2, lonj2);
        if (jananpituusToiseen == 0) {
            return Apumetodit.pisteidenEtaisyys(latp, lonp, latj1, lonj1);
        }
        double latjana = latj2 - latj1;
        double lonjana = lonj2 - lonj1;

        double kerroin = ((lonp - lonj1) * lonjana + (latp - latj1) * latjana) / jananpituusToiseen;
        if (kerroin < 0.0) {
            return Apumetodit.pisteidenEtaisyys(latj1, lonj1, latp, lonp);
        }
        else if (kerroin > 1.0) {
            return Apumetodit.pisteidenEtaisyys(latj2, lonj2, latp, lonp);
        }
        return Apumetodit.pisteidenEtaisyys(latp, lonp,
                latj1 + kerroin * latjana,
                lonj1 + kerroin * lonjana);
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
