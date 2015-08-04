/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta.toteutuneetReitit;

import java.util.HashMap;
import reittienEtsinta.KuvanLukija;

/**
 *
 * @author elias
 */
public class MaastoKirjasto {

    private HashMap<Integer, Double> vauhditMaastossa;
    private HashMap<Integer, Integer> vauhtiOtostenMaara;

    public MaastoKirjasto() {
        this.vauhditMaastossa = new HashMap<>();
        this.vauhtiOtostenMaara = new HashMap<>();
    }
    
    

    public void lisaaVauhti(int maasto, double vauhti) {
        if (!this.vauhditMaastossa.containsKey(maasto)) {
            this.vauhditMaastossa.put(maasto, vauhti);
            this.vauhtiOtostenMaara.put(maasto, 1);
        } else {
            this.vauhditMaastossa.put(maasto, this.vauhditMaastossa.get(maasto) + vauhti);
            this.vauhtiOtostenMaara.put(maasto, this.vauhtiOtostenMaara.get(maasto) + 1);
        }
    }

    public double haeVauhti(int maasto) {
        if (this.vauhditMaastossa.containsKey(maasto)) {
            return this.vauhditMaastossa.get(maasto) / this.vauhtiOtostenMaara.get(maasto);
        } else {
            return 0.001;
        }
    }

    public void lisaaReitti(int maastoTyyppi, int maastonAlku, int maastonLoppu, Reitti reitti) {
        this.lisaaVauhti(maastoTyyppi, reitti.vauhti(maastonAlku, maastonLoppu));
    }
}
