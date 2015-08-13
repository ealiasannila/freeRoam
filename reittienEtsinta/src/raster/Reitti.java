/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raster;

import java.util.Arrays;
import reittienEtsinta.Apumetodit;
import raster.KuvanLukija;

/**
 * Kuvaa toteutunutta reittiä, tai annettua reittiehdotusta.
 *
 * @author elias
 */
public class Reitti {

    private int[] x;
    private int[] y;
    private int[] aika;

    public Reitti(int[] x, int[] y, int[] aika) {
        this.x = x;
        this.y = y;
        this.aika = aika;
    }

    public int[] getX() {
        return x;
    }

    public int[] getY() {
        return y;
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

            matka += Apumetodit.pisteidenEtaisyys(this.x[i], this.y[i], this.x[i + 1], this.y[i + 1]);
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
    private int aika(int alku, int loppu) {
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
        return this.matka(alku, loppu) / this.aika(alku, loppu);
    }

    public String toString() {
        return "X: " + Arrays.toString(this.x) + 
                "\nY: " + Arrays.toString(this.y) + 
                "\nA: " + Arrays.toString(this.aika);
    }

}
