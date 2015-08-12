/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import reittienEtsinta.tiedostonKasittely.KuvanLukija;
import reittienEtsinta.tietorakenteet.MinimiKeko;
import java.util.Arrays;
import java.util.Random;
import reittienEtsinta.tiedostonKasittely.GPXLukija;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Pino;
import reittienEtsinta.tietorakenteet.PolygonVerkko;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.VerkkoSolmu;
import reittienEtsinta.tietorakenteet.Verkontekija;
import reittienEtsinta.toteutuneetReitit.MaastoKirjasto;
import reittienEtsinta.toteutuneetReitit.Reitti;

/**
 *
 * @author elias
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        GeoJsonLukija lukija = new GeoJsonLukija("aineisto/testi.json");

        Polygoni[] lueJson = lukija.lueJson();

        
        Verkontekija verkontekija = new Verkontekija(lueJson, lueJson[0].getLatmin(), lueJson[1].getLatmax(), 
                lueJson[0].getLonmin(), lueJson[1].getLonmax(), lueJson[0].getPisteita()+lueJson[1].getPisteita());
        
        verkontekija.luoVerkko();
        
        PolygonVerkko verkko = verkontekija.getVerkko();
        
        System.out.println(verkko);
        
        
        /*
         //matinkyläkuvan kulmien koord, älä hukkaa.
         GPXLukija lukija = new GPXLukija(24.743541, 60.148093, 24.753985, 60.153663, 200, "aineisto/matinkyla.gpx");
         Reitti lueGpX = lukija.lueGpx();
         //System.out.println(lueGpX);
         KuvanLukija kuvanLukija = new KuvanLukija("aineisto/matinkyla_200.jpg", "aineisto/matinkyla_200_reitti.jpg");
         //kuvanLukija.piirraReitti(lueGpX);
         MaastoKirjasto maastoKirjasto = new MaastoKirjasto();
         maastoKirjasto.lisaaReitti(lueGpX, kuvanLukija);
         Verkko verkko = new Verkko(200, maastoKirjasto, kuvanLukija);
         verkko.aStar(4, 10, 176, 196);
         // kuvanLukija.piirraReitti(lueGpX);
         Reitti reitti = verkko.lyhyinReitti(4, 10, 176, 196);
         kuvanLukija.piirraReitti(reitti);
         */
    }

}
