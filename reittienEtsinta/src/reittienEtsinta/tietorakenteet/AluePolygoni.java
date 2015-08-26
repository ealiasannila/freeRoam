/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;

/**
 * Laajentaa Polygoni luokkaa tarjoamalla metodin sen testaamiseksi, onko piste polygonin sisällä.
 * 
 *
 * @author elias
 */
public class AluePolygoni extends Polygoni {

    public AluePolygoni(int pisteidenMaara) {
        super(pisteidenMaara);
        alue = true;
    }

    /**
     * testaa raycasting menetelmän avulla onko piste polygonin sisällä
     *
     * @param pLat
     * @param pLon
     * @return
     */
    public boolean pisteSisalla(double pLat, double pLon) {
        if (pLat < this.latmin || pLat > this.latmax || pLon < this.lonmin || pLon > this.lonmax) {
       //     System.out.println("bbout");

            return false;
        }
        //System.out.println("bbin");

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

}
