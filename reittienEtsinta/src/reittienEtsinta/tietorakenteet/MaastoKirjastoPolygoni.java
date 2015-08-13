/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.tietorakenteet;

import raster.*;
import java.util.HashMap;
import raster.KuvanLukija;

/**
 * Pitää kirjaa toteutuneiden reittien perusteella lasketusta nopeuksista eri
 * maastoille (= eri tiedostoissa olleille polygoneille)
 *
 * @author elias
 */
public class MaastoKirjastoPolygoni {

    private double[] vauhtiMaastossa;
    private int[] otostenMaara;
    private int[] maastorajat;

    public MaastoKirjastoPolygoni(int[] maastorajat) {
        this.vauhtiMaastossa = new double[maastorajat.length + 1];
        this.otostenMaara = new int[maastorajat.length + 1];
        this.maastorajat = maastorajat;
    }

    /**
     * Lisää yksittäiselle maastotyypille vauhdin
     *
     * @param maasto
     * @param vauhti
     */
    private void lisaaVauhti(int maasto, double vauhti) {
        this.vauhtiMaastossa[maasto] += vauhti;
        this.otostenMaara[maasto]++;
    }

    private int haeMaasto(int solmu) {
        for (int i = 0; i < this.maastorajat.length; i++) {
            if (solmu < this.maastorajat[i]) {
                return i;
            }
        }
        return this.maastorajat.length - 1;
    }

    private double haeMaastolla(int maasto) {
        if (this.otostenMaara[maasto] == 0) {
            return 0.00001;
        }
        return this.vauhtiMaastossa[maasto] / this.otostenMaara[maasto];
    }

    /**
     * palauttaa solmun ja kohdesolmun välisen maaston vauhdin.
     *
     * @param maasto
     * @return
     */
    public double haeVauhti(int solmu, int kohde) {
        if (this.haeMaasto(solmu) == this.haeMaasto(kohde)) {
            return this.haeMaastolla(this.haeMaasto(solmu));
        } else {
            return this.haeMaastolla(this.vauhtiMaastossa.length - 1); //luokittelematon maasto polygonien välillä
        }
    }

    /**
     * Lisää toteutuneen reitin kaikkien pisteiden perusteella vauhdit
     * kirjastoon. Kahden pisteen välillä kuljettu vauhti lisätään molempiin
     * pisteisiin.
     *
     * @param reitti
     * @param kuvanLukija
     */
    public void lisaaReitti(ReittiPolygoni reitti, Polygoni[] polygonit) {
        for (int i = 0; i < reitti.getAika().length - 1; i++) {
            for (int j = 0; j < polygonit.length; j++) {
                if (polygonit[j].pisteSisalla(reitti.getLat()[i], reitti.getLon()[i])) {
                    this.lisaaVauhti(this.haeMaasto(polygonit[j].getId()[0]), reitti.vauhti(i, i + 1));

                }
            }
            this.lisaaVauhti(this.vauhtiMaastossa.length - 1, reitti.vauhti(i, i + 1));

        }

        //testaa minkä polygonin sisällä reitti on ja lisää vauhti
    }

    public String toString() {
        String tuloste = "";
        for (int i = 0; i < this.otostenMaara.length; i++) {
            tuloste += "\n" + i + ": " + this.haeMaastolla(i);
        }
        return tuloste;
    }

}
