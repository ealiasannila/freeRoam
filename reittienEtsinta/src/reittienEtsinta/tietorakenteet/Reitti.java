/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;
import reittienEtsinta.Apumetodit;

/**
 * Kuvaa toteutunutta reittiä, tai annettua reittiehdotusta.
 *
 * @author elias
 */
public class Reitti {

    private double[] lon;
    private double[] lat;
    private int[] aika;

    public Reitti(double[] lon, double[] lat, int[] aika) {
        this.lon = lon;
        this.lat = lat;
        this.aika = aika;
    }

    public double[] getLon() {
        return lon;
    }

    public double[] getLat() {
        return lat;
    }

    public int[] getAika() {
        return aika;
    }

    /**
     * laskee kahden reitin pisteen välisen matkan.
     *
     * @param alku
     * @param loppu
     * @return
     */
    private double matka(int alku, int loppu) {
        double matka = 0;
        for (int i = alku; i < loppu; i++) {

            matka += Apumetodit.pisteidenEtaisyys(this.lon[i], this.lat[i], this.lon[i + 1], this.lat[i + 1]);
        }
        return matka;
    }

    /**
     * laskee kahden reitin pisteen välisen ajan
     *
     * @param alku
     * @param loppu
     * @return
     */
    private double aika(int alku, int loppu) {
        return aika[loppu] - aika[alku];
    }

    /**
     * laskee kahden reitin pisteen välillä kuljetun vauhdin
     *
     * @param alku
     * @param loppu
     * @return
     */
    public double vauhti(int alku, int loppu) {
      /*  System.out.println("m: " + this.matka(alku, loppu));
        System.out.println("a: " + this.aika(alku, loppu));

        System.out.println("v: " + (this.matka(alku, loppu) / this.aika(alku, loppu)));
        */return this.matka(alku, loppu) / this.aika(alku, loppu);
    }

    public String toString() {

        return "lon: " + Arrays.toString(this.lon)
                + "\nlat: " + Arrays.toString(this.lat)
                + "\naika: " + Arrays.toString(this.aika);
    }

}
