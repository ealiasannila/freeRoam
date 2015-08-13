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
import org.json.JSONArray;
import org.json.JSONObject;
import raster.Reitti;
import reittienEtsinta.Apumetodit;

/**
 * Verkko jossa lyhyimpien reittien etsintä tapahtuu. 
 * Verkko muodostetaan siten, että eri polygoineista muodostuvien alueiden välillä voi liikkua vain polygonin solmusta toiseen,
 * eikä kaaret saa leikata toista polygonia, tai polygonia itseään.
 * 
 * @author elias
 */
public class VerkkoPolygoni {

    private double[] lat;
    private double[] lon;
    private double[] alkuun;
    private double[] loppuun;
    private int[] polku;
    private int[] kekoindeksit;
    private double[][] vm;

    private int maalisolmu;

    private MaastoKirjastoPolygoni maastokirjasto;

    //ei tarvitse kertoa erikseen mistä minne, tai vieruslistoja naapurit tiedätään muutoinkin
    public VerkkoPolygoni(int solmujenMaara, int maalisolmu, MaastoKirjastoPolygoni maastokirjasto) {
        this.maalisolmu = maalisolmu;
        this.alkuun = new double[solmujenMaara];
        this.loppuun = new double[solmujenMaara];
        this.lat = new double[solmujenMaara];
        this.lon = new double[solmujenMaara];
        this.polku = new int[solmujenMaara];
        this.kekoindeksit = new int[solmujenMaara];
        this.vm = new double[solmujenMaara][solmujenMaara];

        this.maastokirjasto = maastokirjasto;
        this.alustus();

    }

    /**
     * lisää kaaren kun verkontekijä luokassa on ensin testattu, ettei se leikkaa mitään polygonia
     * @param solmu
     * @param kohde
     * @param lats
     * @param lons
     * @param latk
     * @param lonk 
     */
    public void lisaaKaari(int solmu, int kohde, double lats, double lons, double latk, double lonk) {
        //lisätään kaari verkkoon ja tarpeen vaatiessa alustetaan solmut
        //System.out.println(lons);
        //System.out.println(lonk);
        if (this.loppuun[solmu] < 0.001) {
            this.lat[solmu] = lats;
            this.lon[solmu] = lons;
            this.loppuun[solmu] = this.arvioiEtaisyys(solmu);

        }
        if (this.loppuun[kohde] < 0.001) {
            this.lat[kohde] = latk;
            this.lon[kohde] = lonk;
            this.loppuun[kohde] = this.arvioiEtaisyys(kohde);
        }

        double etaisyys = Apumetodit.pisteidenEtaisyys(lats, lons, latk, lonk);
        double aika = etaisyys / this.maastokirjasto.haeVauhti(solmu, kohde);

        this.vm[solmu][kohde] = aika; 
        this.vm[kohde][solmu] = aika;

    }

    private double arvioiEtaisyys(int solmu) {
        return Apumetodit.pisteidenEtaisyys(lat[solmu], lon[solmu], lat[this.maalisolmu], lon[this.maalisolmu]);
    }

