/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import raster.KuvanLukija;
import raster.MinimiKeko;
import java.util.Arrays;
import java.util.Random;
import org.json.JSONObject;
import reittienEtsinta.tiedostonKasittely.GPXLukija;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Pino;
import reittienEtsinta.tietorakenteet.PolygonVerkko;
import reittienEtsinta.tietorakenteet.Polygoni;
import raster.Verkko;
import raster.VerkkoSolmu;
import reittienEtsinta.tietorakenteet.Verkontekija;
import raster.MaastoKirjasto;
import raster.Reitti;
import reittienEtsinta.tiedostonKasittely.GeoJsonKirjoittaja;
import reittienEtsinta.tietorakenteet.MinimiKekoPolygon;
import reittienEtsinta.tietorakenteet.ReittiPoly;

/**
 *
 * @author elias
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        GeoJsonLukija lukija = new GeoJsonLukija("aineisto/parkkipaikat.geojson");

        Polygoni[] lueJson = lukija.lueJson();

        Verkontekija verkontekija = new Verkontekija(lueJson, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), lukija.getPisteita(), lukija.getPisteita()-1);

        verkontekija.luoVerkko();

        PolygonVerkko verkko = verkontekija.getVerkko();
        
        System.out.println(verkko);
        
        verkko.aStar(0);
        JSONObject lyhyinReitti = verkko.lyhyinReitti(0);
        GeoJsonKirjoittaja.kirjoita("aineisto/parkkipaikkojapitkin.geojson", lyhyinReitti);
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
