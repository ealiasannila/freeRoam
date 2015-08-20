/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.ArrayList;
import java.util.List;

/**
 * Luo polygonien pohjalta verkon, jossa laskenta tapahtuu. Ideana muodostaa
 * jokaisen polygonin jokaisesta solmusta kaari jokaisen polygonin jokaiseen
 * solmuun, mikäli kaari ei leikkaa polygoneja. Kuitenkin tehostamiseksi
 * käytetään naapurustoja, eli alue jaetaan tietyn kokoisiin naapurustoihin, ja
 * kaaria muodostotetaan vain naapuruston sisällä, ja sitä naapurustoa
 * ympärövien naapurustojen kanssa.
 *
 * @author elias
 */
public class Verkontekija {

    private VerkkoPolygoni verkko;
    private PolygoniLista[][] naapurustot;
    private int pisteita;

    private double latmin;
    private double latmax;
    private double lonmin;
    private double lonmax;

    public Verkontekija(PolygoniLista polygonit, double latmin, double latmax, double lonmin, double lonmax, int pisteita, MaastoKirjastoPolygoni maastokirjasto) {
        this.pisteita = pisteita;
        int n = (int) this.pisteita / 300; //max solmujen maara naapurustossa
        n = Math.max(n, 1);

        System.out.println("n: " + n);

        this.naapurustot = new PolygoniLista[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.naapurustot[i][j] = new PolygoniLista(8);

            }
        }

        this.latmax = latmax;
        this.latmin = latmin;
        this.lonmax = lonmax;
        this.lonmin = lonmin;

        this.verkko = new VerkkoPolygoni(pisteita, maastokirjasto);

