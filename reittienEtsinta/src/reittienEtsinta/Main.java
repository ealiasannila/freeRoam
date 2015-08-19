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
import raster.GPXLukija;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Pino;
import reittienEtsinta.tietorakenteet.VerkkoPolygoni;
import reittienEtsinta.tietorakenteet.AluePolygoni;
import raster.Verkko;
import raster.VerkkoSolmu;
import reittienEtsinta.tietorakenteet.Verkontekija;
import raster.MaastoKirjasto;
import raster.Reitti;
import reittienEtsinta.tiedostonKasittely.GPXLukijaPolygoni;
import reittienEtsinta.tiedostonKasittely.GeoJsonKirjoittaja;
import reittienEtsinta.tietorakenteet.MaastoKirjastoPolygoni;
import reittienEtsinta.tietorakenteet.MinimiKekoPolygon;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.ReittiPolygoni;

/**
 *
 * @author elias
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        GeoJsonLukija lukija = new GeoJsonLukija();
        int maastoja = 3;

        Polygoni[] rakennukset = lukija.luePolygonit("aineisto/matinkyla/rakennukset.geojson", 0);
        Polygoni[] parkkikset = lukija.luePolygonit("aineisto/matinkyla/parkkikset.geojson", 1);
        Polygoni[] tiet = lukija.luePolygonit("aineisto/matinkyla/tiet.geojson", 2);

        Polygoni[] polygonit = new Polygoni[rakennukset.length + parkkikset.length + tiet.length];

        for (int i = 0; i < rakennukset.length; i++) {
            polygonit[i] = rakennukset[i];
        }

        for (int i = 0; i < parkkikset.length; i++) {
            polygonit[i + rakennukset.length] = parkkikset[i];
        }

        for (int i = 0; i < tiet.length; i++) {
            polygonit[i + rakennukset.length + parkkikset.length] = tiet[i];
        }

        MaastoKirjastoPolygoni maastokirjasto = new MaastoKirjastoPolygoni(maastoja);

        GeoJsonLukija reittilukija = new GeoJsonLukija();
        ReittiPolygoni reitti = reittilukija.lueReitti("aineisto/matinkyla/matinkyla.geojson");
        maastokirjasto.lisaaReitti(reitti, polygonit);
      //  GeoJsonKirjoittaja.kirjoita("aineisto/matinkyla/mallireitti.geojson", GeoJsonKirjoittaja.muunnaJson(reitti));

        System.out.println(maastokirjasto);

        Verkontekija verkontekija = new Verkontekija(polygonit, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), lukija.getPisteita(), maastokirjasto);

        verkontekija.luoVerkko();
        VerkkoPolygoni verkko = verkontekija.getVerkko();

        Kayttoliittyma kali = new Kayttoliittyma(verkko);
        kali.kaynnista();

        /*
         int lahto = 59;
         verkko.alustus(lahto, rakennukset[rakennukset.length - 50].getId()[0]);

         System.out.println("a*");
         verkko.aStar();
         System.out.println("reitti");

         JSONObject lyhyinReitti = verkko.lyhyinReitti();
         System.out.println("kirjoita");
         GeoJsonKirjoittaja.kirjoita("aineisto/matinkyla/reitti.geojson", lyhyinReitti);
        
        
         //GeoJsonKirjoittaja.kirjoita("aineisto/matinkyla/verkko.geojson", verkko.getVerkko());
         */
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
