/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import java.io.File;
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

        File polygonKansio = new File(args[0]);
        if (!polygonKansio.isDirectory()) {
            System.out.println("anna kansio");
            return;
        }
        File[] polygonTiedostot = polygonKansio.listFiles();

        GeoJsonLukija lukija = new GeoJsonLukija();
        int maastoja;
        for (maastoja = 0; maastoja < polygonTiedostot.length; maastoja++) {
            Polygoni[] polygonit = lukija.luePolygonit(polygonTiedostot[maastoja], maastoja);
            //tee jotain
        }
        
        File reittiKansio = new File(args[1]);
        if (!reittiKansio.isDirectory()) {
            System.out.println("anna kansio");
            return;
        }
        File[] reittiTiedostot = reittiKansio.listFiles();

        for (int i = 0; maastoja < reittiTiedostot.length; maastoja++) {
            ReittiPolygoni reitti = lukija.lueReitti(reittiTiedostot[i]);
            //tee jotain
        }
        
        
        

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

        ReittiPolygoni reitti = lukija.lueReitti("aineisto/matinkyla/matinkyla.geojson");
        maastokirjasto.lisaaReitti(reitti, polygonit);

        System.out.println(maastokirjasto);

        Verkontekija verkontekija = new Verkontekija(polygonit, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), lukija.getPisteita(), maastokirjasto);

        verkontekija.luoVerkko();
        VerkkoPolygoni verkko = verkontekija.getVerkko();

        Kayttoliittyma kali = new Kayttoliittyma(verkko);
        kali.kaynnista();

    }

}