        this.lisaaPolygonit(polygonit);

    }

    private void lisaaPolygonit(PolygoniLista polygonit) {
        for (int i = 0; i < polygonit.koko(); i++) {
            this.lisaaPolygon(polygonit.ota(i));
        }
    }

    /**
     * lisää plygonin naapurustoon polygonin boundingboxin keskipisteen
     * perusteella
     *
     * @param poly
     */
    private void lisaaPolygon(Polygoni poly) {
        int x = (int) (((poly.getBBlat() - this.latmin) / (this.latmax - this.latmin)) * this.naapurustot.length);
        int y = (int) (((poly.getBBlon() - this.lonmin) / (this.lonmax - this.lonmin)) * this.naapurustot.length);
        /*
         System.out.println("bblat: " + poly.getBBlat());
         System.out.println("bblon: " + poly.getBBlon());

         System.out.println("latmin: " + this.latmin);
         System.out.println("latmax: " + this.latmax);
         System.out.println("lonmin: " + this.lonmin);
         System.out.println("lonmax: " + this.lonmax);

         System.out.println("y: " + y);
         System.out.println("x: " + x);
         */
        this.naapurustot[y][x].lisaa(poly);

    }

    /**
     * pyytää yrittämään kaarien asetuta jokaisesta solmusta
     */
    public void luoVerkko() {
        for (int i = 0; i < this.naapurustot.length; i++) {
            for (int j = 0; j < this.naapurustot[i].length; j++) {
                for (int k = 0; k < this.naapurustot[i][j].koko(); k++) {
                    Polygoni polygoni = this.naapurustot[i][j].ota(k);
                    for (int l = 0; l < polygoni.getId().length; l++) {
                        this.asetaKaaret(l, polygoni.getId()[l], polygoni.getLat()[l], polygoni.getLon()[l], j, i, polygoni);
                    }
                }
            }
        }
    }

    /**
     * asettaa kaaret solmusta sitä ympäröiviin naapurustoihin.
     *
     * @param id
     * @param lat
     * @param lon
     * @param naapurustoX
     * @param naapurustoY
     */
    private void asetaKaaret(int lahtosolmuIndeksi, int id, double lat, double lon, int naapurustoX, int naapurustoY, Polygoni lahto) {
        for (int i = -2; i <= 2; i++) {
            if (naapurustoY + i < 0 || naapurustoY + i >= this.naapurustot.length) {
                continue;
            }
            for (int j = -2; j <= 2; j++) {
                if (naapurustoX + j < 0 || naapurustoX + j >= this.naapurustot[naapurustoY + i].length) {
                    continue;
                }

                for (int k = 0; k < this.naapurustot[naapurustoY + i][naapurustoX + j].koko(); k++) {
                    Polygoni kohde = this.naapurustot[naapurustoY + i][naapurustoX + j].ota(k);
                    if (kohde.getId()[0] == lahto.getId()[0]) {
                        if (lahto.getClass() == AluePolygoni.class) {//alueamainen kohde, asetetaan kaaret alueen läpi joka solmuun
                            for (int l = 0; l < kohde.getId().length; l++) {
                                
                                if (kohde.getId()[l] != id) {
                                    
                                    if (lahtosolmuIndeksi  == l - 1 || lahtosolmuIndeksi  == l + 1 ) { //reunaa pitkin seuraava solmu, täytyy asettaa kahdesti koska muutoin tulee asetetuksi else kohdassa...
                                        this.verkko.lisaaKaari(id, kohde.getId()[l], kohde.getMaasto(), lat, lon, kohde.getLat()[l], kohde.getLon()[l], true);
                                        continue;
                                    }
                                    
                                    if ((lahtosolmuIndeksi == kohde.getId().length - 1 && l == 0) ||(lahtosolmuIndeksi == 0 && l == kohde.getId().length - 1)) {//viimeisestä ekaan
                                        this.verkko.lisaaKaari(id, kohde.getId()[l], kohde.getMaasto(), lat, lon, kohde.getLat()[l], kohde.getLon()[l], true);
                                        continue;
                                    }
                                    else { //kaari alueen läpi
                                        this.asetaKaari(kohde.getMaasto(), id, lat, lon, kohde.getId()[l], kohde.getLat()[l], kohde.getLon()[l], naapurustoX, naapurustoY, naapurustoY + i, naapurustoX + j);
                                        continue;
                                    }
                                }
                            }
                        } else {// viivamainen kohde asetetaan omat kaaret vain seuraavaan solmuun.
                            if (lahtosolmuIndeksi + 1 == kohde.getId().length) {
                                continue;
                            }
                            this.verkko.lisaaKaari(id, kohde.getId()[lahtosolmuIndeksi + 1], kohde.getMaasto(), lat, lon, kohde.getLat()[lahtosolmuIndeksi + 1], kohde.getLon()[lahtosolmuIndeksi + 1], true);
                            continue;
                        }
                    } else { // asetetaan kaaret tuntemattoman alueen läpi muihin polygoneihin
                        for (int l = 0; l < kohde.getId().length; l++) {
                            if (kohde.getId()[l] != id) {
                                this.asetaKaari(-1, id, lat, lon, kohde.getId()[l], kohde.getLat()[l], kohde.getLon()[l], naapurustoX, naapurustoY, naapurustoY + i, naapurustoX + j);
                                continue;
                            }
                        }
                    }
                }

            }
        }
    }

    public VerkkoPolygoni getVerkko() {
        return verkko;
    }

    /**
     * tarkistaa leikkaako kaari polygoneja, ja jos ei pyytää verkkoa luomaan
     * kaaren
     *
     * @param id1
     * @param lat1
     * @param lon1
     * @param idk
     * @param latk
     * @param lonk
     * @param naapurustoX
     * @param naapurustoY
     * @param kohdenaapurustoX
     * @param kohdenaapurustoY
     */
    private void asetaKaari(int maasto, int id1, double lat1, double lon1, int idk, double latk, double lonk, int naapurustoX, int naapurustoY, int kohdenaapurustoX, int kohdenaapurustoY) {

        if (Math.abs(lat1 - latk) < 0.1 && Math.abs(lon1 - lonk) < 0.1) {
            this.verkko.lisaaKaari(id1, idk, maasto, lat1, lon1, latk, lonk, false);
            return;
        }

        //     System.out.println("s: " + id1);
        //     System.out.println("k: " + idk);
        for (int i = -2; i <= 2; i++) {
            if (naapurustoY + i < 0 || naapurustoY + i >= this.naapurustot.length) {
                continue;
            }
            for (int j = -2; j <= 2; j++) {
                if (naapurustoX + j < 0 || naapurustoX + j >= this.naapurustot[naapurustoY + i].length) {
                    continue;
                }
                PolygoniLista ruutu = this.naapurustot[naapurustoY + i][naapurustoX + j];

                for (int k = 0; k < ruutu.koko(); k++) {
                    if (ruutu.ota(k).janaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                        return;
                    }

                }
            }
        }
        for (int i = -2; i <= 2; i++) {
            if (kohdenaapurustoY + i < 0 || kohdenaapurustoY + i >= this.naapurustot.length) {
                continue;
            }
            for (int j = -2; j <= 2; j++) {
                if (kohdenaapurustoX + j < 0 || kohdenaapurustoX + j >= this.naapurustot[kohdenaapurustoY + i].length) {
                    continue;
                }
                PolygoniLista ruutu = this.naapurustot[kohdenaapurustoY + i][kohdenaapurustoX + j];

                for (int k = 0; k < ruutu.koko(); k++) {
                    if (ruutu.ota(k).janaLeikkaaPolygonin(lat1, lon1, latk, lonk)) {
                        return;
                    }

                }
            }
        }

        this.verkko.lisaaKaari(id1, idk, maasto, lat1, lon1, latk, lonk, false);

    }

}
