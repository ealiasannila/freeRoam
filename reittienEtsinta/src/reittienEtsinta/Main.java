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
import reittienEtsinta.tietorakenteet.PolygoniLista;
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

        if (args.length != 2) {
            System.out.println("väärät argumentit");
            return;
        }

        File polygonKansio = new File(args[0]);
        if (!polygonKansio.isDirectory()) {
            System.out.println("anna kansio");
            return;
        }
        File[] polygonTiedostot = polygonKansio.listFiles();

        GeoJsonLukija lukija = new GeoJsonLukija();
        int maastoja;

        PolygoniLista polygonilista = new PolygoniLista(128);

        for (maastoja = 0; maastoja < polygonTiedostot.length; maastoja++) {
            lukija.luePolygonit(polygonTiedostot[maastoja], maastoja, polygonilista); //lisää polygonit listaan
        }
        
        System.out.println("polygonit luettu, " + polygonilista.koko() + " polygonia");

        File reittiKansio = new File(args[1]);
        if (!reittiKansio.isDirectory()) {
            System.out.println("anna kansio");
            return;
        }
        File[] reittiTiedostot = reittiKansio.listFiles();

        ReittiPolygoni reitti = null;
        for (int i = 0; i < reittiTiedostot.length; i++) {
            reitti = lukija.lueReitti(reittiTiedostot[i]);
        }
        
        System.out.println("reitit luettu, ");

        MaastoKirjastoPolygoni maastokirjasto = new MaastoKirjastoPolygoni(maastoja);

        maastokirjasto.lisaaReitti(reitti, polygonilista);

        System.out.println("Vauhdit eri maastoissa:");
        System.out.println(maastokirjasto);

        Verkontekija verkontekija = new Verkontekija(polygonilista, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), lukija.getPisteita(), maastokirjasto);

        verkontekija.luoVerkko();
        VerkkoPolygoni verkko = verkontekija.getVerkko();

        Kayttoliittyma kali = new Kayttoliittyma(verkko);
        kali.kaynnista();

    }

}
