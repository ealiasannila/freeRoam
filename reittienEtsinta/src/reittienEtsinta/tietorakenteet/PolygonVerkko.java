/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.PriorityQueue;
import reittienEtsinta.Apumetodit;

/**
 *
 * @author elias
 */
public class PolygonVerkko {

    private double[] lat;
    private double[] lon;
    private double[] alkuun;
    private double[] loppuun;
    private int[] polku;
    private int[] kekoindeksit;
    private double[][] vm;

    private int maalisolmu;

    //ei tarvitse kertoa erikseen mistä minne, tai vieruslistoja naapurit tiedätään muutoinkin
    public PolygonVerkko(int solmujenMaara) {
        this.alkuun = new double[solmujenMaara];
        this.loppuun = new double[solmujenMaara];
        this.lat = new double[solmujenMaara];
        this.lon = new double[solmujenMaara];
        this.polku = new int[solmujenMaara];
        this.kekoindeksit = new int[solmujenMaara];
        this.vm = new double[solmujenMaara][solmujenMaara];

    }

    public void lisaaKaari(int solmu, int kohde, double lats, double lons, double latk, double lonk) {
        //lisätään kaari verkkoon ja tarpeen vaatiessa alustetaan solmut
        if (this.loppuun[solmu] == Double.MAX_VALUE) {
            this.lat[solmu] = lats;
            this.lon[solmu] = lons;
            this.loppuun[solmu] = this.arvioiEtaisyys(solmu);

        }
        if (this.loppuun[kohde] == Double.MAX_VALUE) {
            this.lat[kohde] = latk;
            this.lat[kohde] = lonk;
            this.loppuun[kohde] = this.arvioiEtaisyys(kohde);
        }

        this.vm[solmu][kohde] = Apumetodit.pisteidenEtaisyys(lats, lons, latk, lonk);  //matka, muutetaan ajaksi maaston vauhdin mukaan
        this.vm[kohde][solmu] = Apumetodit.pisteidenEtaisyys(lats, lons, latk, lonk);

    }

    private double arvioiEtaisyys(int solmu) {
        return Apumetodit.pisteidenEtaisyys(lat[solmu], lon[solmu], lat[this.maalisolmu], lon[this.maalisolmu]);
    }

    private void alustus(int lahtoSolmu, int maaliSolmu) {
        for (int i = 0; i < this.alkuun.length; i++) {
            this.alkuun[i] = Double.MAX_VALUE;
            this.polku[i] = -1;
        }

        this.alkuun[lahtoSolmu] = 0;
        this.loppuun[maaliSolmu] = 0;

        this.maalisolmu = maaliSolmu;
    }

    private boolean loysaa(int solmu, int naapuri) {
        if (this.alkuun[solmu] == Double.MAX_VALUE) {
            return false;
        }
        if (this.alkuun[naapuri] > this.alkuun[solmu] + vm[solmu][naapuri]) {
            this.alkuun[naapuri] = this.alkuun[solmu] + vm[solmu][naapuri];
            this.polku[naapuri] = solmu;
            return true;
        }
        return false;
    }
    /*
     public boolean aStar(int lahtoSolmu, int maaliSolmu) {
     alustus(lahtoSolmu, maaliSolmu);

     MinimiKeko keko = new MinimiKeko(vm.length);

     for (int i = 0; i < vm.length; i++) {
     keko.lisaa(solmut[i]);
     }

     while (!keko.tyhja()) {
     System.out.println(keko);

     int solmu = keko.otaPienin().getId();
     System.out.println(solmu);

     for (int naapuri = 0; naapuri < vm.length; naapuri++) {

     if (solmu == maaliSolmu) {    //voiko jo pysäyttää?
     return true;
     }

     if (vm[solmu][naapuri] != 0) {
     if (loysaa(solmu, naapuri)) {
     keko.paivita(solmut[naapuri]);
     }

     }
     }
     }
     if (this.alkuun[maaliSolmu] == Long.MAX_VALUE) { //reittiä ei löytynyt
     return false;
     }

     return true;
     }
     */

    public String toString() {
        StringBuilder builder = new StringBuilder();
        StringBuilder koord = new StringBuilder();

        koord.append("[   ]");
        for (int i = 0; i < this.vm.length; i++) {
            koord.append("[ " + i + " ]");
        }
        koord.append("\n");
        builder.append(koord);
        for (int i = 0; i < this.vm.length; i++) {
            builder.append("[ "+i+" ]");
            for (int j = 0; j < this.vm[i].length; j++) {
                String s = String.format("%.1f", this.vm[i][j]);
                builder.append("[" + s + "]");
            }

            builder.append("\n");
        }
        return builder.toString();
    }

}
