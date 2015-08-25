/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reittienEtsinta;

import java.io.File;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.Verkontekija;
import reittienEtsinta.tietorakenteet.Maastokirjasto;
import reittienEtsinta.tietorakenteet.Lista;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Reitti;

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

        Lista<Polygoni> polygonilista = new Lista(128);

        for (maastoja = 0; maastoja < polygonTiedostot.length; maastoja++) {
            lukija.luePolygonit(polygonTiedostot[maastoja], maastoja, polygonilista); //lisää polygonit listaan
        }

        System.out.println("polygonit luettu, " + polygonilista.koko() + " polygonia");
        int solmuja = lukija.getPisteita();

        File reittiKansio = new File(args[1]);
        Lista<Reitti> reittilista = new Lista(2);

        if (!reittiKansio.isDirectory()) {
            System.out.println("anna kansio");
            return;
        }
        File[] reittiTiedostot = reittiKansio.listFiles();

        for (int i = 0; i < reittiTiedostot.length; i++) {
            reittilista.lisaa(lukija.lueReitti(reittiTiedostot[i]));
        }

        System.out.println("reitit luettu, " + reittilista.koko() + " reittia");

        Maastokirjasto maastokirjasto = new Maastokirjasto(maastoja);

        maastokirjasto.lisaaReitit(reittilista, polygonilista);

        System.out.println("Vauhdit eri maastoissa:");
        System.out.println(maastokirjasto);

        Verkko verkko = new Verkko(solmuja, maastokirjasto);
        Verkontekija verkontekija = new Verkontekija(polygonilista, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), solmuja);

        verkontekija.luoVerkko(verkko);

        Kayttoliittyma kali = new Kayttoliittyma(verkko);
        kali.kaynnista();

    }

}
