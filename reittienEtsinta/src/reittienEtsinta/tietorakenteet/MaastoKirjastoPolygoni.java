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

    public MaastoKirjastoPolygoni(int maastoja) {
        this.vauhtiMaastossa = new double[maastoja + 1];
        this.otostenMaara = new int[maastoja + 1];
    }

    /**
     * Lisää yksittäiselle maastotyypille vauhdin
     *
     * @param maasto
     * @param vauhti
     */
    public void lisaaVauhti(int maasto, double vauhti) {
        this.vauhtiMaastossa[maasto] += vauhti;
        this.otostenMaara[maasto]++;
    }

    /**
     * hakee solmuun liittyvän maaston. Periaatteessa aikavaativuus n suhteessa
     * maastojen määrään, mutta maastoja on vain muutamia
     *
     * @param solmu
     * @return
     */
    private double haeMaastolla(int maasto) {
        if (this.otostenMaara[maasto] == 0) {
            //System.out.println("ei otoksia");
            return 0.00001;
        }
        return this.vauhtiMaastossa[maasto] / this.otostenMaara[maasto];
    }

    /**
     * palauttaa solmun ja kohdesolmun välisen maaston vauhdin. niin rikki... :(
     *
     * @param maasto
     * @param ulko
     * @return
     */
    public double haeVauhti(int maasto, boolean ulko) {
        if (ulko) {
            return Math.max(this.haeMaastolla(maasto), this.haeMaastolla(this.vauhtiMaastossa.length - 1));
        }
        if (maasto == -1) {
            return this.haeMaastolla(this.vauhtiMaastossa.length - 1); //luokittelematon maasto polygonien välillä

        }

        return this.haeMaastolla(maasto);

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
                if (polygonit[j].getClass().equals(AluePolygoni.class)) {//aluemainen kohde
                    AluePolygoni aluepoly = (AluePolygoni) polygonit[j];
                    if (aluepoly.pisteSisalla(reitti.getLat()[i], reitti.getLon()[i])) {
                        this.lisaaVauhti(polygonit[j].getMaasto(), reitti.vauhti(i, i + 1));
                        System.out.println("alue: " + polygonit[j].getMaasto() + " v: " + reitti.vauhti(i, i + 1));
                        return;
                    }
                } else { //viivamainen kohde
                    if (polygonit[j].pisteenEtaisyys(reitti.getLat()[i], reitti.getLon()[i]) < 4) {//etaisyys metreinä
                        this.lisaaVauhti(polygonit[j].getMaasto(), reitti.vauhti(i, i + 1));
                        System.out.println("viiva: " + polygonit[j].getMaasto() + " v: " + reitti.vauhti(i, i + 1));
                        return;

                    }
                }
            }
            System.out.println("muu: " + (this.vauhtiMaastossa.length - 1) + " v: " + reitti.vauhti(i, i + 1));
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
