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

        Polygoni[] rakennukset = lukija.lueJson("aineisto/matinkyla/rakennukset.geojson", 0);

        Polygoni[] parkkikset = lukija.lueJson("aineisto/matinkyla/parkkikset.geojson", 1);

        Polygoni[] tiet = lukija.lueJson("aineisto/matinkyla/tiet.geojson", 2);

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

        GPXLukijaPolygoni parkkisLukija = new GPXLukijaPolygoni("aineisto/testiParkkis.gpx");
        GPXLukijaPolygoni tuntematonLukija = new GPXLukijaPolygoni("aineisto/testiTuntematon.gpx");
        GPXLukijaPolygoni tieLukija = new GPXLukijaPolygoni("aineisto/testiTie.gpx");

        ReittiPolygoni parkkisreitti = parkkisLukija.lueGpx();
        ReittiPolygoni tuntematonreitti = tuntematonLukija.lueGpx();
        ReittiPolygoni tiereitti = tieLukija.lueGpx();
        /*
         maastokirjasto.lisaaReitti(parkkisreitti, polygonit);
         maastokirjasto.lisaaReitti(tiereitti, polygonit);       
         maastokirjasto.lisaaReitti(tuntematonreitti, polygonit);
         */

        maastokirjasto.lisaaVauhti(1, 3);
        maastokirjasto.lisaaVauhti(2, 1.6);
        maastokirjasto.lisaaVauhti(3, 1.4);

        System.out.println(maastokirjasto);

        Verkontekija verkontekija = new Verkontekija(polygonit, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), lukija.getPisteita(), 60, maastokirjasto);

        verkontekija.luoVerkko();
        VerkkoPolygoni verkko = verkontekija.getVerkko();
        

        // System.out.println(verkko);
        int lahto = 59;
        System.out.println("a*");
        verkko.aStar(lahto);
        System.out.println("reitti");
        
        JSONObject lyhyinReitti = verkko.lyhyinReitti(lahto);
        System.out.println("väli");
        System.out.println("59 - 60");
        System.out.println("aika: " + verkko.haeKaari(59, 60));
        double etaisyys = Apumetodit.pisteidenEtaisyys(verkko.lat[59], verkko.lon[59], verkko.lat[60], verkko.lon[60]);
        System.out.println("etaisyys: " + etaisyys);
        System.out.println("vauhti: " + etaisyys/verkko.haeKaari(59, 60));
        System.out.println("kirjoita");
        GeoJsonKirjoittaja.kirjoita("aineisto/matinkyla/reitti.geojson", lyhyinReitti);
        //GeoJsonKirjoittaja.kirjoita("aineisto/matinkyla/verkko.geojson", verkko.getVerkko());

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
