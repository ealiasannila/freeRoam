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
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.VerkkoSolmu;
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

        Polygoni testigoni = lueJson[0];

        System.out.println("true: " + testigoni.pisteSisalla(0.5, 0.5));
        System.out.println("false: " + testigoni.pisteSisalla(2.5, 0.5));
        
        System.out.println("true: " + testigoni.viivaLeikkaaPolygonin(2.1, 0.1, 0.1, 2.1));
        System.out.println("false: " + testigoni.viivaLeikkaaPolygonin(5, 5, 6, 6));
        
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
