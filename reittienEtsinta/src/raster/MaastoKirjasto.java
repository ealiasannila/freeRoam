/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package raster;

import java.util.HashMap;
import raster.KuvanLukija;

/**
 * Pitää kirjaa toteutuneiden reittien perusteella lasketusta nopeuksista eri maastoille (=väreille kuvassa)
 * 
 * @author elias
 */
public class MaastoKirjasto {

    private HashMap<Integer, Double> vauhditMaastossa;
    private HashMap<Integer, Integer> vauhtiOtostenMaara;

    public MaastoKirjasto() {
        this.vauhditMaastossa = new HashMap<Integer, Double>();
        this.vauhtiOtostenMaara = new HashMap<Integer, Integer>();
    }
    
    /**
     * Lisää yksittäiselle maastotyypille vauhdin
     * @param maasto
     * @param vauhti 
     */

    public void lisaaVauhti(int maasto, double vauhti) {
        if (!this.vauhditMaastossa.containsKey(maasto)) {
            this.vauhditMaastossa.put(maasto, vauhti);
            this.vauhtiOtostenMaara.put(maasto, 1);
        } else {
            this.vauhditMaastossa.put(maasto, this.vauhditMaastossa.get(maasto) + vauhti);
            this.vauhtiOtostenMaara.put(maasto, this.vauhtiOtostenMaara.get(maasto) + 1);
        }
    }

    /**
     * palauttaa kysytyn maastotyypin vauhtien keskiarvon
     * @param maasto
     * @return 
     */
    public double haeVauhti(int maasto) {
        if (this.vauhditMaastossa.containsKey(maasto)) {
            return this.vauhditMaastossa.get(maasto) / this.vauhtiOtostenMaara.get(maasto);
        } else {
            return 0.001;
        }
    }

    /**
     * Lisää toteutuneen reitin kaikkien pisteiden perusteella vauhdit kirjastoon.
     * Kahden pisteen välillä kuljettu vauhti lisätään molempiin pisteisiin.
     * @param reitti
     * @param kuvanLukija 
     */
    public void lisaaReitti(Reitti reitti, KuvanLukija kuvanLukija) {
        for (int i = 0; i < reitti.getAika().length-1; i++) {
            double vauhti = reitti.vauhti(i, i+1);
            this.lisaaVauhti(kuvanLukija.getMaasto(reitti.getX()[i], reitti.getY()[i]), vauhti);
            this.lisaaVauhti(kuvanLukija.getMaasto(reitti.getX()[i+1], reitti.getY()[i+1]), vauhti);
        }
        
    }
}
