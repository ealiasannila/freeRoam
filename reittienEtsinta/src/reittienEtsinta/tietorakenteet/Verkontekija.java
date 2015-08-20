/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import java.util.ArrayList;
import java.util.List;
import reittienEtsinta.Apumetodit;

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

    private Verkko verkko;
    private Lista<Polygoni>[][] naapurustot;
    private int pisteita;

    private double latmin;
    private double latmax;
    private double lonmin;
    private double lonmax;

    public Verkontekija(Lista<Polygoni> polygonit, double latmin, double latmax, double lonmin, double lonmax, int pisteita, MaastoKirjasto maastokirjasto) {
        this.pisteita = pisteita;
        
        this.latmax = latmax;
        this.latmin = latmin;
        this.lonmax = lonmax;
        this.lonmin = lonmin;
        
        int n = (int) this.pisteita / 300; //max solmujen maara naapurustossa
        n = Math.max(n, 1);

        System.out.println("n: " + n);

        this.alustaNaapurustot(n);
        this.verkko = new Verkko(pisteita, maastokirjasto);

        this.lisaaPolygonit(polygonit);

    }

    private void alustaNaapurustot(int n) {
        this.naapurustot = new Lista[n][n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.naapurustot[i][j] = new Lista(8);

            }
        }
    }

    private void lisaaPolygonit(Lista<Polygoni> polygonit) {
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

    private boolean naapurustoUlkona(int i, int naapurusto) {
        return (naapurusto + i < 0 || naapurusto + i >= this.naapurustot.length);
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
    private void asetaAlueminenKohde(Polygoni kohde, int id, int lahtosolmuIndeksi, double lat, double lon, int naapurustoX, int naapurustoY, int kohdenaapurustoX, int kohdenaapurustoY) {
        for (int l = 0; l < kohde.getId().length; l++) {

            if (kohde.getId()[l] != id) {

                if (lahtosolmuIndeksi == l - 1 || lahtosolmuIndeksi == l + 1) { //reunaa pitkin seuraava solmu, täytyy asettaa kahdesti koska muutoin tulee asetetuksi else kohdassa...
                    this.verkko.lisaaKaari(id, kohde.getId()[l], kohde.getMaasto(), lat, lon, kohde.getLat()[l], kohde.getLon()[l], true);
                } else if ((lahtosolmuIndeksi == kohde.getId().length - 1 && l == 0) || (lahtosolmuIndeksi == 0 && l == kohde.getId().length - 1)) {//viimeisestä ekaan
                    this.verkko.lisaaKaari(id, kohde.getId()[l], kohde.getMaasto(), lat, lon, kohde.getLat()[l], kohde.getLon()[l], true);
                } else { //kaari alueen läpi
                    this.asetaKaari(kohde.getMaasto(), id, lat, lon, kohde.getId()[l], kohde.getLat()[l], kohde.getLon()[l], naapurustoX, naapurustoY, kohdenaapurustoX, kohdenaapurustoY);
                }
            }
        }
    }

    private void asetaViivamainenKohde(int id, int lahtosolmuIndeksi, Polygoni kohde, double lat, double lon) {
        if (lahtosolmuIndeksi + 1 == kohde.getId().length) {
            return;
        }
        this.verkko.lisaaKaari(id, kohde.getId()[lahtosolmuIndeksi + 1], kohde.getMaasto(), lat, lon, kohde.getLat()[lahtosolmuIndeksi + 1], kohde.getLon()[lahtosolmuIndeksi + 1], true);

    }

    private void asetaTuntemattomanLapi(int id, Polygoni kohde, double lat, double lon, int naapurustoX, int naapurustoY, int kohdenaapurustoX, int kohdenaapurustoY) {
        for (int l = 0; l < kohde.getId().length; l++) {
            if (kohde.getId()[l] != id) {
                this.asetaKaari(-1, id, lat, lon, kohde.getId()[l], kohde.getLat()[l], kohde.getLon()[l], naapurustoX, naapurustoY, kohdenaapurustoX, kohdenaapurustoY);
            }
        }
    }

    private void asetaKaaret(int lahtosolmuIndeksi, int id, double lat, double lon, int naapurustoX, int naapurustoY, Polygoni lahto) {
        for (int i = -2; i <= 2; i++) {
            if (this.naapurustoUlkona(i, naapurustoY)) {
                continue;
            }
            for (int j = -2; j <= 2; j++) {
                if (this.naapurustoUlkona(j, naapurustoX)) {
                    continue;
                }

                for (int k = 0; k < this.naapurustot[naapurustoY + i][naapurustoX + j].koko(); k++) {
                    Polygoni kohde = this.naapurustot[naapurustoY + i][naapurustoX + j].ota(k);
                    if (kohde.getId()[0] == lahto.getId()[0]) {
                        if (lahto.getClass() == AluePolygoni.class) {//alueamainen kohde, asetetaan kaaret alueen läpi joka solmuun
                            this.asetaAlueminenKohde(kohde, id, lahtosolmuIndeksi, lat, lon, naapurustoX, naapurustoY, naapurustoX + j, naapurustoY + i);
                        } else {// viivamainen kohde asetetaan omat kaaret vain seuraavaan solmuun.
                            this.asetaViivamainenKohde(id, lahtosolmuIndeksi, kohde, lat, lon);
                        }
                    } else { // asetetaan kaaret tuntemattoman alueen läpi muihin polygoneihin
                        this.asetaTuntemattomanLapi(id, kohde, lat, lon, naapurustoX, naapurustoY, naapurustoX + j, naapurustoY + i);
                    }
                }

            }
        }
    }

    public Verkko getVerkko() {
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
    private int[] rajat(int ero) {
        int[] rajat = new int[2];

        if (ero > 0) {
            rajat[0] = -1;
            rajat[1] = ero + 1;

        } else if (ero < 0) {
            rajat[0] = ero - 1;
            rajat[1] = 1;
        } else {
            rajat[0] = -1;
            rajat[1] = 1;
        }
        return rajat;
    }

    private void asetaKaari(int maasto, int idp, double latp, double lonp, int idk, double latk, double lonk, int naapurustoX, int naapurustoY, int kohdenaapurustoX, int kohdenaapurustoY) {

        if (Apumetodit.pisteSama(latp, lonp, latk, lonk)) {
            this.verkko.lisaaKaari(idp, idk, maasto, latp, lonp, latk, lonk, false);
            return;
        }

        int[] xRajat = this.rajat(kohdenaapurustoX - naapurustoX);
        int[] yRajat = this.rajat(naapurustoY - kohdenaapurustoY);

        for (int i = yRajat[0]; i <= yRajat[1]; i++) {
            if (this.naapurustoUlkona(i, naapurustoY)) {
                continue;
            }
            for (int j = xRajat[0]; j <= xRajat[1]; j++) {
                if (this.naapurustoUlkona(j, naapurustoX)) {
                    continue;
                }
                Lista<Polygoni> ruutu = this.naapurustot[naapurustoY + i][naapurustoX + j];

                for (int k = 0; k < ruutu.koko(); k++) {
                    if (ruutu.ota(k).janaLeikkaaPolygonin(latp, lonp, latk, lonk)) {
                        return;
                    }

                }
            }
        }

        this.verkko.lisaaKaari(idp, idk, maasto, latp, lonp, latk, lonk, false);

    }

}