    private void alustus() {
        for (int i = 0; i < this.alkuun.length; i++) {
            this.alkuun[i] = Double.MAX_VALUE;
            this.polku[i] = -1;
        }

        this.loppuun[this.maalisolmu] = 0;

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

    /**
     * laskee lyhyimmät etäisyydet maalisolmuun lähtien lähtösolmusta.
     * @param lahtoSolmu
     * @return 
     */
    public boolean aStar(int lahtoSolmu) {
        this.alkuun[lahtoSolmu] = 0;

        MinimiKekoPolygon keko = new MinimiKekoPolygon(this.alkuun, this.loppuun, this.kekoindeksit);

        for (int i = 0; i < this.alkuun.length; i++) {
            keko.lisaa(i);
        }

        while (!keko.tyhja()) {
            int solmu = keko.otaPienin();
            //   System.out.println("s: " + solmu);
            for (int naapuri = 0; naapuri < vm.length; naapuri++) {

                if (vm[solmu][naapuri] > 0.0001) {
                    //    System.out.println("n: " + naapuri);

                    if (loysaa(solmu, naapuri)) {
                        keko.paivita(naapuri);
                    }

                }
            }
        }
        if (this.alkuun[this.maalisolmu] == Long.MAX_VALUE) { //reittiä ei löytynyt
            return false;
        }

        return true;
    }

    public int[] getPolku() {
        return polku;
    }

    /**
     * palauttaa aStar metodin etsimän lyhyimmän reitin lähtö ja maalisolmun
     * välillä GeoJson muodossa
     *
     */
    
    
    public JSONObject lyhyinReitti(int lahtosolmu) {
        if (lahtosolmu == this.maalisolmu) {
            return null;
        }
        Pino pino = new Pino(this.alkuun.length);

        int seuraava = this.polku[this.maalisolmu];

        while (seuraava != lahtosolmu) {
            pino.lisaa(seuraava);
            seuraava = this.polku[seuraava];
        }

        JSONObject reitti = new JSONObject();
        reitti.put("type", "FeatureCollection");

        JSONObject properties = new JSONObject();
        properties.put("name", "urn:ogc:def:crs:EPSG::3047");

        JSONObject crs = new JSONObject();
        crs.put("type", "name");
        crs.put("properties", properties);

        reitti.put("crs", crs);

        JSONArray features = new JSONArray();

        JSONObject feature = new JSONObject();
        feature.put("type", "Feature");
        feature.put("properties", "{ }");

        JSONObject geometry = new JSONObject();
        geometry.put("type", "LineString");

        JSONArray coordinates = new JSONArray();

        double[] lahtopiste = new double[]{this.lon[lahtosolmu], this.lat[lahtosolmu]};
        coordinates.put(new JSONArray(lahtopiste));

        while (!pino.tyhja()) {
            int solmu = pino.ota();
            System.out.println(solmu);
            double[] reittipiste = new double[]{this.lon[solmu], this.lat[solmu]};
            coordinates.put(new JSONArray(reittipiste));

        }

        double[] maalipiste = new double[]{this.lon[this.maalisolmu], this.lat[this.maalisolmu]};
        coordinates.put(new JSONArray(maalipiste));

        geometry.put("coordinates", coordinates);
        feature.put("geometry", geometry);
        features.put(feature);

        reitti.put("features", features);

        return reitti;

    }

    /**
     * palauttaa verkon geojson muodossa, hyödyllistä koska verkon voi helposti visualisoida paikkatieto-ohjelmassa
     * @return 
     */
    public JSONObject getVerkko() {

        JSONObject verkko = new JSONObject();
        verkko.put("type", "FeatureCollection");

        JSONObject properties = new JSONObject();
        properties.put("name", "urn:ogc:def:crs:EPSG::3047");

        JSONObject crs = new JSONObject();
        crs.put("type", "name");
        crs.put("properties", properties);

        verkko.put("crs", crs);

        JSONArray features = new JSONArray();

        for (int i = 0; i < this.vm.length; i++) {
            for (int j = 0; j < this.vm[i].length; j++) {

                if (vm[i][j] < 0.001) {
                    continue;
                }

                JSONObject feature = new JSONObject();
                feature.put("type", "Feature");
                feature.put("properties", "{ }");

                JSONObject geometry = new JSONObject();
                geometry.put("type", "LineString");

                JSONArray coordinates = new JSONArray();

                double[] lahtopiste = new double[]{this.lon[i], this.lat[i]};
                coordinates.put(new JSONArray(lahtopiste));

                double[] maalipiste = new double[]{this.lon[j], this.lat[j]};
                coordinates.put(new JSONArray(maalipiste));

                geometry.put("coordinates", coordinates);
                feature.put("geometry", geometry);
                features.put(feature);
            }
        }

        verkko.put("features", features);
        return verkko;

    }

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
            builder.append("[ " + i + " ]");
            for (int j = 0; j < this.vm[i].length; j++) {
                String s = String.format("%.1f", this.vm[i][j]);
                builder.append("[" + s + "]");
            }

            builder.append("\n");
        }
        return builder.toString();
    }

}
