package reittienEtsinta.tietorakenteet;

import java.util.HashMap;

/**
 * Pitää kirjaa toteutuneiden reittien perusteella lasketusta nopeuksista eri
 * maastoille (= eri tiedostoissa olleille polygoneille)
 *
 * @author elias
 */
public class Maastokirjasto {

    private double[] vauhtiMaastossa;
    private int[] otostenMaara;

    public Maastokirjasto(int maastoja) {
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
     * palauttaa maastoon liittyvien vauhtien keskiarvon.
     * @param maasto
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
     * palauttaa solmusta lähtevän kaaren vauhdin.
     * Boolean muuttuja ulko kertoo kulkeeko kaari polygonin ulkoreunaa pitkin.
     * maasto -1 kuvaa maastoa joka ei kuulu mihinkään polygoniin
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

    public void lisaaReitit(Lista<Reitti> lista, Lista<Polygoni> polygonit){
        for (int i = 0; i < lista.koko(); i++) {
            this.lisaaReitti(lista.ota(i), polygonit);
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
    
    public void lisaaReitti(Reitti reitti, Lista<Polygoni> polygonit) {
        for (int i = 0; i < reitti.getAika().length - 1; i++) {
            for (int j = 0; j <= polygonit.koko(); j++) {
                if (j == polygonit.koko()) { //Ei löytynyt polygonia, jonka alueella reittipiste olisi -> laitetaan tuntemattomaan maastoon
                    //  System.out.println("muu: " + (this.vauhtiMaastossa.length - 1) + " v: " + reitti.vauhti(i, i + 1));
                    this.lisaaVauhti(this.vauhtiMaastossa.length - 1, reitti.vauhti(i, i + 1));
                    break;
                }

                if (polygonit.ota(j).getClass().equals(AluePolygoni.class)) {//aluemainen kohde, tarkistetaan onko piste kohteen sisällä
                    AluePolygoni aluepoly = (AluePolygoni) polygonit.ota(j);
                    //  System.out.println(reitti.getLat()[i] + " lat " + reitti.getLon()[i] + " lon");
                    if (aluepoly.pisteSisalla(reitti.getLat()[i], reitti.getLon()[i])) {
                        this.lisaaVauhti(polygonit.ota(j).getMaasto(), reitti.vauhti(i, i + 1));
                        //         System.out.println("alue: " + polygonit[j].getMaasto() + " v: " + reitti.vauhti(i, i + 1));
                        break;
                    }
                } else { //viivamainen kohde, tarkistetaan onko reittipiste riittävän lähellä viivaa
                    if (polygonit.ota(j).pisteenEtaisyys(reitti.getLat()[i], reitti.getLon()[i]) < 4) {//etaisyys metreinä
                        this.lisaaVauhti(polygonit.ota(j).getMaasto(), reitti.vauhti(i, i + 1));
                        //          System.out.println("viiva: " + polygonit[j].getMaasto() + " v: " + reitti.vauhti(i, i + 1));
                        break;

                    }
                }
            }

        }

    }

    public String toString() {
        String tuloste = "";
        for (int i = 0; i < this.otostenMaara.length; i++) {
            tuloste += "\n" + i + ": " + this.haeMaastolla(i);
        }
        return tuloste;
    }

}
