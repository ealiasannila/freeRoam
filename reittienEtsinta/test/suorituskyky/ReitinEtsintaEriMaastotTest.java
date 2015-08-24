/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package suorituskyky;

import java.io.File;
import java.util.Random;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import reittienEtsinta.tiedostonKasittely.GeoJsonLukija;
import reittienEtsinta.tietorakenteet.Lista;
import reittienEtsinta.tietorakenteet.Maastokirjasto;
import reittienEtsinta.tietorakenteet.Polygoni;
import reittienEtsinta.tietorakenteet.Verkko;
import reittienEtsinta.tietorakenteet.Verkontekija;

/**
 *
 * @author elias
 */
public class ReitinEtsintaEriMaastotTest {

    private Verkko verkko;

    public ReitinEtsintaEriMaastotTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @Test
    public void reitinEtsintaTestTasavauhdit() {
        System.out.println("Tasavauhdit");
        this.reitinEtsintaTest(new int[]{1, 1, 1, 1});
    }

    @Test
    public void reitinEtsintaTestErotX10() {
        System.out.println("X10");
        this.reitinEtsintaTest(new int[]{10, 10, 10, 1});
    }

    @Test
    public void reitinEtsintaTestErotX100() {
        System.out.println("X100");
        this.reitinEtsintaTest(new int[]{100, 100, 100, 1});
    }

    @Test
    public void reitinEtsintaTestErotX1000() {
        System.out.println("X1000");
        this.reitinEtsintaTest(new int[]{1000, 1000, 1000, 1});
    }

    private void reitinEtsintaTest(int[] vauhdit) {
        this.luoVerkko(vauhdit);
        long aikasumma = 0;
        int n = 10;
        for (int i = 0; i < n; i++) {
            long aikaAlussa = System.currentTimeMillis();
            this.verkko.alustus(4405, 2348);

            this.verkko.aStar();

            long aikaLopussa = System.currentTimeMillis();
            aikasumma += aikaLopussa - aikaAlussa;

        }
        System.out.println("Reitin etsintään kului aikaa: " + (aikasumma) / n + "ms.");

    }

    private void luoVerkko(int[] vauhdit) {
        File polygonKansio = new File("aineisto/matinkyla/polygonit");
        if (!polygonKansio.isDirectory()) {
            System.out.println("anna kansio");
            return;
        }
        File[] polygonTiedostot = polygonKansio.listFiles();

        GeoJsonLukija lukija = new GeoJsonLukija();
        int maastoja;

        Lista<Polygoni> polygonilista = new Lista(128);

        for (maastoja = 0; maastoja < polygonTiedostot.length; maastoja++) {

            lukija.luePolygonit(polygonTiedostot[maastoja], maastoja, polygonilista);
        }
        Maastokirjasto maastokirjasto = new Maastokirjasto(maastoja);
        for (int i = 0; i <= maastoja; i++) {
            maastokirjasto.lisaaVauhti(i, vauhdit[i]);
        }
        System.out.println("polygonit luettu, " + polygonilista.koko() + " polygonia");
        int solmuja = lukija.getPisteita();
        System.out.println("Solmuja: " + solmuja);

        Verkontekija verkontekija = new Verkontekija(polygonilista, lukija.getLatmin(), lukija.getLatmax(),
                lukija.getLonmin(), lukija.getLonmax(), solmuja, maastokirjasto);
        verkontekija.luoVerkko();
        this.verkko = verkontekija.getVerkko();
    }

}
