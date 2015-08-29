/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.Arrays;

/**
 * Laajentaa Polygoni luokkaa tarjoamalla metodin sen testaamiseksi, onko piste
 * polygonin sisällä.
 *
 *
 * @author elias
 */
public class AluePolygoni extends Polygoni {

    public AluePolygoni(int pisteidenMaara) {
        super(pisteidenMaara);
    }
    

    /**
     * tarkistaa pisteen etäisyyden viimeiseen kaareen, muutoin sama kuin Polygon luokassa
     * @param lat
     * @param lon
     * @return 
     */
    @Override
    public double pisteenEtaisyys(double lat, double lon) {
        return Math.min(super.pisteenEtaisyys(lat, lon), this.pisteJanasta(lat, lon, this.lat[this.lat.length-1], this.lon[this.lat.length-1], this.lat[0], this.lon[0])); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * tarkistaa leikkaako jana Polygonin viimeisestä solmusta ensimmäiseen kulkevan kaaren, normi janaLeikkaPolygonin operaation lisäksi
     * @param lat1
     * @param lon1
     * @param lat2
     * @param lon2
     * @return 
     */
    @Override
    public boolean janaLeikkaaPolygonin(double lat1, double lon1, double lat2, double lon2) {
        if (this.janatLeikkaavat(lat1, lon1, lat2, lon2, this.lat[this.lat.length - 1], this.lon[this.lat.length - 1], this.lat[0], this.lon[0])) {

            return true;
        }
        return super.janaLeikkaaPolygonin(lat1, lon1, lat2, lon2); //To change body of generated methods, choose Tools | Templates.
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
            return false;
        }
        int leikkaukset = 0;

        for (int i = 0; i < this.lat.length; i++) {
            int loppu = i + 1;
            if (i == this.lat.length - 1) { //viimeisestä pisteesta takaisin ekaan
                loppu = 0;
            }
            if ((this.lat[i] <= pLat && this.lat[loppu] > pLat) || (this.lat[i] > pLat && this.lat[loppu] <= pLat)) { //kaari leikkaa pisteen lat koordinaatin
                double osuusViivasta = (pLat - this.lat[i]) / (this.lat[loppu] - this.lat[i]);
                double leikkauslon = this.lon[i] + osuusViivasta * (this.lon[loppu] - this.lon[i]);
                if (pLon < leikkauslon) { //kaari leikkaa pisteen lat koordinaatin pisteen itäpuolella 
                    leikkaukset++;
                }
            }
        }
        return leikkaukset % 2 != 0;
    }
}
